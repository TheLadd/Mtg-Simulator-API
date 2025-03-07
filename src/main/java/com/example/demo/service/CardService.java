package com.example.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Card;
import com.example.demo.entity.ScryfallCompositeKey;
import com.example.demo.exception.InvalidSyntaxException;
import com.example.demo.repository.CardRepository;

@Service
public class CardService {
    CardRepository cardRepository;

    @Autowired
    CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    private List<ScryfallCompositeKey> extractCompositeKeys(String archidektFileContents) throws InvalidSyntaxException { 
        /*  Assumptions:
         *  - The default export to txt option is used on Archidekt.com
         *  - No cards have "(", ")", "[", or "]" in their title
         *  - The 
         */

        // TODO: input sanitation/checking
        
        List<ScryfallCompositeKey> keys = new ArrayList<>(100);
        keys.add(null); // Leave top space for commander 

        List<String> lines = new ArrayList<>( Arrays.asList(archidektFileContents.split("\n")) );
        List<String> words = new ArrayList<>(2);
        Integer begin, end, collectorNumber;
        String setCode;
        ScryfallCompositeKey key;
        for (String line : lines) {
            // Find where the title of the card ends and categories begin
            begin = line.indexOf("(");
            end = line.indexOf("[");

            // Get the section of the line containing setCode and collectorNumber
            words.clear();
            words = Arrays.asList(line.substring(begin, end));

            // Grab the important bits and construct our key
            setCode = words.get(0).substring(1, 4); // Set code should be form of "(xxx)"
            collectorNumber = Integer.valueOf(words.get(1));
            key = new ScryfallCompositeKey(setCode, collectorNumber);

            // Put in "deck" (on top if commander)
            if (line.substring(end).contains("Commander")) {
                keys.set(0, key);
            }
            else {
                keys.add(key);
            }
        }

        return keys;
     }

    public List<Card> getDeckFromCompositeKeys(List<ScryfallCompositeKey> keys) { 
        List<Card> deck = cardRepository.getCardLinksSetCodeAndCollectorNumber(keys)
                                .stream()
                                .map(Card::new)
                                .collect(Collectors.toList());
        return deck;
     } // Throws whatever bad request to Scryfall throws

    public List<Card> getDeckFromArchidektFileContents(String archidektFileContents) throws InvalidSyntaxException {
        List<ScryfallCompositeKey> keys = extractCompositeKeys(archidektFileContents);
        return getDeckFromCompositeKeys(keys);
     } // Throws whatever bad request to Scryfall throws

    private Optional<String> getCard(String setCode, int collectorNumber) throws IOException, InterruptedException, InvalidSyntaxException { 
            return cardRepository.getCardLinkBySetAndCollectorNumber(setCode, collectorNumber);
    }
}
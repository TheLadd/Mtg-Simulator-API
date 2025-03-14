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
import com.example.demo.exception.GatewayTimeoutException;
import com.example.demo.exception.InternalServerErrorException;
import com.example.demo.exception.InvalidSyntaxException;
import com.example.demo.repository.CardRepository;

@Service
public class CardService {
    CardRepository cardRepository;

    @Autowired
    CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<String> extractImageLinks(List<Card> cards) {
        return cards.stream()
                    .map(card -> card.getImg())
                    .collect(Collectors.toList());
    }

    public List<ScryfallCompositeKey> extractCompositeKeys(String archidektFileContents) throws InvalidSyntaxException { 
        /*  Assumptions:
         *  - The default export to txt option is used on Archidekt.com
         *  - No cards have "(", ")", "[", or "]" in their title
         */

        // TODO: input sanitation/checking
        
        List<ScryfallCompositeKey> keys = new ArrayList<>(100);
        keys.add(null); // Leave top space for commander 

        List<String> lines = new ArrayList<>( Arrays.asList(archidektFileContents.split("\n")) );
        List<String> words = new ArrayList<>(2);
        Integer begin, end, quantity, collectorNumber;
        String setCode;
        ScryfallCompositeKey key;
        try {
            for (String line : lines) {
                end = line.indexOf("x");
                quantity = Integer.valueOf(line.substring(0, end));

                // Find where the title of the card ends and categories begin
                begin = line.indexOf("(");
                end = line.indexOf("[");

                // Get the section of the line containing setCode and collectorNumber
                words.clear();
                words.addAll(Arrays.asList(line.substring(begin, end).split(" ")));

                // Grab the important bits and construct our key
                end = words.get(0).indexOf(")");
                setCode = words.get(0).substring(1, end); // Set code should be form of "(xxx)"

                // Check if card is part of "The List". Make appropriate adjustments if so
                if (setCode.equals("plst")) {
                    setCode = words.get(1).substring(0, 3);
                    collectorNumber = Integer.valueOf(words.get(1).substring(4));   // Skip third character ('-')
                }
                else {
                    collectorNumber = Integer.valueOf(words.get(1));
                }

                // Put in "deck" (on top if commander)
                key = new ScryfallCompositeKey(setCode, collectorNumber);
                if (line.substring(end).contains("Commander")) {
                    keys.set(0, key);
                }
                else {
                    for (int i = 0; i < quantity; i++) {
                        keys.add(key);
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            String errMsg = "Invalid syntax in provided deck list." + e.getMessage();
            throw new InvalidSyntaxException(errMsg);
        }

        return keys;
    }

    private List<Card> getDeckFromCompositeKeys(List<ScryfallCompositeKey> keys) throws IOException, InterruptedException { 
        List<Card> deck = cardRepository.getCardLinksBySetCodeAndCollectorNumber(keys)
                                .stream()
                                .map(Card::new)
                                .collect(Collectors.toList());
        return deck;
    } 

    public List<Card> getDeckFromArchidektFileContents(String archidektFileContents) throws InvalidSyntaxException, InternalServerErrorException, GatewayTimeoutException {
        try {
            List<ScryfallCompositeKey> keys = extractCompositeKeys(archidektFileContents);
            return getDeckFromCompositeKeys(keys);
        } catch (IOException e) {
            throw new InternalServerErrorException(e.getMessage());
        } catch (InterruptedException e) {
            throw new GatewayTimeoutException(e.getMessage());
        }
    } 

    private Optional<String> getCard(String setCode, int collectorNumber) throws IOException, InterruptedException, InvalidSyntaxException { 
            return cardRepository.getCardLinkBySetAndCollectorNumber(setCode, collectorNumber);
    }
}
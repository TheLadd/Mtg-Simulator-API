package com.example.demo.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Card;
import com.example.demo.exception.InvalidSyntaxException;
import com.example.demo.repository.CardRepository;

@Service
public class CardService {
    CardRepository cardRepository;

    @Autowired
    CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public static List<Card> buildDeck(String archidekString) throws InvalidSyntaxException { return null; } // Throws whatever bad request to Scryfall throws
    private Optional<String> getCard(String setCode, int collectorNumber) throws IOException, InterruptedException, InvalidSyntaxException { 
            return cardRepository.getCardLinkBySetAndCollectorNumber(setCode, collectorNumber);
    }
}
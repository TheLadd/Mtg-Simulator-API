package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Card;
import com.example.demo.exception.InvalidSyntaxException;

@Service
public class CardService {
    public static List<Card> buildDeck(String archidekString) throws InvalidSyntaxException { return null; } // Throws whatever bad request to Scryfall throws
    private Optional<Card> getCard(String setCode, int collectorNumber) { return null; }    // Throws whatever bad request to Scryfall throws
}
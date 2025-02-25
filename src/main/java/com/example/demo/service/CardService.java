package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Card;
import com.example.demo.exception.InvalidSyntaxException;

public class CardService {
    public static List<Card> buildDeck(String archidekString) throws InvalidSyntaxException { return null; } // Throws whatever bad request to Scryfall throws
    private Optional<Card> getCard(String setCode, int collectorNumber) { return null; }    // Throws whatever bad request to Scryfall throws
}
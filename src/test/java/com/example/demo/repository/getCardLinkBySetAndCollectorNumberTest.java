package com.example.demo.repository;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.demo.exception.InvalidSyntaxException;

public class getCardLinkBySetAndCollectorNumberTest {
    CardRepository cardRepository;

    @BeforeEach
    public void setup() {
        cardRepository = new CardRepository();
    }

    @Test
    public void getCardLinkSuccessful() throws IOException, InterruptedException, InvalidSyntaxException {
        // Composite key for "Stella Lee, Wild Card"
        String setCode = "otc";
        Integer collectorNumber = 3;


        String actualCardLink = "https://cards.scryfall.io/normal/front/2/a/2a8a7696-b5d9-4378-9d5c-2c9007e4df63.jpg?1714110409";
        Optional<String> retrievedCardLink = cardRepository.getCardLinkBySetAndCollectorNumber(setCode, collectorNumber);
        Assertions.assertEquals(actualCardLink, retrievedCardLink.get());
    }

    @Test
    public void getCardLinkUnsuccessful() throws IOException, InterruptedException, InvalidSyntaxException {
        // Composite key for a card that doesn't exist 
        String setCode = "otc";
        Integer collectorNumber = 999;

        InvalidSyntaxException e = Assertions.assertThrows(InvalidSyntaxException.class, () -> cardRepository.getCardLinkBySetAndCollectorNumber(setCode, collectorNumber));
        Assertions.assertTrue(e.getMessage().contains("Scryfall responded with status code"));
    }
}
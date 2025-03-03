package com.example.demo;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import com.example.demo.exception.InvalidSyntaxException;
import com.example.demo.repository.CardRepository;

public class getCardLinkBySetAndCollectorNumberTest {
    ApplicationContext app;
    CardRepository cardRepository;

    @BeforeEach
    public void setup() throws InterruptedException {
        cardRepository = new CardRepository();
        String[] args = new String[] {};
        app = SpringApplication.run(MtgApp.class, args);
        Thread.sleep(500);
    }

    @AfterEach
    public void teardown() throws InterruptedException {
        Thread.sleep(500);
        SpringApplication.exit(app);
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
}
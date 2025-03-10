package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.demo.entity.ScryfallCompositeKey;
import com.example.demo.exception.InvalidSyntaxException;
import com.example.demo.repository.CardRepository;



public class extractCompositeKeysTest {
    // @Autowired
    private CardService cardService;

    @BeforeEach
    public void setup() {
        this.cardService = new CardService(new CardRepository());   // Feels like Spring could make this nicer, no?
    }

    @Test
    public void extractCompositeKeysSuccessful() {
        Path deckPath = Paths.get("src/test/java/com/example/demo/resources/archidektFile-zahur.txt");
        Path keysPath = Paths.get("src/test/java/com/example/demo/resources/zahurCompositeKeys.txt");
        try {
            String fileContents = new String(Files.readAllBytes(deckPath));
            List<ScryfallCompositeKey> extractedKeys = cardService.extractCompositeKeys(fileContents);

            List<ScryfallCompositeKey> actualKeys = Files.lines(keysPath)
                                        .map(ScryfallCompositeKey::new)
                                        .collect(Collectors.toList());
            

            for (int i = 0; i < 100; i++) {
                Assertions.assertEquals(actualKeys.get(i), extractedKeys.get(i), 
                    "The " + String.valueOf(i) + "-th expected element, " + actualKeys.get(i) 
                    + " doesn't match it's corresponding actual element, " + extractedKeys.get(i));
            }

            // Assertions.assertEquals(actualKeys, extractedKeys);
        } catch(IOException e) {
            Assertions.fail("Invalid file path in extractCompositeKeysSuccessful(); " + e.getMessage());
        } catch (InvalidSyntaxException e) {
            Assertions.fail("File with invalid syntax in extractCompositeKeysSuccessful(); " + e.getMessage());
        }

    }

    @Test
    public void extractCompositeKeysUnsuccessful() {}
}
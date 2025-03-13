package com.example.demo.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.demo.entity.ScryfallCompositeKey;
import com.example.demo.exception.NonExistantCardException;

public class getCardLinksBySetCodeAndCollectorNumberTest {
    private CardRepository cardRepository;

    @BeforeEach
    public void setup() {
        this.cardRepository = new CardRepository();
    }

    @Test
    public void getCardLinksBySetCodeAndCollectorNumberSuccessful() {
        try {
            Path keysPath = Paths.get("src/test/java/com/example/demo/resources/zahurCompositeKeys.txt");
            List<ScryfallCompositeKey> keys = Files.lines(keysPath)
                                        .map(ScryfallCompositeKey::new)
                                        .collect(Collectors.toList());
            List<String> actualLinks = cardRepository.getCardLinksBySetCodeAndCollectorNumber(keys);

            Path cardLinksPath = Paths.get("src/test/java/com/example/demo/resources/zahurFrontImageLinks.txt");
            List<String> expectedLinks = Files.lines(cardLinksPath).collect(Collectors.toList());

            Assertions.assertIterableEquals(expectedLinks, actualLinks);
        } catch (IOException e) {
            Assertions.fail("Invalid file path in getCardLinksBySetCodeAndCollectorNumberSuccessful(); " + e.getMessage());
        } catch (InterruptedException e) {
            Assertions.fail("Scryfall not responding;" + e.getMessage());
        }
    }

    @Test
    public void getCardLinksBySetCodeAndCollectorNumberUnsuccessful() {
        ScryfallCompositeKey nonExistantCard = new ScryfallCompositeKey("orc", 999);
        List<ScryfallCompositeKey> keys = Arrays.asList(nonExistantCard);
        Assertions.assertThrows(NonExistantCardException.class, () -> cardRepository.getCardLinksBySetCodeAndCollectorNumber(keys));
    }
}
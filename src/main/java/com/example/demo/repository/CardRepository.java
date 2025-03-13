package com.example.demo.repository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.ScryfallCompositeKey;
import com.example.demo.exception.InvalidSyntaxException;
import com.example.demo.exception.NonExistantCardException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class CardRepository {
    HttpClient webClient;

    public CardRepository() {
        this.webClient = HttpClient.newHttpClient();
    }

    private HttpResponse<String> scryfallCollectionRequest(String body) 
        throws IOException, InterruptedException {
            String uri = "https://api.scryfall.com/cards/collection";
            HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(uri))
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(body))
                            .build();
            HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response;
    }

    private String createScryfallCollectionRequestBody(List<String> cardIds) {
            if (cardIds.isEmpty()) {
                return null;
            }

            String requestBodyTemplate = "{ \"identifiers\": [%s] }";
            String requestBody = String.format(
                requestBodyTemplate, 
                cardIds.stream().collect(Collectors.joining(","))
            );
            return requestBody;
    }

    private void removeBackImageLinks(List<String> links) {
            List<Integer> indicesToRemove = new ArrayList<>();
            for (int i = 0; i < links.size(); i++) {
                if (links.get(i).contains("back")) {
                    indicesToRemove.add(i);
                }
            }
            for (int i = indicesToRemove.size()-1; i >= 0; i--) {
                links.remove(indicesToRemove.get(i).intValue());
            }
    }

    public List<String> getCardLinksBySetCodeAndCollectorNumber(List<ScryfallCompositeKey> keys)
        throws NonExistantCardException, IOException, InterruptedException { 
            List<String> cardLinks = new ArrayList<>(100);

            // 0. Scryfall won't let you query for more than 50 cards at a time
            while (keys.size() > 50) {
                List<String> firstFifty = getCardLinksBySetCodeAndCollectorNumber(keys.subList(0, 50));
                cardLinks.addAll(firstFifty);
                keys = keys.subList(50, keys.size()); 
            }

            // 1. Make JSON package for collection POST request
            List<String> cardIdentifiers = new ArrayList<>(100);
            String singleIdentifier =  "{ \"set\": \"%s\", \"collector_number\": \"%d\" }";
            for (ScryfallCompositeKey key : keys) {
                cardIdentifiers.add(String.format(singleIdentifier, key.getSetCode(), key.getCollectorNumber()));
            }
            String requestBody = createScryfallCollectionRequestBody(cardIdentifiers);

            // 2. Make request to Scryfall 
            HttpResponse<String> response = scryfallCollectionRequest(requestBody);
        
            // 3. Check if any queries card doesn't exist
            ObjectMapper om = new ObjectMapper();
            JsonNode notFoundArray = om.readTree(response.body()).get("not_found");
            if (!notFoundArray.isEmpty()) {
                throw new NonExistantCardException();
            }

            // 4. Extract image links from HTTP response
                // response["data"][i]["image_uris"]["normal"] for all i
            JsonNode cards = om.readTree(response.body()).get("data");
            List<String> links = cards.findValuesAsText("normal"); 

            // 5. Get rid of card "back" images because holy shit.
            removeBackImageLinks(links);

            cardLinks.addAll(links);
            return cardLinks;
    }




    // Note: this was for practice and doesn't have plans for production
    public Optional<String> getCardLinkBySetAndCollectorNumber(String setCode, int collectorNumber) 
        throws IOException, InterruptedException, InvalidSyntaxException { 
        String uri = "https://api.scryfall.com/cards/%s/%d";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(String.format(uri, setCode, collectorNumber)))
            .header("format", "json")
            .GET()
            .build();

        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        if (status != 200) {
            String msg = "Scryfall responded with status code %d";
            throw new InvalidSyntaxException(String.format(msg, status));
        }
    
        ObjectMapper om = new ObjectMapper();
        String link = om.readTree(response.body()).get("image_uris").get("normal").textValue();
        return Optional.of(link);
    }
}
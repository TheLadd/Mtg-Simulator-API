package com.example.demo.repository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.exception.InvalidSyntaxException;

@Repository
public class CardRepository {
    HttpClient webClient;

    // @Autowired
    // CardRepository(HttpClient webClient) {
    public CardRepository() {
        this.webClient = HttpClient.newHttpClient();
    }

    public List<String> getCardLinksBySetAndCollectorNumber(String setCode, int collectorNumber) { return null; }
    public Optional<String> getCardLinkBySetAndCollectorNumber(String setCode, int collectorNumber) 
        throws IOException, InterruptedException, InvalidSyntaxException { 
        String uri = "https://api.scryfall.com/cards/%s/%d";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(String.format(uri, setCode, collectorNumber)))
            .header("format", "json")
            .header("pretty", "true")
            .GET()
            .build();

        // TODO: .ofString() because JSON is processed as a string?
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString() );
        int status = response.statusCode();
        if (status != 200) {
            String msg = "Scryfall responded with status code %d";
            throw new InvalidSyntaxException(String.format(msg, status));
        }
    
        // System.out.println(response.body());
        // ObjectMapper om = new ObjectMapper();
        // Map<String, String> json = om.readValue(response.body(), new TypeReference<Map<String, String>>() {});
        // Map<String, String> image_uris = om.readValue(json.get("image_uris"), new TypeReference<Map<String, String>>() {});

        // return Optional.of(image_uris.get("normal"));
        return Optional.of(response.body());
    }
}
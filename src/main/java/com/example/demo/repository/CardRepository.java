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
import com.fasterxml.jackson.databind.ObjectMapper;

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

        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString() );
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
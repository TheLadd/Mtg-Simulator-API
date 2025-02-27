package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Card;
import com.example.demo.entity.Move;
import com.example.demo.service.CardService;
import com.example.demo.service.PlayerService;

@RestController
public class MtgSimulatorController {
    private final PlayerService playerService;
    private final CardService cardService;

    @Autowired
    MtgSimulatorController(PlayerService playerService, CardService cardService) {
        this.playerService = playerService;
        this.cardService = cardService;
    }

    @PostMapping(value = "/game/join") 
    public Boolean joinGame() {
        return playerService.addPlayer();
    }

    @PostMapping(value = "/player/{playerId}/library")
    public List<Card> createLibraryByPlayerId(@RequestBody String archidektFileContents, @PathVariable Integer playerId ) {
        List<Card> library = CardService.buildDeck(archidektFileContents);
        return playerService.setLibrary(playerId, library);
    }

    @GetMapping(value = "/player/{playerId}/library")
    public List<Card> getLibraryByPlayerId(@PathVariable Integer playerId) {
        return playerService.getLibrary(playerId);
    }

    @PatchMapping(value = "/player/{playerId}/move")
    public Boolean makeMoveByPlayerId(@RequestBody Move move, @PathVariable Integer playerId) { 
        return playerService.move(move);
     }

     @PatchMapping(value = "/player/{playerId}/library/shuffle")
     public List<Card> shuffleLibraryByPlayerId(@PathVariable Integer playerId) {
        return playerService.shuffle(playerId);
     }

     @PatchMapping(value = "/player/{playerId}/battlefield/tap")
     public Boolean tapCardByPlayerId(@RequestBody Integer index, @PathVariable Integer playerId) {
        return playerService.tap(playerId, index);
     }

     @PatchMapping(value = "/player/{playerId}/flip")
     public Boolean tapCardByPlayerId(@RequestBody Integer index, @PathVariable Integer playerId) {
        return playerService.tap(playerId, index);
     }
}
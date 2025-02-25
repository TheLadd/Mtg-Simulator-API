package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Player;
import com.example.demo.exception.GameFullException;
import com.example.demo.exception.InvalidMoveException;
import com.example.demo.exception.InvalidSyntaxException;
// import com.example.demo.exception.MalformedSyntaxException;

@Service
public class PlayerService {
    private List<Player> players;
    private CardService cardService;

    @Autowired
    PlayerService(CardService cardService) {
        this.cardService = cardService;
    }

    public boolean addPlayer() throws GameFullException { return false; }
    public boolean buildDeck(int playerId, String archidektString) throws InvalidSyntaxException { return false; }  // Also throws whatever a bad request to Scryfall would throw
    public void shuffle(int playerId) {};
    public boolean tap(int playerId, int index) { return false; }
    public boolean flip(int playerId, String zone, int index) throws InvalidMoveException { return false; } // NOTE: zoneString should already contain playerId, right?
    public int adjustLife(int playerId, int x) { return -1; }

    public boolean move(String origZone, int origIndex, String destZone, int destIndex) throws InvalidSyntaxException { return false; }
}
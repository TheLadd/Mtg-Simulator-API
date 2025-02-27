package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Card;
import com.example.demo.entity.Move;
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

    public static List<Card> getZoneByString(String zone) { return null; }

    public Boolean addPlayer() throws GameFullException { return false; }
    public List<Card> setLibrary(int playerId, List<Card> deck) { return null; }
    public List<Card> getLibrary(int playerId) { return null; }
    public List<Card> shuffle(int playerId) { return null; }
    public Boolean tap(int playerId, int index) { return false; }
    public Boolean flip(int playerId, String zone, int index) throws InvalidMoveException { return false; } // NOTE: zoneString should already contain playerId, right?
    public Integer adjustLife(int playerId, int x) { return -1; }

    public Boolean move(Move move) throws InvalidSyntaxException { return false; }
}
package com.example.demo.entity;

import java.util.List;
import java.util.Optional;

public class Player {
    private int playerId;
    private int life;

    private List<Card> library;
    private List<Card> hand;
    private List<Card> battlefield;
    private List<Card> graveyard;
    private List<Card> exile;
    private Optional<Card> commandZone;

    public void shuffle() {}
    public boolean tap(int battlefieldIndex) { return false; }
    public boolean flip(String zone, int index) { return false; }
    public int adjustLife(int x) { return -1; }
    public Optional<Card> placeCard(Card c, String zone, int index) { return null; }
    public Optional<Card> getCard(String zone, int index) { return null; }

    private List<Card> getZone(String zone) { return null; }
}
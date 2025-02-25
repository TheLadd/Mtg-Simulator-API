package com.example.demo.entity;

public class Card {
    private final String imgLink;
    private boolean isTapped;
    private boolean isFlipped;


    public Card(String imgLink) {
        this.imgLink = imgLink;
    }

    public Card(String imgLink, boolean isTapped, boolean isFlipped) {
        this.imgLink = imgLink;
        this.isTapped = isTapped;
        this.isFlipped = isFlipped;
    }

    public String getImg() {
        return imgLink;
    }

    public boolean isTapped() {
        return isTapped;
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public void tap() {
        isTapped = !isTapped;
    }

    public void flip() {
        isFlipped = !isFlipped; 
    }
}
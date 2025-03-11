package com.example.demo.entity;

import java.util.Arrays;
import java.util.List;

public class ScryfallCompositeKey {
    private final String setCode;
    private final Integer collectorNumber;

    public ScryfallCompositeKey(String setCode, Integer collectorNumber) {
        this.setCode = setCode;
        this.collectorNumber = collectorNumber;
    }

    public ScryfallCompositeKey(String line) {
        List<String> words = Arrays.asList(line.split(" "));
        this.setCode = words.get(0);
        this.collectorNumber = Integer.valueOf(words.get(1));
    }
    
    public String getSetCode() {
        return this.setCode;
    }

    public Integer getCollectorNumber() {
        return this.collectorNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ScryfallCompositeKey)) {
            return false;
        }

        ScryfallCompositeKey other = (ScryfallCompositeKey) o;
        return (this.getSetCode().equals(other.getSetCode()) && this.getCollectorNumber().equals(other.getCollectorNumber()) );
    }

    @Override
    public String toString() {
        return this.setCode + " " + String.valueOf(collectorNumber);
    }
}
package com.example.demo.entity;

public class ScryfallCompositeKey {
    private final String setCode;
    private final Integer collectorNumber;

    public ScryfallCompositeKey(String setCode, Integer collectorNumber) {
        this.setCode = setCode;
        this.collectorNumber = collectorNumber;
    }
    
    public String getSetCode() {
        return this.setCode;
    }

    public Integer getCollectorNumber() {
        return this.collectorNumber;
    }
}
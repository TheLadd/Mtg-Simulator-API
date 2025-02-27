package com.example.demo.entity;

public class Move {
    private String originZone;
    private Integer originIndex;
    private String destinationZone;
    private Integer destinationIndex;
    private Boolean flip;
    private Boolean tap;

    Move(String originZone, Integer originIndex, String destinationZone, Integer destinationIndex, Boolean tap, Boolean flip) {
        this.originZone = originZone;
        this.originIndex = originIndex;
        this.destinationZone = destinationZone;
        this.destinationIndex = destinationIndex;
        this.tap = tap;
        this.flip = flip;
    }

    public String getOriginZone() { return this.originZone; }
    public String getDestinationZone() { return this.destinationZone; }
    public Integer getOriginIndex() { return this.originIndex; }
    public Integer getDestinationIndex() { return this.destinationIndex; }
    public Boolean getFlip() { return this.flip; }
    public Boolean getTap() { return this.tap; }
}
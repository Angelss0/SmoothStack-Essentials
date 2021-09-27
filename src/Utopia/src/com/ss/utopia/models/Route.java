package com.ss.utopia.models;

public class Route implements IModel {
    private int id;
    private Airport origin;
    private Airport destination;

    public Route() {}
    public Route(int id, Airport origin, Airport destination) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Airport getOrigin() { return origin; }
    public void setOrigin(Airport origin) { this.origin = origin; }

    public Airport getDestination() { return destination; }
    public void setDestination(Airport destination) { this.destination = destination; }

    @Override
    public String getReadView() {
        return getOrigin().getReadView() + " -> " + getDestination().getReadView();
    }

    @Override
    public String toString() {
        return getReadView();
    }
}

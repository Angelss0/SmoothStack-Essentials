package com.ss.utopia.models;

public class AirplaneType implements IModel {
    private int id;
    private int maxCapacity;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }

    @Override
    public String getReadView() {
        return "max capacity: " + getMaxCapacity();
    }

    @Override
    public String toString() { return getReadView(); }
}

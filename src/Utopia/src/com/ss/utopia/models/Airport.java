package com.ss.utopia.models;

public class Airport implements IModel {
    private String iataID;
    private String cityName;

    public String getIataID() { return iataID; }
    public void setIataID(String iataID) { this.iataID = iataID; }

    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }

    @Override
    public int getId() { return -1; }

    @Override
    public String getReadView() { return getIataID() + ", " + getCityName(); }

    @Override
    public String toString() { return getReadView(); }
}

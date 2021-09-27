package com.ss.utopia.models;

import java.sql.Date;

public class Passenger implements IModel {
    private int id;
    private Booking booking;
    private String givenName;
    private String familyName;
    private Date dateOfBirth;
    private String gender;
    private String address;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getFamilyName() { return familyName; }
    public void setFamilyName(String familyName) { this.familyName = familyName; }

    public String getGivenName() { return givenName; }
    public void setGivenName(String givenName) { this.givenName = givenName; }

    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }

    @Override
    public String getReadView() {
        return getFamilyName() + ", " + getGivenName() + ": " + getBooking().getReadView();
    }

    @Override
    public String toString() {
        return
        "Name: " + getFamilyName() + ", " + getGivenName() + "\n" +
        "Date of Birth: " + getDateOfBirth() + "\n" +
        "Gender: " + getGender() + "\n" +
        "Address: " + getAddress()
        ;
    }
}

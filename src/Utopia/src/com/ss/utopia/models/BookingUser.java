package com.ss.utopia.models;

public class BookingUser implements IModel {
    private Booking booking;
    private User user;

    public BookingUser() {}
    public BookingUser(Booking booking, User user) {
        this.booking = booking;
        this.user = user;
    }

    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    @Override
    public int getId() { return getBooking().getId(); }

    @Override
    public String getReadView() {
        return
        getUser().getReadView() + "\n" +
        getBooking().getReadView();
    }

    @Override
    public String toString() { return getReadView(); }
}

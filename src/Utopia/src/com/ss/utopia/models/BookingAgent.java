package com.ss.utopia.models;

public class BookingAgent implements IModel {
    private Booking booking;
    private User agent;

    public BookingAgent() {}
    public BookingAgent(Booking booking, User agent) {
        this.booking = booking;
        this.agent = agent;
    }

    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }

    public User getAgent() { return agent; }
    public void setAgent(User agent) { this.agent = agent; }

    @Override
    public int getId() { return getBooking().getId(); }

    @Override
    public String getReadView() {
        return getAgent().getReadView() + ", " + getBooking().getReadView();
    }
}

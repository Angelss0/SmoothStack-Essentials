package com.ss.utopia.models;

public class BookingPayment implements IModel {
    private Booking booking;
    private String stripeId;
    private boolean refunded;

    public BookingPayment() {}

    public BookingPayment(Booking booking, String stripeId, boolean refunded) {
        this.booking = booking;
        this.stripeId = stripeId;
        this.refunded = refunded;
    }

    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }

    public boolean getRefunded() { return refunded; }
    public void setRefunded(boolean refunded) { this.refunded = refunded; }

    public String getStripeId() { return stripeId; }
    public void setStripeId(String stripeId) { this.stripeId = stripeId; }

    @Override
    public int getId() { return getBooking().getId(); }

    @Override
    public String getReadView() {
        return getStripeId() + ", " + getBooking().getReadView();
    }
}

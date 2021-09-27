package com.ss.utopia.models;

public class BookingGuest implements IModel {
    private Booking booking;
    private String email;
    private String phone;

    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String contactEmail) { this.email = contactEmail; }

    @Override
    public int getId() { return getBooking().getId(); }

    @Override
    public String getReadView() {
        return getBooking().getReadView() + getBooking().getConfirmationCode();
    }
}

package com.ss.lms.models;

import java.util.List;

public class Borrower implements IModel {
    private int cardNo;
    private String name;
    private String address;
    private String phone;
    private List<BookLoans> loans;

    public int getCardNo() { return cardNo; }
    public void setCardNo(int cardNo) { this.cardNo = cardNo; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public List<BookLoans> getLoans() { return loans; }
    public void setLoans(List<BookLoans> loans) { this.loans = loans; }

    @Override
    public String toString() {
        return "Borrower - CardNo: " + getCardNo() +
        ", name: " + getName() +
        ", Address " + getAddress() +
        ", Phone " + getPhone();
    }

    @Override
    public String getMenuRep() { return getName() + ", Card No." + getCardNo(); }
}

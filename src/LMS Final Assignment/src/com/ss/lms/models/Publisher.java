package com.ss.lms.models;

public class Publisher implements IModel {
    private int id;
    private String name;
    private String address;
    private String phone;

    public int getID() { return id; }
    public void setID(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String getMenuRep() {
        return getName() + ", " + getAddress();
    }
}

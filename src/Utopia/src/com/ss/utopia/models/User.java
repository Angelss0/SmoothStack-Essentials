package com.ss.utopia.models;

public class User implements IModel {

    public final static int EMPLOYEE = 0;
    public final static int TRAVELER = 1;

    private int id;
    private UserRole role;
    private String givenName;
    private String familyName;
    private String username;
    private String email;
    private String password;
    private String phone;

    public int getId() { return id;}
    public void setId(int id) { this.id = id;}

    public String getPhone() { return phone;}
    public void setPhone(String phone) { this.phone = phone;}

    public String getPassword() { return password;}
    public void setPassword(String password) { this.password = password;}

    public String getEmail() { return email;}
    public void setEmail(String email) { this.email = email;}

    public String getUsername() { return username;}
    public void setUsername(String username) { this.username = username;}

    public String getFamilyName() { return familyName;}
    public void setFamilyName(String familyName) { this.familyName = familyName;}

    public String getGivenName() { return givenName;}
    public void setGivenName(String givenName) { this.givenName = givenName;}

    public UserRole getRole() { return role;}
    public void setRole(UserRole role) { this.role = role;}

    @Override
    public String getReadView() {
        return getUsername() + ", " + getRole().getName();
    }

    @Override
    public String toString() {
        return
        "Name: " + getFamilyName() + ", " + getGivenName() + "\n" +
        "Role: " + getRole().getName() + "\n" +
        "Username: " + getUsername() + "\n" +
        "Email: " + getEmail() + "\n" +
        "Password: " + getPassword() + "\n" +
        "Phone: " + getPhone()
        ;
    }
}

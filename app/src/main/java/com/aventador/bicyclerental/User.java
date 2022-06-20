package com.aventador.bicyclerental;

public class User {

    private int id;
    private String specID;
    private String name;
    private String phoneNumber;
    private String password;
    private double wallet;
    private String bikeName;
    private int bikeNumber;

    public User(int id, String specID, String name, String phoneNumber, String password, double wallet, String bikeName, int bikeNumber) {
        this.id = id;
        this.specID = specID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.wallet = wallet;
        this.bikeName = bikeName;
        this.bikeNumber = bikeNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpecID() {
        return specID;
    }

    public void setSpecID(String specID) {
        this.specID = specID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }

    public String getBikeName() {
        return bikeName;
    }

    public void setBikeName(String bikeName) {
        this.bikeName = bikeName;
    }

    public int getBikeNumber() {
        return bikeNumber;
    }

    public void setBikeNumber(int bikeNumber) {
        this.bikeNumber = bikeNumber;
    }
}

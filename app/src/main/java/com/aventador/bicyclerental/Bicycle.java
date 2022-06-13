package com.aventador.bicyclerental;

public class Bicycle {

    private int id;
    private int specID;
    private String name;
    private double coordX;
    private double coordY;
    private String status;
    private String bType;

    public Bicycle(int id, int specID, String name, double coordX, double coordY, String status, String bType) {
        this.id = id;
        this.specID = specID;
        this.name = name;
        this.coordX = coordX;
        this.coordY = coordY;
        this.status = status;
        this.bType = bType;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSpecID() {
        return specID;
    }

    public void setSpecID(int specID) {
        this.specID = specID;
    }

    public double getCoordX() {
        return coordX;
    }

    public void setCoordX(double coordX) {
        this.coordX = coordX;
    }

    public double getCoordY() {
        return coordY;
    }

    public void setCoordY(double coordY) {
        this.coordY = coordY;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getbType() {
        return bType;
    }

    public void setbType(String bType) {
        this.bType = bType;
    }

}

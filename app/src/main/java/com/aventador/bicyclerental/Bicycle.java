package com.aventador.bicyclerental;

public class Bicycle {

    private int id;
    private int specID;             // 0001 (Для кожного ровера резервується 1000 слотів на всякий випадок)
    private String name;            // Orbea Capre
    private double coordX;          // 48.9127
    private double coordY;          // 24.6877
    private String status;          // parking, inUse, inRepair
    private String whoIsRental;     // TEST_USER
    private String bType;           // City, Electro, Kids

    public Bicycle(int id, int specID, String name, double coordX, double coordY, String status, String whoIsRental, String bType) {
        this.id = id;
        this.specID = specID;
        this.name = name;
        this.coordX = coordX;
        this.coordY = coordY;
        this.status = status;
        this.whoIsRental = whoIsRental;
        this.bType = bType;
    }


    public int getId() { return id; }

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

    public String getWhoIsRental() {
        return whoIsRental;
    }

    public void setWhoIsRental(String whoIsRental) {
        this.whoIsRental = whoIsRental;
    }
}

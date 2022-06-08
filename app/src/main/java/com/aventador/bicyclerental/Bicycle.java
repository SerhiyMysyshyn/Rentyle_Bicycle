package com.aventador.bicyclerental;

public class Bicycle {

    private String id;
    private String specID;
    private String name;
    private String coordX;
    private String coordY;
    private String status;
    private String bType;

    public Bicycle(String id, String specID, String name, String coordX, String coordY, String status, String bType) {
        this.id = id;
        this.specID = specID;
        this.name = name;
        this.coordX = coordX;
        this.coordY = coordY;
        this.status = status;
        this.bType = bType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getCoordX() {
        return coordX;
    }

    public void setCoordX(String coordX) {
        this.coordX = coordX;
    }

    public String getCoordY() {
        return coordY;
    }

    public void setCoordY(String coordY) {
        this.coordY = coordY;
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

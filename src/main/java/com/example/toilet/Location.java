package com.example.toilet;

public class Location {
    private String name;
    private String type;
    private boolean isFree;
    private String latitude;
    private String longitude;
    private Status status;

    public Location() {
    }

    public Location(String name, String type, boolean isFree, String latitude, String longitude, Status status) {
        this.name = name;
        this.type = type;
        this.isFree = isFree;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getIsFree() {
        return isFree;
    }

    public void setFree(boolean isFree) {
        this.isFree = isFree;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

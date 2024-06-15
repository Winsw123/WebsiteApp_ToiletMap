package com.example.toilet;

public class Location extends Status {
    private String name;
    private String isFree;
    private String type;
    private String latitude;
    private String longitude;
    private Status status;

    public Location() {
    }

    public Location(boolean isAvailable, boolean isClean, boolean isPaper, boolean isSoap, String name, String isFree, String type, String latitude, String longitude) {
        this.status = new Status(isAvailable, isClean, isPaper, isSoap);
        this.name = name;
        this.isFree = isFree;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsFree() {
        return isFree;
    }

    public void setIsFree(String isFree) {
        this.isFree = isFree;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", isFree='" + isFree + '\'' +
                ", type='" + type + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", isAvailable='" + getAvailable() + '\'' +
                ", isClean='" + getClean() + '\'' +
                ", isPaper='" + getPaper() + '\'' +
                ", isSoap='" + getSoap() + '\'' +
                '}';
    }
}

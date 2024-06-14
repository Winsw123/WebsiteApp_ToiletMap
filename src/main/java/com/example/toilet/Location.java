package com.example.toilet;

public class Location {
    private String name;
    private String isFree;
    private String type;
    private String latitude;
    private String longitude;

    Status status = new Status();

    public Location() {
    }

    public Location(String name, String isFree, String type, String latitude, String longitude) {
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


    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", isFree='" + isFree + '\'' +
                ", type='" + type + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                status.toString() +
                '}';
    }
}

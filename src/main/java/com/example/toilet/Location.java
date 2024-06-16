package com.example.toilet;

public class Location {
    private String name;
    private String type;
    private boolean isFree;
    private boolean isAvailable;
    private boolean isClean;
    private boolean isPaper;
    private boolean isSoap;
    private String longitude;
    private String latitude;

    // Constructors
    public Location(String name, String type, boolean isFree, boolean isAvailable, boolean isClean
                    , boolean isPaper, boolean isSoap, String longitude, String latitude) {
        this.name = name;
        this.type = type;
        this.isFree = isFree;
        this.longitude = longitude;
        this.latitude = latitude;
        this.isAvailable = isAvailable;
        this.isClean = isClean;
        this.isPaper = isPaper;
        this.isSoap = isSoap;
    }

    // Getters and Setters
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

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean isFree) {
        this.isFree = isFree;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public boolean isClean() {
        return isClean;
    }

    public void setClean(boolean isClean) {
        this.isClean = isClean;
    }

    public boolean isPaper() {
        return isPaper;
    }

    public void setPaper(boolean isPaper) {
        this.isPaper = isPaper;
    }

    public boolean isSoap() {
        return isSoap;
    }

    public void setSoap(boolean isSoap) {
        this.isSoap = isSoap;
    }

    // toString method (for logging and debugging)
    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", isFree=" + isFree +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", isAvailable=" + isAvailable +
                ", isClean=" + isClean +
                ", isPaper=" + isPaper +
                ", isSoap=" + isSoap +
                '}';
    }
}

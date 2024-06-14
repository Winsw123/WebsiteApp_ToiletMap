package com.example.toilet;

public class Status {
    private String isAvailable;
    private String isClean;
    private String isPaper;
    private String isSoap;

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getIsClean() {
        return isClean;
    }

    public void setIsClean(String isClean) {
        this.isClean = isClean;
    }

    public String getIsPaper() {
        return isPaper;
    }

    public void setIsPaper(String isPaper) {
        this.isPaper = isPaper;
    }

    public String getIsSoap() {
        return isSoap;
    }

    public void setIsSoap(String isSoap) {
        this.isSoap = isSoap;
    }

    @Override
    public String toString() {
        return "Status{" +
                "isAvailable='" + isAvailable + '\'' +
                ", isClean='" + isClean + '\'' +
                ", isPaper='" + isPaper + '\'' +
                ", isSoap='" + isSoap + '\'' +
                '}';
    }
}

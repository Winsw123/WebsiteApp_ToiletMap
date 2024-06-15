package com.example.toilet;

public class Status {
    private Boolean isAvailable;
    private Boolean isClean;
    private Boolean isPaper;
    private Boolean isSoap;

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public Boolean getClean() {
        return isClean;
    }

    public void setClean(Boolean clean) {
        isClean = clean;
    }

    public Boolean getPaper() {
        return isPaper;
    }

    public void setPaper(Boolean paper) {
        isPaper = paper;
    }

    public Boolean getSoap() {
        return isSoap;
    }

    public void setSoap(Boolean soap) {
        isSoap = soap;
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

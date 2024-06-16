package com.example.toilet;

public class Status {
    private boolean isAvailable;
    private boolean isClean;
    private boolean isPaper;
    private boolean isSoap;

    public Status() {
    }

    public Status(boolean isAvailable, boolean isClean, boolean isPaper, boolean isSoap) {
        this.isAvailable = isAvailable;
        this.isClean = isClean;
        this.isPaper = isPaper;
        this.isSoap = isSoap;
    }

    public boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public boolean getClean() {
        return isClean;
    }

    public void setClean(boolean clean) {
        isClean = clean;
    }

    public boolean getPaper() {
        return isPaper;
    }

    public void setPaper(boolean paper) {
        isPaper = paper;
    }

    public boolean getSoap() {
        return isSoap;
    }

    public void setSoap(boolean soap) {
        isSoap = soap;
    }
}

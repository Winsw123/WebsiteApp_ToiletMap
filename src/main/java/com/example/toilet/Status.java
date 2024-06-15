package com.example.toilet;

public class Status {
    private boolean isAvailable;
    private boolean isClean;
    private boolean isPaper;
    private boolean isSoap;

    public Status(boolean isAvailable, boolean isClean, boolean isPaper, boolean isSoap) {
        this.isAvailable = isAvailable;
        this.isClean = isClean;
        this.isPaper = isPaper;
        this.isSoap = isSoap;
    }

    public Status() {
    }

    public boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public boolean getClean() {
        return isClean;
    }

    public void setClean(boolean clean) {
        this.isClean = clean;
    }

    public boolean getPaper() {
        return isPaper;
    }

    public void setPaper(boolean paper) {
        this.isPaper = paper;
    }

    public boolean getSoap() {
        return isSoap;
    }

    public void setSoap(boolean soap) {
        this.isSoap = soap;
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

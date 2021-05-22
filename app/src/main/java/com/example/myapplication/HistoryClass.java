package com.example.myapplication;

public class HistoryClass {
    private String dateTime;
    private int id;

    public HistoryClass(String dateTime, int id) {
        this.dateTime = dateTime;
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

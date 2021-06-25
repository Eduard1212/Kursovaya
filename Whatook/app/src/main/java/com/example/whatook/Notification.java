package com.example.whatook;

public class Notification {

    private long id;
    private String text;
    private String date;

    Notification(long id, String text, String date){
        this.id = id;
        this.text = text;
        this.date = date;
    }
    public long getId() {
        return id;
    }
    public String getText() {
        return text;
    }
    public String getDate() {
        return date;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
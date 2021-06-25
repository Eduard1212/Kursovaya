package com.example.whatook;

public class Recipe {

    private long id;
    private long userId;
    private String user;
    private String name;
    private int ttc;
    private String text;
    private int likes;
    private int uv;

    Recipe(long id, long userId, String user, String name, int ttc, String text, int likes, int uv){
        this.id = id;
        this.userId = userId;
        this.user = user;
        this.name = name;
        this.ttc = ttc;
        this.text = text;
        this.likes = likes;
        this.uv = uv;
    }
    public long getId() {
        return id;
    }
    public long getUserId() {
        return userId;
    }
    public String getUser() {
        return user;
    }
    public String getName() {
        return name;
    }
    public long getTTC() {
        return ttc;
    }
    public String getText() {
        return text;
    }
    public int getLikes() {
        return likes;
    }
    public int getUV() {
        return uv;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setTTC(int ttc) {
        this.ttc = ttc;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setLikes(int likes) {
        this.likes = likes;
    }
    public void setUV(int uv) {
        this.uv = uv;
    }
}
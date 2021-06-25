package com.example.whatook;

public class IngredientMeasure {

    private long id;
    private String name;

    IngredientMeasure(long id, String name){
        this.id = id;
        this.name = name;
    }
    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
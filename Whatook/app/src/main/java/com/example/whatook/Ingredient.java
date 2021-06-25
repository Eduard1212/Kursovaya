package com.example.whatook;

public class Ingredient {

    private long id;
    private long measureId;
    private String measure;
    private String name;

    Ingredient(long id, long measureId,String measure, String name){
        this.id = id;
        this.measureId = measureId;
        this.measure = measure;
        this.name = name;
    }
    public long getId() {
        return id;
    }
    public long getMeasureId() {
        return measureId;
    }
    public String getMeasure() {
        return measure;
    }
    public String getName() {
        return name;
    }

    public void setMeasureId(long measureId) {
        this.measureId = measureId;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setName(String name) {
        this.name = name;
    }
}
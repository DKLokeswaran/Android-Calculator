package com.example.calculator;

public class Unit {

    private String id,name;
    private double key;

    public Unit(String name,String id, double key) {
        this.id = id;
        this.name=name;
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public double getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}

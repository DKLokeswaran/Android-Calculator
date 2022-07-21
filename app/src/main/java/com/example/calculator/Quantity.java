package com.example.calculator;

import java.util.ArrayList;

public class Quantity {

    private ArrayList<Unit> units;
    private String name;

    public Quantity(String name,ArrayList<Unit> units) {
        this.name=name;
        this.units = units;
    }

    public double convert(double input,int src,int dest){
        return input*(units.get(dest).getKey()/units.get(src).getKey());
    }

    public String getName() {
        return name;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }
}

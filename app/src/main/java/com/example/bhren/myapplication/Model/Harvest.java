package com.example.bhren.myapplication.Model;

public class Harvest {
    private String kilo;
    private String glasses;

    public Harvest() {

    }

    public Harvest(String kilo, String glasses) {
        this.kilo = kilo;
        this.glasses = glasses;
    }

    public String getKilo() {
        return kilo;
    }

    public void setKilo(String kilo) {
        this.kilo = kilo;
    }

    public String getGlasses() {
        return glasses;
    }

    public void setGlasses(String glasses) {
        this.glasses = glasses;
    }
}
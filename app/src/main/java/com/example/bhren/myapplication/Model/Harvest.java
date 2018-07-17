package com.example.bhren.myapplication.Model;

public class Harvest {
    private String kilo;
    private String bigGlasses;
    private String smallGlasses;

    public Harvest() {
    }

    public Harvest(String kilo, String bigGlasses, String smallGlasses) {
        this.kilo = kilo;
        this.bigGlasses = bigGlasses;
        this.smallGlasses = smallGlasses;
    }


    public String getKilo() {
        return kilo;
    }

    public void setKilo(String kilo) {
        this.kilo = kilo;
    }

    public String getBigGlasses() {
        return bigGlasses;
    }

    public void setBigGlasses(String bigGlasses) {
        this.bigGlasses = bigGlasses;
    }

    public String getSmallGlasses() {
        return smallGlasses;
    }

    public void setSmallGlasses(String smallGlasses) {
        this.smallGlasses = smallGlasses;
    }
}
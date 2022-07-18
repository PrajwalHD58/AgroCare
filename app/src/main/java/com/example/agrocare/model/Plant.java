package com.example.agrocare.model;

public class Plant {
    private String plantTitle;
    private String plantCountry;
    private String plantGrowth;
    private int plantPicture;

    public Plant(String plantTitle, String plantCountry, String plantGrowth,int plantPicture) {
        this.plantTitle = plantTitle;
        this.plantCountry = plantCountry;
        this.plantGrowth = plantGrowth;
        this.plantPicture = plantPicture;
    }

    public Plant() {}

    public String getPlantTitle() {
        return plantTitle;
    }

    public void setPlantTitle(String plantTitle) {
        this.plantTitle = plantTitle;
    }

    public String getPlantCountry() {
        return plantCountry;
    }

    public void setPlantCounty(String plantCountry) {
        this.plantCountry = plantCountry;
    }

    public String getPlantGrowth() {
        return plantGrowth;
    }

    public void setPlantGrowth(String plantPrice) {
        this.plantGrowth = plantPrice;
    }

    public int getPlantPicture() {
        return plantPicture;
    }

    public void setPlantPicture(int plantPicture) {
        this.plantPicture = plantPicture;
    }
}

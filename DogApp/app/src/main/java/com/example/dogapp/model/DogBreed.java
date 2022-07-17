package com.example.dogapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DogBreed implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("life_span")
    private String lifeSpan;

    @SerializedName("origin")
    private String origin;

    @SerializedName("bred_for")
    private String bredFor;

    @SerializedName("breed_group")
    private String breedGroup;

    @SerializedName("url")
    private String url;

    @SerializedName("temperament")
    private String temperament;

    @SerializedName("weight")
    private HeightWeightData weight;

    @SerializedName("height")
    private HeightWeightData height;

    public DogBreed(int id, String name, String lifeSpan, String origin,String bredFor, String url, String temperament, String breedGroup, String height, String weight) {
        this.id = id;
        this.name = name;
        this.lifeSpan = lifeSpan;
        this.origin = origin;
        this.url = url;
        this.bredFor = bredFor;
        this.temperament = temperament;
        this.breedGroup = breedGroup;
        this.height.metric = height;
        this.weight.metric = weight;
    }

    public class HeightWeightData{
        @SerializedName("imperial")
        private String imperial;

        @SerializedName("metric")
        private String metric;
    }

    public String getWeight() {
        return weight.metric;
    }

    public void setWeight(String weight) {
        this.weight.metric = weight;
    }

    public String getHeight() {
        return height.metric;
    }

    public void setHeight(String height) {
        this.height.metric = height;
    }

    public String getBreedGroup() {
        return breedGroup;
    }

    public void setBreedGroup(String breedGroup) {
        this.breedGroup = breedGroup;
    }

    public String getTemperament() {
        return temperament;
    }

    public void setTemperament(String temperament) {
        this.temperament = temperament;
    }

    public String getBredFor() {
        return bredFor;
    }

    public void setBredFor(String bredFor) {
        this.bredFor = bredFor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(String lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

package com.example.networking;

public class Mountain <string> {
    private String name;
    private String location;
    private int height;

    public Mountain(String name, int height, String location) {
        this.name=name;
        this.height=height;
        this.location=location;
    }

    public String getName(String name) {
        return this.name;
    }
    public int getHeight(String height) { return this.height; }
    public String getLocation(String location) { return this.location; }

    @Override
    public String toString(){return name;}
}

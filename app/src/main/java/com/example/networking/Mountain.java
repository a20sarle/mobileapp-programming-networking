package com.example.networking;

public class Mountain {
    private String ID;
    private String name;
    private String type;
    private String company;
    private String location;
    private String category;
    private int height;
    private String description;

    private int size;
    private int cost;

    private Auxdata auxdata;

    public Mountain(String name, int height, String description) {
        this.name=name;
        this.height=height;
        this.description=description;
    }

    public String getName(String name) {
        return this.name;
    }
    public int getHeight(String height) { return this.height; }
    public String getDescription(String description) { return this.description; }

    public String getLocation() {
        return location;
    }
    public int getSize(){return size; }
    public Auxdata getAuxdata() {
        return auxdata;
    }

    @Override
    public String toString(){return name;}
}

package com.example.szermil.restaurant_search.model;

public class Restaurant {
    private long id;
    private String name;
    private String locality;

    public Restaurant(long id, String name, String locality) {
        this.id = id;
        this.name = name;
        this.locality = locality;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }
}

package com.example.anik.shop.helpers;

/**
 * Created by Anik on 11-Aug-15, 011.
 */
public class SubCategory {
    private String id, name, imageUrl;

    public SubCategory(String id, String name, String url) {
        this.id = id;
        this.name = name;
        this.imageUrl = url;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

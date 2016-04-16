package com.example.anik.shop.helpers;

/**
 * Created by Anik on 07-Aug-15, 007.
 */
public class Category {
    private int categoryId;
    private String categoryName;
    private String categoryImageUrl;

    public Category(int categoryId, String categoryName, String categoryImageUrl) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryImageUrl = categoryImageUrl;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryImageUrl() {
        return categoryImageUrl;
    }
}

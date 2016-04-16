package com.example.anik.shop.helpers;

import java.io.Serializable;

/**
 * Created by Anik on 14-Aug-15, 014.
 */
public class Offer implements Serializable {
    private String id;
    private String title;
    private String description;
    private String price;
    private String percentage;
    private String startDate;
    private String endDate;
    private String link;
    private String youtube;
    private String imageLink;

    public Offer(String id, String title, String description, String price, String percentage, String startDate, String endDate, String link, String youtube, String imageLink) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.percentage = percentage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.link = link;
        this.youtube = youtube;
        this.imageLink = imageLink;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getImageLink() {
        return imageLink;
    }
}

package com.example.anik.agent.helpers;

/**
 * Created by Anik on 15-Aug-15, 015.
 */
public class Shop {

    private String id;
    private String companyName;
    private String ownerName;
    private String mobileNumber;

    public Shop(String id, String companyName, String ownerName, String mobileNumber) {
        this.id = id;
        this.companyName = companyName;
        this.ownerName = ownerName;
        this.mobileNumber = mobileNumber;
    }

    public String getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }
}

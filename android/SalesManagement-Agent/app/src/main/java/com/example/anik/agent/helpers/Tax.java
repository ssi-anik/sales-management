package com.example.anik.agent.helpers;

import java.io.Serializable;

/**
 * Created by Anik on 14-Aug-15, 014.
 */
public class Tax implements Serializable {
    private String name;
    private String percentage;

    public Tax(String name, String percentage) {
        this.name = name;
        this.percentage = percentage;
    }

    public String getName() {
        return name;
    }

    public String getPercentage() {
        return percentage;
    }
}

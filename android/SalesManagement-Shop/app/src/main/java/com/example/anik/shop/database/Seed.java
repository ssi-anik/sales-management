package com.example.anik.shop.database;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anik on 07-Aug-15, 007.
 */
public class Seed {
    private Map<String, String> seed = new HashMap<>();
    private String seed_table_users = "INSERT INTO users (mobile_number, is_activated, activation_key, profile_picture) VALUES ('01515607083', 1, 1544, 'http://mockup.in/sales-management/agent_images/agent_424dea7dd02d644222a5f5adc941dd7f.png')";

    public Seed() {
        seed.put("seed_table_users", seed_table_users);
    }

    public Map<String, String> getSeed() {
        return seed;
    }
}

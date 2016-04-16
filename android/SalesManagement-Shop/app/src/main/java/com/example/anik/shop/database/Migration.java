package com.example.anik.shop.database;

import java.util.HashMap;
import java.util.Map;

public class Migration {

    String table_complaints = "CREATE TABLE IF NOT EXISTS complaints ( " +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "title TEXT NOT NULL DEFAULT '', " +
            "description TEXT NOT NULL DEFAULT '', " +
            "photo_path TEXT NOT NULL DEFAULT '', " +
            "tracking_number TEXT NOT NULL, " +
            "is_submitted INTEGER NOT NULL DEFAULT 0, " +
            "is_solved INTEGER NOT NULL DEFAULT 0, " +
            "solution TEXT NOT NULL DEFAULT '', " +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            ")";

    String table_order_details = "CREATE TABLE IF NOT EXISTS order_details ( " +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "order_for TEXT NOT NULL, " +
            "company_name TEXT NOT NULL DEFAULT '', " +
            "product_id INTEGER NOT NULL, " +
            "product_name TEXT NOT NULL, " +
            "quantity INTEGER NOT NULL, " +
            "price REAL NOT NULL, " +
            "total_price REAL NOT NULL, " +
            "is_submitted INTEGER NOT NULL DEFAULT 0, " +
            "is_received INTEGER NOT NULL DEFAULT 0, " +
            "order_remarks TEXT NOT NULL DEFAULT '', " +
            "batch TEXT NOT NULL " +
            ")";

    String table_bill_details = "CREATE TABLE IF NOT EXISTS bill_details ( " +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "order_for TEXT NOT NULL, " +
            "company_name TEXT NOT NULL DEFAULT '', " +
            "product_id INTEGER NOT NULL, " +
            "product_name TEXT NOT NULL, " +
            "quantity INTEGER NOT NULL, " +
            "price REAL NOT NULL, " +
            "total_price REAL NOT NULL, " +
            "is_submitted INTEGER NOT NULL DEFAULT 0, " +
            "is_ready INTEGER NOT NULL DEFAULT 1, " +
            "bill_remarks TEXT NOT NULL DEFAULT '', " +
            "bill_key TEXT NOT NULL " +
            ")";

    String table_quick_order_details = "CREATE TABLE IF NOT EXISTS quick_orders ( " +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "order_for TEXT NOT NULL, " +
            "company_name TEXT NOT NULL DEFAULT '', " +
            "product_id INTEGER NOT NULL, " +
            "product_name TEXT NOT NULL, " +
            "quantity INTEGER NOT NULL, " +
            "price REAL NOT NULL, " +
            "total_price REAL NOT NULL, " +
            "is_submitted INTEGER NOT NULL DEFAULT 0, " +
            "is_received INTEGER NOT NULL DEFAULT 0, " +
            "order_remarks TEXT NOT NULL DEFAULT '', " +
            "batch TEXT NOT NULL " +
            ")";


    String table_activity_log = "CREATE TABLE IF NOT EXISTS activity_log ( " +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "reference_id INTEGER NOT NULL, " +
            "reference_type TEXT NOT NULL, " +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP " +
            ")";


    String table_users = "CREATE TABLE IF NOT EXISTS users ( " +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT NOT NULL DEFAULT '', " +
            "mobile_number TEXT NOT NULL, " +
            "agent_mobile_number TEXT NOT NULL, " +
            "email TEXT NOT NULL DEFAULT '', " +
            "address TEXT NOT NULL DEFAULT '', " +
            "is_activated INTEGER NOT NULL DEFAULT 0, " +
            "activation_key INTEGER NOT NULL DEFAULT 0, " +
            "bonus REAL NOT NULL DEFAULT 0.0, " +
            "profile_picture TEXT NOT NULL DEFAULT '', " +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
            "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            ") ";

    String table_trackers = "CREATE TABLE IF NOT EXISTS trackers ( " +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "latitude REAL NOT NULL, " +
            "longitude REAL NOT NULL, " +
            "captured_time TIMESTAMP NOT NULL, " +
            "is_submitted INTEGER NOT NULL DEFAULT 0 ," +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
            "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            ")";

    private Map<String, String> schema;

    public Migration() {
        schema = new HashMap<>();
        schema.put("user", table_users);
        schema.put("trackers", table_trackers);
        schema.put("complaints", table_complaints);
        schema.put("order_details", table_order_details);
        schema.put("bill_details", table_bill_details);
        schema.put("quick_orders", table_quick_order_details);
        schema.put("activity_log", table_activity_log);
    }

    public Map<String, String> getSchema() {
        return this.schema;
    }

}

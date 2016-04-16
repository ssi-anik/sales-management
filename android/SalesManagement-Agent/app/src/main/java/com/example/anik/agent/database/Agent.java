package com.example.anik.agent.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anik on 05-Aug-15, 005.
 */
public class Agent extends Database {
    SQLiteDatabase db;
    Cursor cursor;
    String table;

    public Agent(Context context) {
        super(context);
        this.table = "users";
    }

    public void closeConnection() {
        if (this.cursor != null)
            this.cursor.close();
        this.db.close();
    }

    public Map<String, String> getUser() {
        this.db = getReadableDatabase();
        String query = String.format("SELECT * FROM %s", this.table);
        this.cursor = db.rawQuery(query, null);

        if (cursor == null || !cursor.moveToFirst() || cursor.getCount() == 0) {
            this.closeConnection();
            return null;
        }
        int id = cursor.getInt(cursor.getColumnIndex("_id"));
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String mobile_number = cursor.getString(cursor.getColumnIndex("mobile_number"));
        String email = cursor.getString(cursor.getColumnIndex("email"));
        String address = cursor.getString(cursor.getColumnIndex("address"));
        int is_activated = cursor.getInt(cursor.getColumnIndex("is_activated"));
        int activation_key = cursor.getInt(cursor.getColumnIndex("activation_key"));
        String bonus = String.valueOf(cursor.getDouble(cursor.getColumnIndex("bonus")));
        String profile_picture = cursor.getString(cursor.getColumnIndex("profile_picture"));
        String created_at = cursor.getString(cursor.getColumnIndex("created_at"));
        String updated_at = cursor.getString(cursor.getColumnIndex("updated_at"));
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(id));
        map.put("name", name);
        map.put("mobile_number", mobile_number);
        map.put("email", email);
        map.put("address", address);
        map.put("is_activated", String.valueOf(is_activated));
        map.put("activation_key", String.valueOf(activation_key));
        map.put("bonus", bonus);
        map.put("profile_picture", profile_picture);
        map.put("created_at", created_at);
        map.put("updated_at", updated_at);
        this.closeConnection();
        return map;
    }

    public boolean insert(Map<String, String> values) {
        this.db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (Map.Entry<String, String> map : values.entrySet()) {
            contentValues.put(map.getKey(), map.getValue());
        }
        int rowId = (int) this.db.insert(this.table, null, contentValues);
        this.closeConnection();
        return rowId > 0;
    }

    public boolean update(Map<String, String> values) {
        this.db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (Map.Entry<String, String> map : values.entrySet()) {
            contentValues.put(map.getKey(), map.getValue());
        }
        contentValues.put("updated_at", "CURRENT_TIMESTAMP");
        int updated_row_id = (int) this.db.update(this.table, contentValues, "_id = 1", null);
        this.closeConnection();
        return updated_row_id > 0;
    }
}

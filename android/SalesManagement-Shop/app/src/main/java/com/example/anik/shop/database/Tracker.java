package com.example.anik.shop.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Anik on 07-Aug-15, 007.
 */
public class Tracker extends Database {

    private Context context;
    private SQLiteDatabase db;
    private Cursor cursor = null;
    private String table = "trackers";

    public Tracker(Context context) {
        super(context);
        this.context = context;
    }

    private void closeConnection() {
        if (this.cursor != null) {
            this.cursor.close();
        }
        this.db.close();
    }

    public boolean insert(double latitude, double longitude, String now) {
        this.db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("latitude", latitude);
        contentValues.put("longitude", longitude);
        contentValues.put("captured_time", now);
        int rowId = (int) this.db.insert(this.table, null, contentValues);
        this.closeConnection();
        return rowId > 0;
    }

    public List<Map<String, String>> unsentLocations() {
        String query = String.format("SELECT * FROM %s WHERE is_submitted = 0", table);
        db = getReadableDatabase();
        cursor = db.rawQuery(query, null);
        if (null == cursor || !cursor.moveToFirst()) {
            closeConnection();
            return null;
        }
        List<Map<String, String>> locations = new ArrayList<>();
        do {
            Map<String, String> row = new HashMap<>();
            String id = cursor.getString(cursor.getColumnIndex("_id"));
            String latitude = cursor.getString(cursor.getColumnIndex("latitude"));
            String longitude = cursor.getString(cursor.getColumnIndex("longitude"));
            String time = cursor.getString(cursor.getColumnIndex("captured_time"));
            row.put("id", id);
            row.put("latitude", latitude);
            row.put("longitude", longitude);
            row.put("time", time);
            locations.add(row);
        } while (cursor.moveToNext());

        closeConnection();
        return locations;
    }

    public boolean updateSubmissionStatus(String id) {
        db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("is_submitted", 1);
        String whereClause = String.format("_id = %s", id);
        int row = db.update(table, contentValues, whereClause, null);
        closeConnection();
        return row > 0;
    }
}

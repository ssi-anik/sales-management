package com.example.anik.agent.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Anik on 24-Aug-15, 024.
 */
public class ActivityLog extends Database {

    private String table = "activity_log";
    private Context context;
    private String id;
    private String referenceId;
    private String referenceType;
    private String postedOn;
    private SQLiteDatabase db;
    private Cursor cursor;

    public ActivityLog(Context context, String id, String referenceId, String referenceType, String postedOn) {
        super(context);
        this.context = context;
        this.id = id;
        this.referenceId = referenceId;
        this.referenceType = referenceType;
        this.postedOn = postedOn;
    }

    public ActivityLog(Context context) {
        super(context);
        this.context = context;
        this.id = "";
        this.referenceId = "";
        this.referenceType = "";
        this.postedOn = "";
    }

    public String getId() {
        return id;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public String getPostedOn() {
        return postedOn;
    }

    private void closeConnection() {
        if (null != cursor && !cursor.isClosed()) {
            cursor.close();
        }
        db.close();
    }

    public List<ActivityLog> all() {
        String query = String.format("SELECT * FROM %s ORDER BY created_at DESC", table);
        db = getReadableDatabase();
        cursor = db.rawQuery(query, null);
        if (null == cursor || !cursor.moveToFirst()) {
            closeConnection();
            return null;
        }
        List<ActivityLog> all = new ArrayList<>();
        do {
            String id = cursor.getString(cursor.getColumnIndex("_id"));
            String referenceId = cursor.getString(cursor.getColumnIndex("reference_id"));
            String referenceType = cursor.getString(cursor.getColumnIndex("reference_type"));
            String postedOn = cursor.getString(cursor.getColumnIndex("created_at"));
            all.add(new ActivityLog(context, id, referenceId, referenceType, postedOn));
        } while (cursor.moveToNext());
        closeConnection();
        return all;
    }

    public boolean insert(String referenceId, String referenceType) {
        db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("reference_id", referenceId);
        contentValues.put("reference_type", referenceType);
        long rowInserted = db.insert(table, null, contentValues);
        closeConnection();
        return rowInserted > 0;
    }

    public boolean update(Map<String, String> fieldValues, String onField, String value) {
        db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (Map.Entry<String, String> field : fieldValues.entrySet()) {
            contentValues.put(field.getKey(), field.getValue());
        }
        String whereClause = String.format("%s = %s", onField, value);
        int rowsUpdated = db.update(table, contentValues, whereClause, null);
        closeConnection();
        return rowsUpdated > 0;
    }
}

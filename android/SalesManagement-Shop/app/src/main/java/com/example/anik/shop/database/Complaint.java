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
 * Created by Anik on 14-Aug-15, 014.
 */
public class Complaint extends Database {
    private String id;
    private String title;
    private String description;
    private String photo_path;
    private String tracking_number;
    private String is_submitted;
    private String is_solved;
    private String solution;
    private String created_at;
    private Context context;
    private SQLiteDatabase db;
    private Cursor cursor;
    private String table = "complaints";

    public Complaint(Context context) {
        super(context);
        this.context = context;
    }

    public Complaint(Context context, String id, String title, String description, String photo_path, String tracking_number, String is_submitted, String is_solved, String solution, String created_at) {
        super(context);
        this.id = id;
        this.title = title;
        this.description = description;
        this.photo_path = photo_path;
        this.tracking_number = tracking_number;
        this.is_submitted = is_submitted;
        this.is_solved = is_solved;
        this.solution = solution;
        this.created_at = created_at;
    }

    public List<Complaint> all() {
        List<Complaint> complaintList = new ArrayList<>();
        db = getReadableDatabase();
        String query = String.format("SELECT * FROM %s ORDER BY created_at DESC", table);
        cursor = db.rawQuery(query, null);
        if (null == cursor || !cursor.moveToFirst()) {
            closeCursor();
            closeDatabase();
            return null;
        }
        do {
            complaintList.add(this.processRetrievedData(cursor));
        } while (cursor.moveToNext());
        closeDatabase();
        closeCursor();
        return complaintList;
    }

    public boolean insert(Map<String, String> values) {
        db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (Map.Entry<String, String> data : values.entrySet()) {
            contentValues.put(data.getKey(), data.getValue());
        }
        long row = db.insert(table, null, contentValues);
        String referenceId = values.get("tracking_number");
        String referenceType = "complaint";
        new ActivityLog(this.context).insert(referenceId, referenceType);
        closeCursor();
        closeDatabase();
        return row > 0;
    }

    public void updateSubmission(String tracking_number) {
        db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("is_submitted", "1");
        db.update(table, contentValues, String.format("tracking_number = '%s'", tracking_number), null);
        closeCursor();
        closeDatabase();
    }

    public Complaint get(String id) {
        db = getReadableDatabase();
        String query = String.format("SELECT * FROM %s WHERE _id = %s", table, id);
        cursor = db.rawQuery(query, null);
        if (null == cursor || !cursor.moveToFirst()) {
            return null;
        }
        Complaint complaint = processRetrievedData(cursor);
        closeCursor();
        closeDatabase();
        return complaint;
    }

    public Complaint getByTrackingNumber(String tracking_number) {
        db = getReadableDatabase();
        String query = String.format("SELECT * FROM %s WHERE tracking_number = '%s'", table, tracking_number);
        cursor = db.rawQuery(query, null);
        if (null == cursor || !cursor.moveToFirst()) {
            return null;
        }
        Complaint complaint = processRetrievedData(cursor);
        closeCursor();
        closeDatabase();
        return complaint;
    }

    private void closeDatabase() {
        db.close();
    }

    private void closeCursor() {
        if (null != cursor && !cursor.isClosed())
            cursor.close();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPhotoPath() {
        return photo_path;
    }

    public String getTrackingNumber() {
        return tracking_number;
    }

    public String getIsSubmitted() {
        return is_submitted;
    }

    public String getIsSolved() {
        return is_solved;
    }

    public String getSolution() {
        return solution;
    }

    public String getCreatedAt() {
        return created_at;
    }

    private Complaint processRetrievedData(Cursor cursor) {
        String id = String.valueOf(cursor.getInt(cursor.getColumnIndex("_id")));
        String title = cursor.getString(cursor.getColumnIndex("title"));
        String description = cursor.getString(cursor.getColumnIndex("description"));
        String photo_path = cursor.getString(cursor.getColumnIndex("photo_path"));
        String tracking_number = cursor.getString(cursor.getColumnIndex("tracking_number"));
        String is_submitted = String.valueOf(cursor.getInt(cursor.getColumnIndex("is_submitted")));
        String is_solved = String.valueOf(cursor.getInt(cursor.getColumnIndex("is_solved")));
        String solution = cursor.getString(cursor.getColumnIndex("solution"));
        String created_at = cursor.getString(cursor.getColumnIndex("created_at"));
        return new Complaint(this.context, id, title, description, photo_path, tracking_number, is_submitted, is_solved, solution, created_at);
    }

    public List<Map<String, String>> getUnsentComplaints() {
        String query = String.format("SELECT * FROM %s where is_submitted = 0 and title <> ''", table);
        db = getReadableDatabase();
        cursor = db.rawQuery(query, null);
        if (null == cursor || !cursor.moveToFirst()) {
            closeCursor();
            closeDatabase();
            return null;
        }

        List<Map<String, String>> unsentComplaintList = new ArrayList<>();

        do {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            String photo_path = cursor.getString(cursor.getColumnIndex("photo_path"));
            String tracking_number = cursor.getString(cursor.getColumnIndex("tracking_number"));
            Map<String, String> row = new HashMap<>();
            row.put("title", title);
            row.put("description", description);
            row.put("photo_path", photo_path);
            row.put("tracking_number", tracking_number);
            unsentComplaintList.add(row);
        } while (cursor.moveToNext());
        closeCursor();
        closeDatabase();
        return unsentComplaintList;
    }

    public List<String> getUnsolvedComplaints() {
        String query = String.format("SELECT tracking_number FROM %s WHERE is_solved = 0", table);
        db = getReadableDatabase();
        cursor = db.rawQuery(query, null);
        if (null == cursor || !cursor.moveToFirst()) {
            closeCursor();
            closeDatabase();
            return null;
        }
        List<String> unsolvedComplaints = new ArrayList<>();
        do {
            String tracking_number = cursor.getString(cursor.getColumnIndex("tracking_number"));
            unsolvedComplaints.add(tracking_number);
        } while (cursor.moveToNext());
        closeCursor();
        closeDatabase();
        return unsolvedComplaints;
    }

    public boolean updateSolution(String tracking_number, String solution) {
        db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("solution", solution);
        contentValues.put("is_solved", 1);
        String whereClause = String.format("tracking_number = '%s'", tracking_number);
        int updatedRows = db.update(table, contentValues, whereClause, null);
        closeCursor();
        closeDatabase();
        return updatedRows > 0;
    }

    public List<Map<String, String>> getUncommittedPhotoComplaints() {
        String query = String.format("SELECT * FROM %s where is_submitted = 0 and photo_path <> ''", table);
        db = getReadableDatabase();
        cursor = db.rawQuery(query, null);
        if (null == cursor || !cursor.moveToFirst()) {
            closeCursor();
            closeDatabase();
            return null;
        }

        List<Map<String, String>> unsentComplaintList = new ArrayList<>();

        do {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            String photo_path = cursor.getString(cursor.getColumnIndex("photo_path"));
            String tracking_number = cursor.getString(cursor.getColumnIndex("tracking_number"));
            Map<String, String> row = new HashMap<>();
            row.put("title", title);
            row.put("description", description);
            row.put("photo_path", photo_path);
            row.put("tracking_number", tracking_number);
            unsentComplaintList.add(row);
        } while (cursor.moveToNext());
        closeCursor();
        closeDatabase();
        return unsentComplaintList;
    }
}

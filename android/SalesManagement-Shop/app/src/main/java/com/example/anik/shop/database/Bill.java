package com.example.anik.shop.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.anik.shop.helpers.AppConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Anik on 15-Aug-15, 015.
 */
public class Bill extends Database {

    private Context context;
    private String id;
    private String productId;
    private String productName;
    private String companyName;
    private String companyMobileNumber;
    private String quantity;
    private String price;
    private String totalPrice;
    private String isSubmitted;
    private String isReady;
    private String billRemarks;
    private String billKey;

    private SQLiteDatabase db;
    private Cursor cursor;
    private String table = "bill_details";

    public Bill(Context context) {
        super(context);
        this.context = context;
    }

    public Bill(Context context, String id, String product_id, String quantity, String price, String total_price, String is_submitted, String is_received, String billRemarks, String billKey) {
        this(context);
        this.id = id;
        this.productId = product_id;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = total_price;
        this.isSubmitted = is_submitted;
        this.isReady = is_received;
        this.billRemarks = billRemarks;
        this.billKey = billKey;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyMobileNumber() {
        return companyMobileNumber;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getIsSubmitted() {
        return isSubmitted;
    }

    public String getIsReady() {
        return isReady;
    }

    public String getBillRemarks() {
        return billRemarks;
    }

    public String getBillKey() {
        return billKey;
    }

    public List<Bill> all() {
        String query = "SELECT * FROM " + table;
        db = getReadableDatabase();
        cursor = db.rawQuery(query, null);
        if (null == cursor || !cursor.moveToFirst()) {
            closeCursor();
            closeDatabase();
            return null;
        }
        List<Bill> orders = new ArrayList<>();
        do {
            String id = cursor.getString(cursor.getColumnIndex("_id"));
            String productId = cursor.getString(cursor.getColumnIndex("product_id"));
            String quantity = cursor.getString(cursor.getColumnIndex("quantity"));
            String price = cursor.getString(cursor.getColumnIndex("price"));
            String totalPrice = cursor.getString(cursor.getColumnIndex("total_price"));
            String isSubmitted = cursor.getString(cursor.getColumnIndex("is_submitted"));
            String isReceived = cursor.getString(cursor.getColumnIndex("is_received"));
            String orderRemarks = cursor.getString(cursor.getColumnIndex("order_remarks"));
            String billKey = cursor.getString(cursor.getColumnIndex("bill_key"));
            orders.add(new Bill(context, id, productId, quantity, price, totalPrice, isSubmitted, isReceived, orderRemarks, billKey));
        } while (cursor.moveToNext());
        closeCursor();
        closeDatabase();
        return orders;
    }

    public List<Map<String, String>> groupBy() {
        String query = "SELECT _id, bill_key, company_name, ( COUNT(price) || ' items selected' )  as items, ( 'Rs. ' || SUM(total_price)) as total, is_submitted as submission_status from " + this.table + " where is_ready = 1 GROUP BY bill_key ORDER BY _id DESC";
        db = getReadableDatabase();
        cursor = db.rawQuery(query, null);
        if (null == cursor || !cursor.moveToFirst()) {
            closeCursor();
            closeDatabase();
            return null;
        }

        List<Map<String, String>> rows = new ArrayList<>();
        do {
            String billKey = cursor.getString(cursor.getColumnIndex("bill_key"));
            String items = cursor.getString(cursor.getColumnIndex("items"));
            String total = cursor.getString(cursor.getColumnIndex("total"));
            String company_name = cursor.getString(cursor.getColumnIndex("company_name"));
            String submission_status = cursor.getString(cursor.getColumnIndex("submission_status"));
            Map<String, String> row = new HashMap<>();
            row.put("id", id);
            row.put("billKey", billKey);
            row.put("items", items);
            row.put("total", total);
            row.put("company_name", company_name);
            row.put("submission_status", submission_status);
            rows.add(row);
        } while (cursor.moveToNext());
        closeCursor();
        closeDatabase();
        return rows;
    }

    public List<Map<String, String>> groupByUnsent() {
        String query = "SELECT bill_key from " + this.table + " where is_submitted = 0 GROUP BY bill_key ORDER BY _id DESC";
        db = getReadableDatabase();
        cursor = db.rawQuery(query, null);
        if (null == cursor || !cursor.moveToFirst()) {
            closeCursor();
            closeDatabase();
            return null;
        }

        List<Map<String, String>> rows = new ArrayList<>();
        do {
            String bill_key = cursor.getString(cursor.getColumnIndex("bill_key"));
            Map<String, String> row = new HashMap<>();
            row.put("bill_key", bill_key);
            rows.add(row);
        } while (cursor.moveToNext());
        closeCursor();
        closeDatabase();
        return rows;
    }

    public boolean insert(Map<String, String> data) {
        db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (Map.Entry<String, String> column : data.entrySet()) {
            contentValues.put(column.getKey(), column.getValue());
        }

        long rowId = db.insert(table, null, contentValues);
        closeCursor();
        closeDatabase();
        String referenceId = data.get("bill_key");
        String referenceType = data.containsKey("is_ready") ? "order" : "bill";
        //new ActivityLog(this.context).insert(referenceId, referenceType);
        closeCursor();
        closeDatabase();
        return rowId > 0;
    }

    public List<Bill> where(String column, String value) {
        String query = String.format("SELECT * FROM %s WHERE %s = '%s'", table, column, value);
        db = getReadableDatabase();
        cursor = db.rawQuery(query, null);
        if (null == cursor || !cursor.moveToFirst()) {
            closeCursor();
            closeDatabase();
            return null;
        }

        List<Bill> orders = new ArrayList<>();
        do {
            String id = cursor.getString(cursor.getColumnIndex("_id"));
            String productId = cursor.getString(cursor.getColumnIndex("product_id"));
            String productName = cursor.getString(cursor.getColumnIndex("product_name"));
            String companyName = cursor.getString(cursor.getColumnIndex("company_name"));
            String companyMobileNumber = cursor.getString(cursor.getColumnIndex("order_for"));
            String quantity = cursor.getString(cursor.getColumnIndex("quantity"));
            String price = cursor.getString(cursor.getColumnIndex("price"));
            String totalPrice = cursor.getString(cursor.getColumnIndex("total_price"));
            String isSubmitted = cursor.getString(cursor.getColumnIndex("is_submitted"));
            String isReady = cursor.getString(cursor.getColumnIndex("is_ready"));
            String billRemarks = cursor.getString(cursor.getColumnIndex("bill_remarks"));
            String billKey = cursor.getString(cursor.getColumnIndex("bill_key"));
            Bill order = new Bill(context, id, productId, quantity, price, totalPrice, isSubmitted, isReady, billRemarks, billKey);
            order.set(productName, companyName, companyMobileNumber);
            orders.add(order);
        } while (cursor.moveToNext());
        closeCursor();
        closeDatabase();
        return orders;
    }

    private void set(String productName, String companyName, String companyMobileNumber) {
        this.productName = productName;
        this.companyName = companyName;
        this.companyMobileNumber = companyMobileNumber;
    }

    private void closeDatabase() {
        db.close();
    }

    private void closeCursor() {
        if (null != cursor && !cursor.isClosed()) {
            cursor.close();
        }
    }

    public void updateSubmissionStatus(String bill_key, String product_id) {
        db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("is_submitted", 1);
        String clause = String.format("bill_key = '%s' and product_id = %s", bill_key, product_id);
        db.update(table, contentValues, clause, null);
        closeCursor();
        closeDatabase();
    }

    public boolean makeReady(String bill_key, String message) {
        db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("is_ready", 1);
        contentValues.put("bill_remarks", message);
        String whereClause = String.format("bill_key = '%s'", bill_key);
        int rowsUpdated = db.update(table, contentValues, whereClause, null);
        closeCursor();
        closeDatabase();
        return rowsUpdated > 0;
    }
}

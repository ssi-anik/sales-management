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
 * Created by Anik on 15-Aug-15, 015.
 */
public class Order extends Database {

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
    private String isReceived;
    private String orderRemarks;
    private String batch;

    private SQLiteDatabase db;
    private Cursor cursor;
    private String table = "order_details";

    public Order(Context context) {
        super(context);
        this.context = context;
    }

    public Order(Context context, String id, String product_id, String quantity, String price, String total_price, String is_submitted, String is_received, String order_remarks, String batch) {
        this(context);
        this.id = id;
        this.productId = product_id;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = total_price;
        this.isSubmitted = is_submitted;
        this.isReceived = is_received;
        this.orderRemarks = order_remarks;
        this.batch = batch;
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

    public String getIsReceived() {
        return isReceived;
    }

    public String getOrderRemarks() {
        return orderRemarks;
    }

    public String getBatch() {
        return batch;
    }

    public List<Order> all() {
        String query = "SELECT * FROM " + table;
        db = getReadableDatabase();
        cursor = db.rawQuery(query, null);
        if (null == cursor || !cursor.moveToFirst()) {
            closeCursor();
            closeDatabase();
            return null;
        }
        List<Order> orders = new ArrayList<>();
        do {
            String id = cursor.getString(cursor.getColumnIndex("_id"));
            String productId = cursor.getString(cursor.getColumnIndex("product_id"));
            String quantity = cursor.getString(cursor.getColumnIndex("quantity"));
            String price = cursor.getString(cursor.getColumnIndex("price"));
            String totalPrice = cursor.getString(cursor.getColumnIndex("total_price"));
            String isSubmitted = cursor.getString(cursor.getColumnIndex("is_submitted"));
            String isReceived = cursor.getString(cursor.getColumnIndex("is_received"));
            String orderRemarks = cursor.getString(cursor.getColumnIndex("order_remarks"));
            String batch = cursor.getString(cursor.getColumnIndex("batch"));
            orders.add(new Order(context, id, productId, quantity, price, totalPrice, isSubmitted, isReceived, orderRemarks, batch));
        } while (cursor.moveToNext());
        closeCursor();
        closeDatabase();
        return orders;
    }

    public List<Map<String, String>> groupBy() {
        String query = "SELECT _id, batch, company_name, ( COUNT(price) || ' items selected' )  as items, ( 'Rs. ' || SUM(total_price)) as total, is_submitted as submission_status from " + this.table + " GROUP BY batch ORDER BY _id DESC";
        db = getReadableDatabase();
        cursor = db.rawQuery(query, null);
        if (null == cursor || !cursor.moveToFirst()) {
            closeCursor();
            closeDatabase();
            return null;
        }

        List<Map<String, String>> rows = new ArrayList<>();
        do {
            String batch = cursor.getString(cursor.getColumnIndex("batch"));
            String items = cursor.getString(cursor.getColumnIndex("items"));
            String total = cursor.getString(cursor.getColumnIndex("total"));
            String company_name = cursor.getString(cursor.getColumnIndex("company_name"));
            String submission_status = cursor.getString(cursor.getColumnIndex("submission_status"));
            Map<String, String> row = new HashMap<>();
            row.put("id", id);
            row.put("batch", batch);
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
        String query = "SELECT batch from " + this.table + " where is_submitted = 0 GROUP BY batch ORDER BY _id DESC";
        db = getReadableDatabase();
        cursor = db.rawQuery(query, null);
        if (null == cursor || !cursor.moveToFirst()) {
            closeCursor();
            closeDatabase();
            return null;
        }

        List<Map<String, String>> rows = new ArrayList<>();
        do {
            String batch = cursor.getString(cursor.getColumnIndex("batch"));
            Map<String, String> row = new HashMap<>();
            row.put("batch", batch);
            rows.add(row);
        } while (cursor.moveToNext());
        closeCursor();
        closeDatabase();
        return rows;
    }

    public List<String> groupByNotReceived() {
        String query = "SELECT batch from " + this.table + " where is_received = 0 GROUP BY batch ORDER BY _id DESC";
        db = getReadableDatabase();
        cursor = db.rawQuery(query, null);
        if (null == cursor || !cursor.moveToFirst()) {
            closeCursor();
            closeDatabase();
            return null;
        }

        List<String> rows = new ArrayList<>();
        do {
            String batch = cursor.getString(cursor.getColumnIndex("batch"));
            rows.add(batch);
        } while (cursor.moveToNext());
        closeCursor();
        closeDatabase();
        return rows;
    }

    public boolean deleteOnReceive(String batch) {
        db = getWritableDatabase();
        String whereClause = String.format("batch = '%s'", batch);
        int rowsDeleted = db.delete(table, whereClause, null);
        closeCursor();
        closeDatabase();
        return rowsDeleted > 0;
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
        return rowId > 0;
    }

    public List<Order> where(String column, String value) {
        String query = String.format("SELECT * FROM %s WHERE %s = '%s'", table, column, value);
        db = getReadableDatabase();
        cursor = db.rawQuery(query, null);
        if (null == cursor || !cursor.moveToFirst()) {
            closeCursor();
            closeDatabase();
            return null;
        }

        List<Order> orders = new ArrayList<>();
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
            String isReceived = cursor.getString(cursor.getColumnIndex("is_received"));
            String orderRemarks = cursor.getString(cursor.getColumnIndex("order_remarks"));
            String batch = cursor.getString(cursor.getColumnIndex("batch"));
            Order order = new Order(context, id, productId, quantity, price, totalPrice, isSubmitted, isReceived, orderRemarks, batch);
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

    public void updateSubmissionStatus(String batch, String product_id) {
        db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("is_submitted", 1);
        String clause = String.format("batch = '%s' and product_id = %s", batch, product_id);
        db.update(table, contentValues, clause, null);
        closeCursor();
        closeDatabase();
    }
}

package com.example.anik.shop.broadcastListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.anik.shop.database.Bill;
import com.example.anik.shop.database.Order;
import com.example.anik.shop.helpers.AppConstant;
import com.example.anik.shop.helpers.AppHelper;
import com.example.anik.shop.helpers.AppNotification;
import com.example.anik.shop.helpers.AppStorage;
import com.example.anik.shop.httpService.HttpService;
import com.example.anik.shop.httpService.IHttpResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderBroadcastListener extends BroadcastReceiver {

    private Context context;

    public OrderBroadcastListener() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        postUnsentOrders();
        receiveOrderStatus();
    }

    private void receiveOrderStatus() {
        if (!AppHelper.isNetworkAvailable()) {
            // if no network is available, then send nothing, go away, huh -_- :/ :3
            return;
        }
        List<String> notReceivedOrderList = new ArrayList<>();
        final Order order = new Order(context);
        notReceivedOrderList = order.groupByNotReceived();
        if (null == notReceivedOrderList) {
            // either no order is placed, or orders are already sent, go away :/ :3
            return;
        }

        for (String notReceived : notReceivedOrderList) {
            String batch = notReceived;
            String url = String.format("%s/%s", AppConstant.LINK_ORDER, batch);
            new HttpService(context)
                    .onUrl(url)
                    .withMethod(HttpService.HTTP_GET)
                    .hideProgressDialog()
                    .registerResponse(new IHttpResponseListener() {
                        @Override
                        public void onRemoteCallComplete(int statusCode, String json) {
                            if (statusCode == 200) {
                                try {
                                    JSONObject object = new JSONObject(json);
                                    JSONObject data = object.getJSONObject("data");
                                    String batch = data.getString("batch");
                                    String message = data.getString("order");
                                    Bill bill = new Bill(context);
                                    if (bill.makeReady(batch, message)) {
                                        if (order.deleteOnReceive(batch)) {
                                            AppNotification.build(context, AppNotification.ORDER_RECEIVE_SUCCESSFUL)
                                                    .setTitle("Your order is now Ready")
                                                    .setBody("Check you bill for details")
                                                    .send();
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    })
                    .execute();
        }


    }

    private void postUnsentOrders() {
        if (!AppHelper.isNetworkAvailable()) {
            // if no network is available, then send nothing, go away, huh -_- :/ :3
            return;
        }
        List<Map<String, String>> unsentOrderList = new ArrayList<>();
        final Order order = new Order(context);
        unsentOrderList = order.groupByUnsent();
        if (null == unsentOrderList) {
            // either no order is placed, or orders are already sent, go away :/ :3
            return;
        }
        String user_type = AppConstant.USER_TYPE;
        String order_by = AppStorage.getUserMobileNumber();
        for (Map<String, String> row : unsentOrderList) {
            // found some values
            List<Order> unsentOrders = order.where("batch", row.get("batch"));
            if (null == unsentOrders) {
                continue;
            }

            for (Order unsentOrder : unsentOrders) {
                final String batch = unsentOrder.getBatch();
                final String product_id = unsentOrder.getProductId();
                String quantity = unsentOrder.getQuantity();
                String total_price = unsentOrder.getTotalPrice();
                String order_for = unsentOrder.getCompanyMobileNumber();
                List<String> keys = new ArrayList<>();
                List<String> values = new ArrayList<>();

                keys.add("batch");
                keys.add("product_id");
                keys.add("quantity");
                keys.add("user_type");
                keys.add("price");
                keys.add("order_by");
                keys.add("order_for");

                values.add(batch);
                values.add(product_id);
                values.add(quantity);
                values.add(user_type);
                values.add(total_price);
                values.add(order_by);
                values.add(order_for);
                new HttpService(context)
                        .onUrl(AppConstant.LINK_ORDER)
                        .withMethod(HttpService.HTTP_POST)
                        .withData(keys, values)
                        .hideProgressDialog()
                        .registerResponse(new IHttpResponseListener() {
                            @Override
                            public void onRemoteCallComplete(int statusCode, String json) {
                                if (statusCode == 200) {
                                    try {
                                        JSONObject object = new JSONObject(json);
                                        JSONObject reason = object.getJSONObject("reason");
                                        String batch = reason.getString("batch");
                                        String product_id = reason.getString("product_id");
                                        //Log.v(AppConstant.TAG, String.format("Batch: %s | Product id: %s, JSON: %s", batch, product_id, json));
                                        order.updateSubmissionStatus(batch, product_id);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        })
                        .execute();
            }

            AppNotification.build(context, AppNotification.ORDER_PLACEMENT_SUCCESSFUL)
                    .setTitle("Unsent order is now sent")
                    .setBody("Your order is successfully sent to server.")
                    .send();
        }
    }
}

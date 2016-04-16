package com.example.anik.agent.broadcastListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.anik.agent.database.Quick;
import com.example.anik.agent.helpers.AppConstant;
import com.example.anik.agent.helpers.AppHelper;
import com.example.anik.agent.helpers.AppNotification;
import com.example.anik.agent.helpers.AppStorage;
import com.example.anik.agent.httpService.HttpService;
import com.example.anik.agent.httpService.IHttpResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuickOrderBroadcastListener extends BroadcastReceiver {
    private Context context;

    public QuickOrderBroadcastListener() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        postUnsentQuickOrders();
        receiveOrderStatus();

    }

    private void postUnsentQuickOrders() {
        if (!AppHelper.isNetworkAvailable()) {
            // if no network is available, then send nothing, go away, huh -_- :/ :3
            return;
        }
        List<Map<String, String>> unsentQuickOrderList = new ArrayList<>();
        final Quick quick = new Quick(context);
        unsentQuickOrderList = quick.groupByUnsent();
        if (null == unsentQuickOrderList) {
            // either no order is placed, or orders are already sent, go away :/ :3
            return;
        }
        String user_type = AppConstant.USER_TYPE;
        String order_by = AppStorage.getUserMobileNumber();
        for (Map<String, String> row : unsentQuickOrderList) {
            // found some values
            List<Quick> unsentQuickOrders = quick.where("batch", row.get("batch"));
            if (null == unsentQuickOrders) {
                continue;
            }

            for (Quick unsentQuickOrder : unsentQuickOrders) {
                final String batch = unsentQuickOrder.getBatch();
                final String product_id = unsentQuickOrder.getProductId();
                String quantity = unsentQuickOrder.getQuantity();
                String price = unsentQuickOrder.getPrice();
                String total_price = unsentQuickOrder.getTotalPrice();
                String order_for = unsentQuickOrder.getCompanyMobileNumber();
                List<String> keys = new ArrayList<>();
                List<String> values = new ArrayList<>();

                keys.add("batch");
                keys.add("product_id");
                keys.add("quantity");
                keys.add("user_type");
                keys.add("price");
                keys.add("total_price");
                keys.add("order_by");
                keys.add("order_for");

                values.add(batch);
                values.add(product_id);
                values.add(quantity);
                values.add(user_type);
                values.add(price);
                values.add(total_price);
                values.add(order_by);
                values.add(order_for);
                new HttpService(context)
                        .onUrl(AppConstant.LINK_QUICK_ORDER)
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
                                        //Log.v(AppConstant.TAG, String.format("Quick order-> Batch: %s | Product id: %s, JSON: %s", batch, product_id, json));
                                        quick.updateSubmissionStatus(batch, product_id);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        })
                        .execute();
            }

            AppNotification.build(context, AppNotification.ORDER_PLACEMENT_SUCCESSFUL)
                    .setTitle("Quick order is now sent")
                    .setBody("Your unsent quick order is successfully sent.")
                    .send();
        }
    }

    private void receiveOrderStatus() {
        if (!AppHelper.isNetworkAvailable()) {
            // if no network is available, then send nothing, go away, huh -_- :/ :3
            return;
        }
        List<String> notRespondedQuickOrderList = new ArrayList<>();
        final Quick quick = new Quick(context);
        notRespondedQuickOrderList = quick.groupByNotReceived();
        if (null == notRespondedQuickOrderList) {
            // either no order is placed, or orders are already sent, go away :/ :3
            return;
        }

        for (String noResponse : notRespondedQuickOrderList) {
            String batch = noResponse;
            String url = String.format("%s/%s", AppConstant.LINK_QUICK_ORDER, batch);
            //Log.v(AppConstant.TAG, "On url: " + url);
            new HttpService(context)
                    .onUrl(url)
                    .withMethod(HttpService.HTTP_GET)
                    .hideProgressDialog()
                    .registerResponse(new IHttpResponseListener() {
                        @Override
                        public void onRemoteCallComplete(int statusCode, String json) {
                            if (statusCode == 201) { // accepted, tax applied, move to order_details table
                                try {
                                    JSONObject object = new JSONObject(json);
                                    JSONObject data = object.getJSONObject("data");
                                    String batch = data.getString("batch");
                                    String message = data.getString("message");
                                    quick.moveToOrder(batch);
                                    AppNotification.build(context, AppNotification.ORDER_RECEIVE_SUCCESSFUL)
                                            .setTitle("Your order is now accepted")
                                            .setBody("Your order is accepted and tax applied")
                                            .send();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (statusCode == 202) { // accepted, tax not applied, delete from quick_orders table
                                try {
                                    JSONObject object = new JSONObject(json);
                                    JSONObject data = object.getJSONObject("data");
                                    String batch = data.getString("batch");
                                    String message = data.getString("message");
                                    quick.deleteOnApprove(batch);
                                    AppNotification.build(context, AppNotification.ORDER_RECEIVE_SUCCESSFUL)
                                            .setTitle("Your order is now accepted")
                                            .setBody("Your order is accepted and tax not applied")
                                            .send();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    })
                    .execute();
        }


    }
}

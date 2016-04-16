package com.example.anik.shop.broadcastListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.anik.shop.database.Bill;
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

public class BillBroadcastListener extends BroadcastReceiver {
    private Context context;

    public BillBroadcastListener() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        postUnsentBills();
    }

    private void postUnsentBills() {
        if (!AppHelper.isNetworkAvailable()) {
            // if no network is available, then send nothing, go away, huh -_- :/ :3
            return;
        }
        List<Map<String, String>> unsentBillList = new ArrayList<>();
        final Bill bill = new Bill(context);
        unsentBillList = bill.groupByUnsent();
        if (null == unsentBillList) {
            // either no bill is placed, or bills are already sent, go away :/ :3
            return;
        }
        //String user_type = AppConstant.USER_TYPE;
        String agent_mobile_number = AppStorage.getUserMobileNumber();
        for (Map<String, String> row : unsentBillList) {
            // found some values
            List<Bill> unsentBills = bill.where("bill_key", row.get("bill_key"));
            if (null == unsentBills) {
                continue;
            }

            for (Bill unsentBill : unsentBills) {
                final String billKey = unsentBill.getBillKey();
                final String product_id = unsentBill.getProductId();
                String quantity = unsentBill.getQuantity();
                String total_price = unsentBill.getTotalPrice();
                String bill_for = unsentBill.getCompanyMobileNumber();
                List<String> keys = new ArrayList<>();
                List<String> values = new ArrayList<>();

                keys.add("company_mobile_number");
                keys.add("agent_mobile_number");
                keys.add("bill_key");
                keys.add("product_id");
                keys.add("quantity");
                keys.add("price");

                values.add(bill_for); // company mobile number
                values.add(agent_mobile_number);
                values.add(billKey);
                values.add(product_id);
                values.add(quantity);
                values.add(total_price);
                new HttpService(context)
                        .onUrl(AppConstant.LINK_BILL)
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
                                        String bill_key = reason.getString("bill_key");
                                        String product_id = reason.getString("product_id");
                                        //Log.v(AppConstant.TAG, String.format("Batch: %s | Product id: %s, JSON: %s", bill_key, product_id, json));
                                        bill.updateSubmissionStatus(bill_key, product_id);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                //Log.v(AppConstant.TAG, "Status: " + statusCode + " json: " + json);
                            }
                        })
                        .execute();
            }

            AppNotification.build(context, AppNotification.BILL_PLACEMENT_SUCCESSFUL)
                    .setTitle("Unsent bill is now sent")
                    .setBody("Your bill is successfully sent to server.")
                    .send();
        }
    }
}

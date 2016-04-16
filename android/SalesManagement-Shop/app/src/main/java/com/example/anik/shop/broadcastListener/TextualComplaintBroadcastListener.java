package com.example.anik.shop.broadcastListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.anik.shop.database.Complaint;
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

public class TextualComplaintBroadcastListener extends BroadcastReceiver {
    private Context context;
    private Complaint complaint;

    public TextualComplaintBroadcastListener() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        if (!AppHelper.isNetworkAvailable()) {
            //Log.v(AppConstant.TAG, "No internet connection");
            return;
        }
        complaint = new Complaint(context);
        postTextualComplaint();
        receiveComplaintSolution();
    }

    private void receiveComplaintSolution() {
        List<String> unsolvedComplaints = complaint.getUnsolvedComplaints();

        if (null == unsolvedComplaints) {
            return;
        }

        for (String tracking_number : unsolvedComplaints) {
            new HttpService(context)
                    .onUrl(AppConstant.LINK_COMPLAINT, tracking_number)
                    .withMethod(HttpService.HTTP_GET)
                    .hideProgressDialog()
                    .registerResponse(new IHttpResponseListener() {
                        @Override
                        public void onRemoteCallComplete(int statusCode, String json) {
                            if (statusCode == 200) {
                                try {
                                    JSONObject object = new JSONObject(json);
                                    JSONObject data = object.getJSONObject("data");
                                    String tracking_number = data.getString("tracking_number");
                                    String solution = data.getString("solution");
                                    if (complaint.updateSolution(tracking_number, solution)) {
                                        AppNotification.build(context, AppNotification.COMPLAINT_SOLUTION_RECEIVED)
                                                .setTitle("Your complaint is now solved")
                                                .setBody("Complaint is solved, Check solution for the complaint.")
                                                .send();
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

    private void postTextualComplaint() {
        List<Map<String, String>> unsentComplaintList = new ArrayList<>();
        unsentComplaintList = complaint.getUnsentComplaints();
        if (null == unsentComplaintList) {
            return;
        }

        for (Map<String, String> row : unsentComplaintList) {
            List<String> keys = new ArrayList<>();
            List<String> values = new ArrayList<>();
            keys.add("title");
            keys.add("description");
            keys.add("tracking_number");
            keys.add("mobile_number");
            keys.add("user_type");

            values.add(row.get("title"));
            values.add(row.get("description"));
            values.add(row.get("tracking_number"));
            values.add(AppStorage.getUserMobileNumber());
            values.add(AppConstant.USER_TYPE);


            new HttpService(context)
                    .onUrl(AppConstant.LINK_COMPLAINT)
                    .withMethod(HttpService.HTTP_POST)
                    .withData(keys, values)
                    .hideProgressDialog()
                    .registerResponse(new IHttpResponseListener() {
                        @Override
                        public void onRemoteCallComplete(int statusCode, String json) {
                            if (statusCode == 200) {
                                try {
                                    JSONObject object = new JSONObject(json);
                                    JSONObject data = object.getJSONObject("reason");
                                    String tracking_number = data.getString("tracking_number");
                                    complaint.updateSubmission(tracking_number);
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

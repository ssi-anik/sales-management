package com.example.anik.shop.broadcastListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.anik.shop.database.Complaint;
import com.example.anik.shop.helpers.AppConstant;
import com.example.anik.shop.helpers.AppHelper;
import com.example.anik.shop.helpers.AppStorage;
import com.example.anik.shop.httpService.HttpService;
import com.example.anik.shop.httpService.IHttpResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PhotoComplaintBroadcastListener extends BroadcastReceiver {

    private Context context;
    private Complaint complaint;


    public PhotoComplaintBroadcastListener() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        if (!AppHelper.isNetworkAvailable()) {
            return;
        }
        complaint = new Complaint(context);
        postUncommittedPhotoComplaints();
    }

    private void postUncommittedPhotoComplaints() {
        List<Map<String, String>> uncommittedPhotoComplaintList = new ArrayList<>();
        uncommittedPhotoComplaintList = complaint.getUncommittedPhotoComplaints();

        if (null == uncommittedPhotoComplaintList) {
            return;
        }

        for (Map<String, String> row : uncommittedPhotoComplaintList) {
            List<String> keys = new ArrayList<>();
            List<String> values = new ArrayList<>();
            keys.add("file");
            keys.add("mobile_number");
            keys.add("user_type");
            keys.add("tracking_number");

            values.add(row.get("photo_path"));
            values.add(AppStorage.getUserMobileNumber());
            values.add(AppConstant.USER_TYPE);
            values.add(row.get("tracking_number"));

            new HttpService(context)
                    .onUrl(AppConstant.LINK_COMPLAINT)
                    .withMethod(HttpService.HTTP_POST)
                    .hideProgressDialog()
                    .withData(keys, values)
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

package com.example.anik.shop.broadcastListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.anik.shop.database.Tracker;
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

public class LocationTrackerBroadcastListener extends BroadcastReceiver {
    Tracker tracker;
    private Context context;

    public LocationTrackerBroadcastListener() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        if (!AppHelper.isNetworkAvailable()) {
            return;
        }
        sendLocationToServer();
    }

    private void sendLocationToServer() {
        tracker = new Tracker(context);
        List<Map<String, String>> unsentLocations = new ArrayList<>();
        unsentLocations = tracker.unsentLocations();
        if (null == unsentLocations)
            return;

        for (Map<String, String> row : unsentLocations) {
            String id = row.get("id");
            String latitude = row.get("latitude");
            String longitude = row.get("longitude");
            String time = row.get("time");
            String mobile_number = AppStorage.getUserMobileNumber();

            List<String> keys = new ArrayList<>();
            List<String> values = new ArrayList<>();

            keys.add("id");
            keys.add("mobile_number");
            keys.add("latitude");
            keys.add("longitude");
            keys.add("time");

            values.add(id);
            values.add(mobile_number);
            values.add(latitude);
            values.add(longitude);
            values.add(time);

            new HttpService(context)
                    .onUrl(AppConstant.LINK_TRACKER)
                    .withMethod(HttpService.HTTP_POST)
                    .withData(keys, values)
                    .hideProgressDialog()
                    .registerResponse(new IHttpResponseListener() {
                        @Override
                        public void onRemoteCallComplete(int statusCode, String json) {
                            if (statusCode == 200) {
                                try {
                                    JSONObject object = new JSONObject(json);
                                    JSONObject data = object.getJSONObject("data");
                                    String id = data.getString("id");
                                    boolean updated = tracker.updateSubmissionStatus(id);
                                    if (!updated) {
                                        AppNotification.build(LocationTrackerBroadcastListener.this.context, AppNotification.SAVE_ERROR_NOTIFICATION)
                                                .setTitle("Facing trouble saving your location")
                                                .setBody("Post a complaint")
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
}

package com.example.anik.agent.broadcastListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.anik.agent.database.Agent;
import com.example.anik.agent.helpers.AppConstant;
import com.example.anik.agent.helpers.AppHelper;
import com.example.anik.agent.helpers.AppStorage;
import com.example.anik.agent.httpService.HttpService;
import com.example.anik.agent.httpService.IHttpResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserInformationBroadcastListener extends BroadcastReceiver {
    private Context context;
    private IHttpResponseListener userInformationResponseListener = new IHttpResponseListener() {
        @Override
        public void onRemoteCallComplete(int statusCode, String json) {
            if (statusCode == 200) {
                try {
                    JSONObject object = new JSONObject(json);
                    JSONObject user = object.getJSONObject("data");
                    String name = user.has("agent_name") ? user.getString("agent_name") : "";
                    String email = user.has("email") ? user.getString("email") : "";
                    String address = user.has("address") ? user.getString("address") : "";
                    String profile_picture = user.has("agent_picture") ? user.getString("agent_picture") : "";
                    String bonus = user.has("bonus") ? user.getString("bonus") : "";
                    Map<String, String> userInfo = new HashMap<>();
                    userInfo.put("name", name);
                    userInfo.put("email", email);
                    userInfo.put("address", address);
                    userInfo.put("profile_picture", profile_picture);
                    userInfo.put("bonus", bonus);
                    Agent agent = new Agent(UserInformationBroadcastListener.this.context);
                    agent.update(userInfo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public UserInformationBroadcastListener() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        if (!AppHelper.isNetworkAvailable())
            return;
        String url = String.format("%s/%s?user_type=%s", AppConstant.LINK_USER_INFO, AppStorage.getUserMobileNumber(), AppConstant.USER_TYPE);
        new HttpService(context)
                .onUrl(url)
                .hideProgressDialog()
                .registerResponse(userInformationResponseListener)
                .execute();
    }
}

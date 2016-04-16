package com.example.anik.shop.broadcastListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.anik.shop.SelectProductActivity;
import com.example.anik.shop.helpers.AppConstant;
import com.example.anik.shop.helpers.AppHelper;
import com.example.anik.shop.helpers.AppStorage;
import com.example.anik.shop.httpService.HttpService;
import com.example.anik.shop.httpService.IHttpResponseListener;

public class ProductJSONBroadcastListener extends BroadcastReceiver {
    private Context context;
    public ProductJSONBroadcastListener() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        if(!AppHelper.isNetworkAvailable()){
            return;
        }
        String url = String.format("%s%s",AppConstant.LINK_PRODUCTS_ORDER.substring(0, AppConstant.LINK_PRODUCTS_ORDER.length() - 1) , AppStorage.getUserMobileNumber());
        HttpService httpService = new HttpService(context);
        httpService.onUrl(url)
                .withMethod(HttpService.HTTP_GET)
                .hideProgressDialog()
                .registerResponse(new IHttpResponseListener() {
                    @Override
                    public void onRemoteCallComplete(int statusCode, String json) {
                        if(statusCode == 200){
                            AppStorage.setOrderAbleProductList(json);
                        }
                    }
                })
                .execute();
    }
}

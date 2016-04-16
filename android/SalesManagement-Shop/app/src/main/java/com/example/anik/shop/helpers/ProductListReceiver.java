package com.example.anik.shop.helpers;

import android.content.Context;

/**
 * Created by Anik on 31-Aug-15, 031.
 */
public class ProductListReceiver implements Runnable {
    private Context context;

    public ProductListReceiver(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        while(true){
            synchronized (this){
                try {
                    Thread.sleep(2 * AppConstant.TIME_MINUTES);
                    AppHelper.sendBroadcast(AppConstant.BROADCAST_PRODUCT_JSON_RECEIVER);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

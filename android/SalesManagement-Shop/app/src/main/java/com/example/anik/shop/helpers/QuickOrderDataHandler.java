package com.example.anik.shop.helpers;

import android.content.Context;

/**
 * Created by Anik on 17-Aug-15, 017.
 */
public class QuickOrderDataHandler implements Runnable {

    private Context context;

    public QuickOrderDataHandler(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                try {
                    Thread.sleep( 2 * AppConstant.TIME_MINUTES);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                AppHelper.sendBroadcast(AppConstant.BROADCAST_QUICK_ORDER);
            }
        }
    }
}

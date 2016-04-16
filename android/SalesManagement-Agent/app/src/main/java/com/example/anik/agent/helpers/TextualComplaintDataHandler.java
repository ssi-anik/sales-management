package com.example.anik.agent.helpers;

import android.content.Context;

/**
 * Created by Anik on 17-Aug-15, 017.
 */
public class TextualComplaintDataHandler implements Runnable {

    private Context context;

    public TextualComplaintDataHandler(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                try {
                    Thread.sleep(20 * AppConstant.TIME_SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                AppHelper.sendBroadcast(AppConstant.BROADCAST_TEXTUAL_COMPLAINT);
            }
        }
    }
}

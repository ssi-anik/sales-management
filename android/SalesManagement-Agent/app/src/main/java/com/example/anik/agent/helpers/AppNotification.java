package com.example.anik.agent.helpers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.example.anik.agent.AllComplaintActivity;
import com.example.anik.agent.BillListActivity;
import com.example.anik.agent.OrderListActivity;
import com.example.anik.agent.R;
import com.example.anik.agent.ShowProduct;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anik on 07-Aug-15, 007.
 */
public class AppNotification {
    public static int LOCATION_OFF_NOTIFICATION = 1;
    public static int SAVE_ERROR_NOTIFICATION = 2;
    public static int ASK_LATEST_PRICE_NOTIFICATION = 3;
    public static int ORDER_PLACEMENT_SUCCESSFUL = 4;
    public static int BILL_PLACEMENT_SUCCESSFUL = 5;
    public static int ORDER_RECEIVE_SUCCESSFUL = 6;
    public static int COMPLAINT_SOLUTION_RECEIVED = 7;
    private static AppNotification instance = null;

    private Context context;
    private int notificationType = 0;
    private String title = "";
    private String body = "";
    private Map<String, String> extraInformation = new HashMap<>();
    private NotificationManager notificationManager;

    private AppNotification(Context context, int type) {
        this.context = context;
        this.notificationType = type;
    }

    public static AppNotification build(Context context, int type) {
        if (type != LOCATION_OFF_NOTIFICATION
                && type != ASK_LATEST_PRICE_NOTIFICATION
                && type != ORDER_PLACEMENT_SUCCESSFUL
                && type != BILL_PLACEMENT_SUCCESSFUL
                && type != ORDER_RECEIVE_SUCCESSFUL
                && type != COMPLAINT_SOLUTION_RECEIVED
                ) {
            return null;
        }

        if (instance == null) {
            instance = new AppNotification(context, type);
        }
        return instance;
    }

    public static AppNotification setTitle(String title) {
        instance.title = title;
        return instance;
    }

    public static AppNotification setBody(String body) {
        instance.body = body;
        return instance;
    }

    public static AppNotification setExtraInformation(Map<String, String> information) {
        instance.extraInformation = information;
        return instance;
    }

    public static void send() {
        instance.notificationManager = (NotificationManager) instance.context.getSystemService(instance.context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(instance.context)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(instance.title)
                .setContentText(instance.body)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        Intent nextIntent = null;
        if (instance.getNotificationType() == LOCATION_OFF_NOTIFICATION) {
            nextIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);

        } else if (instance.getNotificationType() == ASK_LATEST_PRICE_NOTIFICATION) {
            nextIntent = new Intent(instance.context, ShowProduct.class);
            if (!instance.extraInformation.isEmpty()) {
                for (Map.Entry<String, String> row : instance.extraInformation.entrySet()) {
                    nextIntent.putExtra(row.getKey(), row.getValue());
                }
            }

        } else if (instance.getNotificationType() == ORDER_PLACEMENT_SUCCESSFUL) {
            nextIntent = new Intent(instance.context, OrderListActivity.class);

        } else if (instance.getNotificationType() == BILL_PLACEMENT_SUCCESSFUL) {
            nextIntent = new Intent(instance.context, BillListActivity.class);

        } else if (instance.getNotificationType() == ORDER_RECEIVE_SUCCESSFUL) {
            nextIntent = new Intent(instance.context, BillListActivity.class);

        } else if (instance.getNotificationType() == COMPLAINT_SOLUTION_RECEIVED) {
            nextIntent = new Intent(instance.context, AllComplaintActivity.class);

        } else if (instance.getNotificationType() == SAVE_ERROR_NOTIFICATION) {
            // todo here
            // nextIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        }
        PendingIntent pendingIntent =
                PendingIntent.getActivity(
                        instance.context,
                        101,
                        nextIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(pendingIntent);
        instance.notificationManager.notify(1123, mBuilder.build());
        instance = null;
    }

    public int getNotificationType() {
        return notificationType;
    }
}

package com.example.anik.shop.helpers;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.anik.shop.database.Tracker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LocationTracker implements LocationListener, Runnable {
    private Context context;
    private Location location;
    private LocationManager gpsLocationManager, networkLocationManager;
    private double latitude;
    private double longitude;
    private boolean isGpsEnabled;
    private boolean isNetworkAvailable;
    private Tracker tracker;

    public LocationTracker(Context context) {
        this.context = context;
        this.tracker = new Tracker(context);
        registerProvider();
    }

    private void registerProvider() {
        gpsLocationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        networkLocationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        isGpsEnabled = gpsLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkAvailable = networkLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        //gpsLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        //networkLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        gpsLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1 * AppConstant.TIME_MINUTES, 10, this);
        networkLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1 * AppConstant.TIME_MINUTES, 10, this);
    }

    public boolean isLocationAvailable() {
        gpsLocationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        networkLocationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        isGpsEnabled = gpsLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkAvailable = networkLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isGpsEnabled) return true;
        else if (isNetworkAvailable) return true;
        else return false;
    }

    public void showDisabledAlert() {
        AppNotification.build(this.context, AppNotification.LOCATION_OFF_NOTIFICATION)
                .setTitle("Location feature is disabled")
                .setBody("You must have to enable the location feature")
                .send();
    }

    private void showSaveErrorAlert() {
        AppNotification.build(this.context, AppNotification.SAVE_ERROR_NOTIFICATION)
                .setTitle("Facing trouble saving your location")
                .setBody("Post a complaint")
                .send();
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
        this.getLocation();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        getLocation();
    }

    @Override
    public void onProviderDisabled(String provider) {
        showDisabledAlert();
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                try {
                    Thread.sleep(1 * AppConstant.TIME_MINUTES);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getLocation();
                AppHelper.sendBroadcast(AppConstant.BROADCAST_LOCATION);
            }
        }

    }

    public void getLocation() {
        if (!isLocationAvailable()) {
            showDisabledAlert();
            return;
        }
        if (location == null) {
            return;
        }
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        String time = now();
        boolean isInserted = tracker.insert(latitude, longitude, time);
        if (!isInserted) {
            showSaveErrorAlert();
        }
    }

    private String now() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

}
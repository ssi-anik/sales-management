package com.example.anik.shop.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.anik.shop.helpers.BillDataHandler;
import com.example.anik.shop.helpers.LocationTracker;
import com.example.anik.shop.helpers.OrderDataHandler;
import com.example.anik.shop.helpers.PhotoComplaintDataHandler;
import com.example.anik.shop.helpers.ProductListReceiver;
import com.example.anik.shop.helpers.QuickOrderDataHandler;
import com.example.anik.shop.helpers.TextualComplaintDataHandler;
import com.example.anik.shop.helpers.UserInformationDataHandler;

public class AppServices extends Service {
    private static boolean isCalled = false;
    private LocationTracker locationTracker;
    private OrderDataHandler orderHandler;
    private BillDataHandler billHandler;
    private QuickOrderDataHandler quickOrderDataHandler;
    private TextualComplaintDataHandler textualComplaintDataHandler;
    private PhotoComplaintDataHandler photoComplaintDataHandler;
    private UserInformationDataHandler userInformationDataHandler;
    private ProductListReceiver productListReceiver;

    public AppServices() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //locationTracker = new LocationTracker(AppServices.this);
        orderHandler = new OrderDataHandler(AppServices.this);
        billHandler = new BillDataHandler(AppServices.this);
        quickOrderDataHandler = new QuickOrderDataHandler(AppServices.this);
        textualComplaintDataHandler = new TextualComplaintDataHandler(AppServices.this);
        photoComplaintDataHandler = new PhotoComplaintDataHandler(AppServices.this);
        userInformationDataHandler = new UserInformationDataHandler(AppServices.this);
        productListReceiver = new ProductListReceiver(AppServices.this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        if (!isCalled) {
            isCalled = true;
            //new Thread(locationTracker).start();
            new Thread(orderHandler).start();
            new Thread(billHandler).start();
            new Thread(quickOrderDataHandler).start();
            new Thread(textualComplaintDataHandler).start();
            new Thread(photoComplaintDataHandler).start();
            new Thread(userInformationDataHandler).start();
            new Thread(productListReceiver).start();
        }
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

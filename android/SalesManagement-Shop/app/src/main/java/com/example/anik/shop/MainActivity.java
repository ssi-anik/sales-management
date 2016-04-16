package com.example.anik.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.anik.shop.database.ShopUser;
import com.example.anik.shop.helpers.AppConstant;
import com.example.anik.shop.helpers.AppStorage;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = MainActivity.this;
        //getApplicationContext().deleteDatabase(AppConstant.DATABASE_NAME);
        ShopUser shopUser = new ShopUser(this.context);
        Map<String, String> shop_user_information = shopUser.getUser();
        if (shop_user_information == null) {
            // no user is available, show the registration criteria
            Intent intent = new Intent(MainActivity.this, RegistrationFormActivity.class);
            startActivity(intent);
            finish();
        } else {
            // user already exists
            String mobile_number = shop_user_information.get("mobile_number");
            String agent_mobile_number = shop_user_information.get("agent_mobile_number");
            int is_activated = Integer.parseInt(shop_user_information.get("is_activated"), 10);
            AppStorage.setUserMobileNumber(mobile_number);
            AppStorage.setAgentMobileNumber(agent_mobile_number);

            if (is_activated == 0) {
                // user registered but not activated
                Intent intent = new Intent(MainActivity.this, MobileNumberConfirmationActivity.class);
                startActivity(intent);
                finish();
            } else {
                // user had registered and activated account
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}

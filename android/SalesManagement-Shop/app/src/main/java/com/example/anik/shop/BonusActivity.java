package com.example.anik.shop;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.example.anik.shop.database.ShopUser;

import java.util.Map;


public class BonusActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus);
        ShopUser shopUser = new ShopUser(BonusActivity.this);
        Map<String, String> user_info = shopUser.getUser();
        String bonus = user_info.get("bonus");
        ((TextView) findViewById(R.id.point)).setText(bonus);
    }

}

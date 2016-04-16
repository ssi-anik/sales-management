package com.example.anik.agent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.anik.agent.database.Agent;
import com.example.anik.agent.helpers.AppConstant;
import com.example.anik.agent.helpers.AppStorage;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = MainActivity.this;
        //getApplicationContext().deleteDatabase(AppConstant.DATABASE_NAME);
        Agent agent = new Agent(this.context);
        Map<String, String> agent_information = agent.getUser();
        if (agent_information == null) {
            // no user is available, show the registration criteria
            Intent intent = new Intent(MainActivity.this, RegistrationFormActivity.class);
            startActivity(intent);
            finish();
        } else {
            // user already exists
            String mobile_number = agent_information.get("mobile_number");
            int is_activated = Integer.parseInt(agent_information.get("is_activated"), 10);
            AppStorage.setUserMobileNumber(mobile_number);

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

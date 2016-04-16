package com.example.anik.agent;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.example.anik.agent.database.Agent;

import java.util.Map;


public class BonusActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus);
        Agent agent = new Agent(BonusActivity.this);
        Map<String, String> user_info = agent.getUser();
        String bonus = user_info.get("bonus");
        ((TextView) findViewById(R.id.point)).setText(bonus);
    }

}

package com.example.anik.agent;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anik.agent.database.Agent;
import com.example.anik.agent.helpers.AppHelper;
import com.example.anik.agent.helpers.AppStorage;
import com.example.anik.agent.httpService.ImageDownloader;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;


public class AccountDetailsActivity extends ActionBarActivity {
    ImageView userPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        Agent agent = new Agent(AccountDetailsActivity.this);
        Map<String, String> user_information = agent.getUser();

        TextView username = (TextView) findViewById(R.id.showUserName);
        username.setText(user_information.get("name").isEmpty() ? "No name is set yet" : user_information.get("name"));

        TextView mobileNumber = (TextView) findViewById(R.id.showMobileNumber);
        mobileNumber.setText(user_information.get("mobile_number"));

        TextView joiningDate = (TextView) findViewById(R.id.showJoiningDate);
        try {
            joiningDate.setText((new SimpleDateFormat("E MMM dd, yyyy")).format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(user_information.get("created_at"))));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        userPhoto = (ImageView) findViewById(R.id.ivUserImage);
        //if (user_information.get("profile_picture") != null) {
        if (!user_information.get("profile_picture").isEmpty()) {
            String profilePictureUrl = user_information.get("profile_picture");
            final String image_name = profilePictureUrl.substring(profilePictureUrl.lastIndexOf("/") + 1);

            File file = new File(AppStorage.getFilesDirectory(), image_name);
            if (file.exists()) {
                AppHelper.setImageToImageView(userPhoto, file);
            } else {
                ImageDownloader.pushJob(profilePictureUrl, userPhoto).execute();
            }
        }

        TextView showAddress = (TextView) findViewById(R.id.showAddress);
        showAddress.setText(user_information.get("address").isEmpty() ? "No address is set yet" : user_information.get("address"));
    }
}

package com.example.anik.agent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.anik.agent.database.Complaint;
import com.example.anik.agent.helpers.AppConstant;
import com.example.anik.agent.helpers.AppHelper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class PhotoComplaintActivity extends ActionBarActivity {

    ImageView imageView;
    Button btnSubmitPhotoComplaint;
    String uri;
    String tracking_number;
    Complaint complaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_complaint);
        imageView = (ImageView) findViewById(R.id.imageViewForCapturedPhoto);
        btnSubmitPhotoComplaint = (Button) findViewById(R.id.btnSubmitPhotoComplaint);
        Intent intent = getIntent();
        uri = intent.getStringExtra("uri");
        File file = new File(uri);
        if (!file.exists()) {
            Toast.makeText(PhotoComplaintActivity.this, AppConstant.MESSAGE_APP_ERROR, Toast.LENGTH_SHORT).show();
            finish();
        }
        AppHelper.setImageToImageView(imageView, uri);

        btnSubmitPhotoComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complaint = new Complaint(PhotoComplaintActivity.this);
                Map<String, String> data = new HashMap<>();
                tracking_number = AppHelper.randomUniqueNumber();
                data.put("tracking_number", tracking_number);
                data.put("photo_path", uri);
                boolean rowInserted = complaint.insert(data);
                if (!rowInserted) {
                    Toast.makeText(PhotoComplaintActivity.this, AppConstant.MESSAGE_APP_ERROR, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PhotoComplaintActivity.this, "Photo complaint is submitted.", Toast.LENGTH_SHORT).show();
                    AppHelper.sendBroadcast(AppConstant.BROADCAST_PHOTO_COMPLAINT);
                }
                finish();
            }
        });
    }
}

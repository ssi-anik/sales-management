package com.example.anik.shop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.anik.shop.helpers.AppConstant;
import com.example.anik.shop.helpers.AppHelper;


public class ComplaintActivity extends AppCompatActivity {

    View.OnClickListener allComplaintClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ComplaintActivity.this, AllComplaintActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener textualComplaintClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ComplaintActivity.this, TextualComplaintActivity.class);
            startActivity(intent);
        }
    };
    Uri fileUri;
    View.OnClickListener photoComplaintClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            fileUri = AppHelper.getOutputMediaFileUri();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, 2015);
        }
    };
    private Button btnAllComplaint, btnTextualComplaint, btnPhotoComplaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        btnAllComplaint = (Button) findViewById(R.id.btnAllComplaints);
        btnTextualComplaint = (Button) findViewById(R.id.btnTextualComplaint);
        btnPhotoComplaint = (Button) findViewById(R.id.btnPhotoComplaint);

        btnAllComplaint.setOnClickListener(allComplaintClickListener);
        btnTextualComplaint.setOnClickListener(textualComplaintClickListener);
        btnPhotoComplaint.setOnClickListener(photoComplaintClickListener);
        if (!AppHelper.isCameraAvailable()) {
            btnPhotoComplaint.setEnabled(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == 2015) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent(this, PhotoComplaintActivity.class);
                intent.putExtra("uri", fileUri.getPath());
                startActivity(intent);
            } else if (resultCode == RESULT_CANCELED) {
                // user has cancelled the image, have I got to do anything with that -_- :3 :/
            } else {
                // failed to capture image
                Toast.makeText(ComplaintActivity.this, AppConstant.MESSAGE_APP_ERROR, Toast.LENGTH_SHORT).show();
            }

        }
    }
}

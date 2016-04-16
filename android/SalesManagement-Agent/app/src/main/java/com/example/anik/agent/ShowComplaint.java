package com.example.anik.agent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anik.agent.database.Complaint;
import com.example.anik.agent.helpers.AppHelper;

import java.io.File;


public class ShowComplaint extends ActionBarActivity {

    private Complaint complaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        //String id = intent.getStringExtra("id");
        String tracking_number = intent.getStringExtra("tracking_number");
        complaint = new Complaint(ShowComplaint.this);
        //Complaint currentComplaint = complaint.get(id);
        Complaint currentComplaint = complaint.getByTrackingNumber(tracking_number);
        if (currentComplaint.getPhotoPath().isEmpty()) {
            setContentView(R.layout.activity_show_complaint);
            setTitle(currentComplaint.getTitle());
            ((TextView) findViewById(R.id.complaintTitleForShowComplaint)).setText(currentComplaint.getTitle());
            ((TextView) findViewById(R.id.complaintDescriptionForShowComplaint)).setText(currentComplaint.getDescription());
            ((TextView) findViewById(R.id.complaintSubmissionStatusForShowComplaint)).setText(currentComplaint.getIsSubmitted().equals("0") ? "Not submitted." : "Complaint is submitted online");
            if (currentComplaint.getSolution().isEmpty()) {
                ((TextView) findViewById(R.id.complaintSolutionForShowComplaint)).setVisibility(View.GONE);
            } else {
                ((TextView) findViewById(R.id.complaintSolutionForShowComplaint)).setText(currentComplaint.getSolution());
            }

            ((TextView) findViewById(R.id.complaintSubmissionDateForShowComplaint)).setText(String.format("Submitted on: %s", AppHelper.formatDate(currentComplaint.getCreatedAt())));
        } else {
            setContentView(R.layout.layout_photo_complaint);
            setTitle("Photo complaint");
            String uri = currentComplaint.getPhotoPath();
            File file = new File(uri);
            if (file.exists()) {
                ImageView imageView = (ImageView) findViewById(R.id.imageViewForShowComplaint);
                AppHelper.setImageToImageView(imageView, file);
            }
            ((TextView) findViewById(R.id.submissionStatusForPhotoComplaint)).setText(currentComplaint.getIsSubmitted().equals("0") ? "Not submitted." : "Complaint is submitted online");
            if (currentComplaint.getSolution().isEmpty()) {
                ((TextView) findViewById(R.id.solutionForPhotoComplaint)).setVisibility(View.GONE);
            } else {
                ((TextView) findViewById(R.id.solutionForPhotoComplaint)).setText(currentComplaint.getSolution());
            }

            ((TextView) findViewById(R.id.submissionDateForPhotoComplaint)).setText(String.format("Submitted on: %s", AppHelper.formatDate(currentComplaint.getCreatedAt())));
        }

    }
}

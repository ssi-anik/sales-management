package com.example.anik.shop;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anik.shop.database.Complaint;
import com.example.anik.shop.helpers.AppConstant;
import com.example.anik.shop.helpers.AppHelper;

import java.util.HashMap;
import java.util.Map;

import static com.example.anik.shop.helpers.AppHelper.toggleSoftKeyboard;


public class TextualComplaintActivity extends ActionBarActivity {

    EditText textualComplaintTitle, textualComplaintDescription;
    Button btnSubmitTextualComplaint;
    String title, description, tracking_number;
    View.OnClickListener submitComplaintListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            toggleSoftKeyboard(textualComplaintTitle);
            title = textualComplaintTitle.getText().toString().trim();
            description = textualComplaintDescription.getText().toString().trim();
            tracking_number = AppHelper.randomUniqueNumber();
            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(TextualComplaintActivity.this, AppConstant.MESSAGE_FORM_NOT_FILLED_CORRECTLY, Toast.LENGTH_SHORT).show();
                return;
            }
            Map<String, String> data = new HashMap<>();
            data.put("title", title);
            data.put("description", description);
            data.put("tracking_number", tracking_number);
            boolean rowInserted = complaint.insert(data);
            if (!rowInserted) {
                Toast.makeText(TextualComplaintActivity.this, AppConstant.MESSAGE_APP_ERROR, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(TextualComplaintActivity.this, "Your complain is submitted.", Toast.LENGTH_SHORT).show();
                AppHelper.sendBroadcast(AppConstant.BROADCAST_TEXTUAL_COMPLAINT);
            }

            finish();
        }
    };
    Complaint complaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textual_complaint);
        complaint = new Complaint(TextualComplaintActivity.this);
        textualComplaintTitle = (EditText) findViewById(R.id.textualComplaintTitle);
        textualComplaintDescription = (EditText) findViewById(R.id.textualComplaintDescription);
        btnSubmitTextualComplaint = (Button) findViewById(R.id.btnSubmitTextualComplaint);

        btnSubmitTextualComplaint.setOnClickListener(submitComplaintListener);
    }
}

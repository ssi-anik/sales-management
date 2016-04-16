package com.example.anik.agent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anik.agent.database.Agent;
import com.example.anik.agent.helpers.AppConstant;
import com.example.anik.agent.helpers.AppHelper;
import com.example.anik.agent.helpers.AppStorage;
import com.example.anik.agent.httpService.HttpService;
import com.example.anik.agent.httpService.IHttpResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MobileNumberConfirmationActivity extends AppCompatActivity {

    TextView registeredMobileNumber;
    Button btnConfirm, btnResend;
    EditText etConfirmationCode;
    View.OnClickListener confirmButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AppHelper.toggleSoftKeyboard(etConfirmationCode);
            if (!AppHelper.isNetworkAvailable()) {
                Toast.makeText(MobileNumberConfirmationActivity.this, AppConstant.MESSAGE_NO_INTERNET, Toast.LENGTH_SHORT).show();
                return;
            }
            List<String> formDataKeys = new ArrayList<>();
            formDataKeys.add("mobile_number");
            formDataKeys.add("user_type");
            formDataKeys.add("confirmation_code");

            List<String> formDataValues = new ArrayList<>();
            formDataValues.add(AppStorage.getUserMobileNumber());
            formDataValues.add(AppConstant.USER_TYPE);
            formDataValues.add(etConfirmationCode.getText().toString());
            new HttpService(MobileNumberConfirmationActivity.this).onUrl(AppConstant.LINK_CONFIRMATION, AppStorage.getUserMobileNumber())
                    .withMethod(HttpService.HTTP_PUT)
                    .withData(formDataKeys, formDataValues)
                    .registerResponse(confirmationCodeResponseListener)
                    .execute();
        }
    };
    Agent agent;
    TextWatcher confirmationCodeTextChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            boolean enableButton = String.valueOf(s).trim().length() >= 4;
            btnConfirm.setEnabled(enableButton);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    View.OnClickListener resendButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (AppHelper.isNetworkAvailable() == false) {
                Toast.makeText(MobileNumberConfirmationActivity.this, AppConstant.MESSAGE_NO_INTERNET, Toast.LENGTH_SHORT).show();
                return;
            } else {
                List<String> formDataKeys = new ArrayList<>();
                List<String> formDataValues = new ArrayList<>();

                formDataKeys.add("user_type");
                formDataKeys.add("mobile_number");

                formDataValues.add(AppConstant.USER_TYPE);
                formDataValues.add(AppStorage.getUserMobileNumber());

                new HttpService(MobileNumberConfirmationActivity.this).onUrl(AppConstant.LINK_RESEND)
                        .withMethod(HttpService.HTTP_POST)
                        .withData(formDataKeys, formDataValues)
                        .registerResponse(new IHttpResponseListener() {
                            @Override
                            public void onRemoteCallComplete(int statusCode, String json) {
                                if (statusCode == 200) {
                                    Toast.makeText(MobileNumberConfirmationActivity.this, AppConstant.MESSAGE_USER_CONFIRMATION_CODE_RESENT, Toast.LENGTH_SHORT).show();
                                    return;
                                } else if (statusCode == 400) {
                                    try {
                                        JSONObject error = new JSONObject(json);
                                        JSONObject reason = error.getJSONObject("reason");
                                        JSONArray message = reason.getJSONArray("mobile_number");
                                        String errorReason = message.getString(0);
                                        Toast.makeText(MobileNumberConfirmationActivity.this, errorReason, Toast.LENGTH_SHORT).show();
                                        return;
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(MobileNumberConfirmationActivity.this, AppConstant.MESSAGE_APP_ERROR, Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } else {
                                    Toast.makeText(MobileNumberConfirmationActivity.this, AppConstant.MESSAGE_APP_ERROR, Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        })
                        .execute();
            }
        }
    };
    IHttpResponseListener confirmationCodeResponseListener = new IHttpResponseListener() {
        @Override
        public void onRemoteCallComplete(int statusCode, String json) {
            if (statusCode == 200) {
                Map<String, String> valuesToInsert = new HashMap();
                valuesToInsert.put("activation_key", etConfirmationCode.getText().toString().trim());
                valuesToInsert.put("is_activated", String.valueOf(1));
                boolean isUpdated = MobileNumberConfirmationActivity.this.agent.update(valuesToInsert);
                if (isUpdated) {
                    Toast.makeText(MobileNumberConfirmationActivity.this, AppConstant.MESSAGE_USER_ACCOUNT_CONFIRMED, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MobileNumberConfirmationActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MobileNumberConfirmationActivity.this, AppConstant.MESSAGE_APP_ERROR, Toast.LENGTH_SHORT).show();
                    return;
                }
            } else if (statusCode == 400) {
                try {
                    JSONObject error = new JSONObject(json);
                    String message = error.getString("reason");
                    Toast.makeText(MobileNumberConfirmationActivity.this, message, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(MobileNumberConfirmationActivity.this, AppConstant.MESSAGE_APP_ERROR, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(MobileNumberConfirmationActivity.this, AppConstant.MESSAGE_APP_ERROR, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_number_confirmation);
        agent = new Agent(MobileNumberConfirmationActivity.this);
        registeredMobileNumber = (TextView) findViewById(R.id.tvRegisteredMobileNumber);
        registeredMobileNumber.setText(AppStorage.getUserMobileNumber());
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnResend = (Button) findViewById(R.id.btnResend);
        btnConfirm.setEnabled(false);
        etConfirmationCode = (EditText) findViewById(R.id.etConfirmationCode);
        etConfirmationCode.addTextChangedListener(confirmationCodeTextChangeListener);
        btnConfirm.setOnClickListener(confirmButtonClickListener);
        btnResend.setOnClickListener(resendButtonClickListener);
    }
}

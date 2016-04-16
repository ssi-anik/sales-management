package com.example.anik.agent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


public class RegistrationFormActivity extends AppCompatActivity {

    TextWatcher textChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            boolean enableButton = String.valueOf(s).trim().length() > 9;
            btnRegister.setEnabled(enableButton);
            btnAlreadyRegistered.setEnabled(enableButton);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    IHttpResponseListener requestResponseListener = new IHttpResponseListener() {
        @Override
        public void onRemoteCallComplete(int statusCode, String json) {
            if (statusCode == 200) {
                Map<String, String> valuesToInsert = new HashMap();
                valuesToInsert.put("mobile_number", editTextUserPhoneNumber.getText().toString());
                boolean isInserted = RegistrationFormActivity.this.agent.insert(valuesToInsert);
                if (isInserted) {
                    AppStorage.setUserMobileNumber(editTextUserPhoneNumber.getText().toString());
                    Toast.makeText(RegistrationFormActivity.this, AppConstant.MESSAGE_USER_ACCOUNT_CREATED, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistrationFormActivity.this, MobileNumberConfirmationActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                } else {
                    Toast.makeText(RegistrationFormActivity.this, AppConstant.MESSAGE_APP_ERROR, Toast.LENGTH_SHORT).show();
                    return;
                }
            } else if (statusCode == 400) {
                try {
                    JSONObject error = new JSONObject(json);
                    JSONObject reason = error.getJSONObject("reason");
                    JSONArray message = reason.getJSONArray("mobile_number");
                    String errorReason = message.getString(0);
                    Toast.makeText(RegistrationFormActivity.this, errorReason, Toast.LENGTH_SHORT).show();
                    return;
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(RegistrationFormActivity.this, AppConstant.MESSAGE_APP_ERROR, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RegistrationFormActivity.this, AppConstant.MESSAGE_APP_ERROR, Toast.LENGTH_SHORT).show();
                return;
            }
        }
    };
    private Context context;
    private EditText editTextUserPhoneNumber;
    View.OnClickListener registerButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (AppHelper.isNetworkAvailable() == false) {
                Toast.makeText(RegistrationFormActivity.this, AppConstant.MESSAGE_NO_INTERNET, Toast.LENGTH_SHORT).show();
                return;
            } else {
                AppHelper.toggleSoftKeyboard(editTextUserPhoneNumber);
                List<String> formDataKeys = new ArrayList<>();
                List<String> formDataValues = new ArrayList<>();

                formDataKeys.add("user_type");
                formDataKeys.add("mobile_number");

                formDataValues.add(AppConstant.USER_TYPE);
                formDataValues.add(editTextUserPhoneNumber.getText().toString());

                new HttpService(RegistrationFormActivity.this).onUrl(AppConstant.LINK_REGISTRATION)
                        .withMethod(HttpService.HTTP_POST)
                        .withData(formDataKeys, formDataValues)
                        .registerResponse(requestResponseListener)
                        .execute();
            }
        }
    };
    View.OnClickListener alreadyRegisteredButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (AppHelper.isNetworkAvailable() == false) {
                Toast.makeText(RegistrationFormActivity.this, AppConstant.MESSAGE_NO_INTERNET, Toast.LENGTH_SHORT).show();
                return;
            } else {
                AppHelper.toggleSoftKeyboard(editTextUserPhoneNumber);
                List<String> formDataKeys = new ArrayList<>();
                List<String> formDataValues = new ArrayList<>();

                formDataKeys.add("user_type");
                formDataKeys.add("mobile_number");

                formDataValues.add(AppConstant.USER_TYPE);
                formDataValues.add(editTextUserPhoneNumber.getText().toString());

                new HttpService(RegistrationFormActivity.this).onUrl(AppConstant.LINK_RESEND)
                        .withMethod(HttpService.HTTP_POST)
                        .withData(formDataKeys, formDataValues)
                        .registerResponse(new IHttpResponseListener() {
                            @Override
                            public void onRemoteCallComplete(int statusCode, String json) {
                                if (statusCode == 200) {
                                    Map<String, String> valuesToInsert = new HashMap();
                                    valuesToInsert.put("mobile_number", editTextUserPhoneNumber.getText().toString());
                                    boolean isInserted = RegistrationFormActivity.this.agent.insert(valuesToInsert);
                                    if (isInserted) {
                                        AppStorage.setUserMobileNumber(editTextUserPhoneNumber.getText().toString());
                                        Toast.makeText(RegistrationFormActivity.this, AppConstant.MESSAGE_USER_CONFIRMATION_CODE_RESENT, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegistrationFormActivity.this, MobileNumberConfirmationActivity.class);
                                        startActivity(intent);
                                        finish();
                                        return;
                                    } else {
                                        Toast.makeText(RegistrationFormActivity.this, AppConstant.MESSAGE_APP_ERROR, Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } else if (statusCode == 400) {
                                    try {
                                        JSONObject error = new JSONObject(json);
                                        JSONObject reason = error.getJSONObject("reason");
                                        JSONArray message = reason.getJSONArray("mobile_number");
                                        String errorReason = message.getString(0);
                                        Toast.makeText(RegistrationFormActivity.this, errorReason, Toast.LENGTH_SHORT).show();
                                        return;
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(RegistrationFormActivity.this, AppConstant.MESSAGE_APP_ERROR, Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(RegistrationFormActivity.this, AppConstant.MESSAGE_APP_ERROR, Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        })
                        .execute();
            }
        }
    };
    private Button btnRegister, btnAlreadyRegistered;
    private Agent agent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);
        this.context = RegistrationFormActivity.this;
        agent = new Agent(this.context);
        editTextUserPhoneNumber = (EditText) findViewById(R.id.etUserPhoneNumber);
        editTextUserPhoneNumber.addTextChangedListener(textChangeListener);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnAlreadyRegistered = (Button) findViewById(R.id.btnAlreadyRegistered);

        btnRegister.setEnabled(false);
        btnAlreadyRegistered.setEnabled(false);

        btnRegister.setOnClickListener(registerButtonClickListener);
        btnAlreadyRegistered.setOnClickListener(alreadyRegisteredButtonClickListener);
    }
}

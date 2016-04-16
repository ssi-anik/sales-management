package com.example.anik.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anik.shop.database.ShopUser;
import com.example.anik.shop.helpers.AppConstant;
import com.example.anik.shop.helpers.AppHelper;
import com.example.anik.shop.helpers.AppStorage;
import com.example.anik.shop.httpService.HttpService;
import com.example.anik.shop.httpService.IHttpResponseListener;

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
                valuesToInsert.put("agent_mobile_number", editTextAgentPhoneNumber.getText().toString());
                boolean isInserted = RegistrationFormActivity.this.shopUser.insert(valuesToInsert);
                if (isInserted) {
                    AppStorage.setUserMobileNumber(editTextUserPhoneNumber.getText().toString());
                    AppStorage.setAgentMobileNumber(editTextAgentPhoneNumber.getText().toString());

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
                    JSONArray message = reason.has("mobile_number") ? reason.getJSONArray("mobile_number") : reason.getJSONArray("agent_mobile_number");
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
    private EditText editTextAgentPhoneNumber;
    View.OnClickListener registerButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (AppHelper.isNetworkAvailable() == false) {
                Toast.makeText(RegistrationFormActivity.this, AppConstant.MESSAGE_NO_INTERNET, Toast.LENGTH_SHORT).show();
                return;
            } else {
                AppHelper.toggleSoftKeyboard(editTextUserPhoneNumber);
                String shop_mobile_number = editTextUserPhoneNumber.getText().toString();
                String agent_mobile_number = editTextAgentPhoneNumber.getText().toString();
                if(shop_mobile_number.isEmpty() || agent_mobile_number.isEmpty() || shop_mobile_number.length() <= 9 || agent_mobile_number.length() <= 9){
                    Toast.makeText(RegistrationFormActivity.this, "Both mobile numbers should be valid", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<String> formDataKeys = new ArrayList<>();
                List<String> formDataValues = new ArrayList<>();

                formDataKeys.add("user_type");
                formDataKeys.add("mobile_number");
                formDataKeys.add("agent_mobile_number");

                formDataValues.add(AppConstant.USER_TYPE);
                formDataValues.add(editTextUserPhoneNumber.getText().toString());
                formDataValues.add(editTextAgentPhoneNumber.getText().toString());

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
                formDataKeys.add("agent_mobile_number");

                formDataValues.add(AppConstant.USER_TYPE);
                formDataValues.add(editTextUserPhoneNumber.getText().toString());
                formDataValues.add(editTextAgentPhoneNumber.getText().toString());

                new HttpService(RegistrationFormActivity.this).onUrl(AppConstant.LINK_RESEND)
                        .withMethod(HttpService.HTTP_POST)
                        .withData(formDataKeys, formDataValues)
                        .registerResponse(new IHttpResponseListener() {
                            @Override
                            public void onRemoteCallComplete(int statusCode, String json) {
                                if (statusCode == 200) {
                                    Map<String, String> valuesToInsert = new HashMap();
                                    valuesToInsert.put("mobile_number", editTextUserPhoneNumber.getText().toString());
                                    valuesToInsert.put("agent_mobile_number", editTextAgentPhoneNumber.getText().toString());
                                    boolean isInserted = RegistrationFormActivity.this.shopUser.insert(valuesToInsert);
                                    if (isInserted) {
                                        AppStorage.setUserMobileNumber(editTextUserPhoneNumber.getText().toString());
                                        AppStorage.setAgentMobileNumber(editTextAgentPhoneNumber.getText().toString());
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
                                        String errorReason = ""; //message.getString(0);
                                        JSONObject error = new JSONObject(json);
                                        JSONObject reason = error.getJSONObject("reason");
                                        JSONArray message = null;
                                        if (reason.has("mobile_number")) {
                                            message = reason.getJSONArray("mobile_number");
                                            errorReason = message.getString(0);
                                        } else if (reason.has("agent_mobile_number")) {
                                            message = reason.getJSONArray("agent_mobile_number");
                                            errorReason = message.getString(0);
                                        } else {
                                            message = reason.getJSONArray("input_error");
                                            errorReason = message.getString(0);
                                        }
                                        if (!errorReason.isEmpty())
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
    private ShopUser shopUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);
        this.context = RegistrationFormActivity.this;
        shopUser = new ShopUser(this.context);
        editTextUserPhoneNumber = (EditText) findViewById(R.id.etUserPhoneNumber);
        editTextAgentPhoneNumber = (EditText) findViewById(R.id.etAgentNumber);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnAlreadyRegistered = (Button) findViewById(R.id.btnAlreadyRegistered);

        editTextUserPhoneNumber.addTextChangedListener(textChangeListener);

        btnRegister.setEnabled(false);
        btnAlreadyRegistered.setEnabled(false);

        btnRegister.setOnClickListener(registerButtonClickListener);
        btnAlreadyRegistered.setOnClickListener(alreadyRegisteredButtonClickListener);
    }
}

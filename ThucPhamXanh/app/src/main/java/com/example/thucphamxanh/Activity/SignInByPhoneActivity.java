package com.example.thucphamxanh.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thucphamxanh.R;
import com.google.android.material.textfield.TextInputLayout;

public class SignInByPhoneActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "SignInByPhoneActivity" ;
    private LinearLayout mLayoutSignUp, mLayoutForgotPassword;
    private TextInputLayout mLayoutPhoneNumber, mLayoutPassword;
    private Button mBtnSignIn;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_by_phone);
        initUI();
        setOnclickListener();
    }
    private void initUI() {
        mLayoutSignUp = findViewById(R.id.layout_sign_in_by_phone_activity_sign_sign_up);
        mLayoutForgotPassword = findViewById(R.id.layout_sign_in_by_phone_activity_sign_forgot_password);
        mBtnSignIn = findViewById(R.id.btn_sign_in_by_phone_activity_sign_in);
        mProgressBar = findViewById(R.id.progress_bar_sign_in_by_phone_activity_loading);
        mLayoutPhoneNumber = findViewById(R.id.layout_sign_in_by_phone_activity_phone_number);
        mLayoutPassword = findViewById(R.id.layout_sign_in_by_phone_activity_password);
    }

    private void setOnclickListener() {
        mLayoutSignUp.setOnClickListener(this::onClick);
        mLayoutForgotPassword.setOnClickListener(this::onClick);
        mBtnSignIn.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_sign_in_by_phone_activity_sign_sign_up: {
                Intent intent = new Intent(SignInByPhoneActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.layout_sign_in_by_phone_activity_sign_forgot_password: {
                Intent intent = new Intent(SignInByPhoneActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_sign_in_by_phone_activity_sign_in: {
                signIn();
                break;
            }
            default:
                break;

        }
    }

    private void signIn() {

    }

    private void verifyPhoneNumber() {

    }
}
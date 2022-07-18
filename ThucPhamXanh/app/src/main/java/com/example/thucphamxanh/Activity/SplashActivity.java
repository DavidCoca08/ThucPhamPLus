package com.example.thucphamxanh.Activity;

import android.content.Intent;

import android.content.SharedPreferences;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;


import com.example.thucphamxanh.IntroActivity;

import com.example.thucphamxanh.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {


    SharedPreferences introActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
            }
        }, 2000);
    }

    private void nextActivity() {


        introActivity = getSharedPreferences("introActivity",MODE_PRIVATE);
        boolean isFirstTime = introActivity.getBoolean("firstTime",true);
        if (isFirstTime){
            SharedPreferences.Editor editor = introActivity.edit();
            editor.putBoolean("firstTime",false);
            editor.commit();

            Intent intent= new Intent(this, IntroActivity.class);
            startActivity(intent);
            finish();
        }else {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Intent intent = new Intent(this, com.example.thucphamxanh.Activity.SignInActivity.class);
            if (user != null) {
                intent = new Intent(this, com.example.thucphamxanh.Activity.SignInActivity.class);
            }
            startActivity(intent);
            finish();

        }


    }
}
package com.example.duan1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    SharedPreferences introActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                introActivity = getSharedPreferences("introActivity",MODE_PRIVATE);
                boolean isFirstTime = introActivity.getBoolean("firstTime",true);
                if (isFirstTime){
                    SharedPreferences.Editor editor = introActivity.edit();
                    editor.putBoolean("firstTime",false);
                    editor.commit();

                    Intent intent= new Intent(SplashScreenActivity.this,IntroActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent= new Intent(SplashScreenActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        },2000);
    }
}
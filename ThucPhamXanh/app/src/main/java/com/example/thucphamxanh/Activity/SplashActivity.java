package com.example.thucphamxanh.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thucphamxanh.R;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
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

        getSupportActionBar().hide(); // Ẩn actionbar
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
            SharedPreferences sharedPreferences = getSharedPreferences("My_User",MODE_PRIVATE);
            boolean wasLogged = sharedPreferences.getBoolean("logged", false);
            Log.d(TAG, "onCreate: " + wasLogged);
            if (wasLogged) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            } else {
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
            }
            finishAffinity();
            /*FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Intent intent = new Intent(this, SignInActivity.class);
            if (user != null) {
                intent = new Intent(this, SignInActivity.class);
                int a  =1;
            }
            startActivity(intent);
            finish();*/

        }


    }
}
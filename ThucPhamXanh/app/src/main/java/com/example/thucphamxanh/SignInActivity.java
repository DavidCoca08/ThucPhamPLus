package com.example.thucphamxanh;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "SignInActivity";
    private LinearLayout layoutSignUp;
    private TextInputLayout formEmail, formPassword;
    private Button btnSignIn;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initUI();
    }

    private void setOnclickListener() {
        layoutSignUp.setOnClickListener(this::onClick);
        btnSignIn.setOnClickListener(this::onClick);
    }


    private void initUI() {
        layoutSignUp = findViewById(R.id.layout_SignInActivity_signIn);
        btnSignIn = findViewById(R.id.btn_SignInActivity_signIn);
        progressBar = findViewById(R.id.progressBar_SignInActivity_loadingLogin);
        formEmail = findViewById(R.id.form_SignInActivity_email);
        formPassword = findViewById(R.id.form_SignInActivity_password);
        setOnclickListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_SignInActivity_signIn:
//                Log.d(TAG,  TAG + " onClick: sign up" );
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_SignInActivity_signIn:
//                Log.d(TAG,  TAG + " onClick: sign in" );
                login();
                break;
            default:
                Toast.makeText(this, "Chức năng đang phát triển", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void login() {
        String email = formEmail.getEditText().getText().toString().trim();
        String password = formPassword.getEditText().getText().toString().trim();
        Log.d(TAG, "login: start");
        if (!validate(email, password)) return;

        FirebaseAuth auth = FirebaseAuth.getInstance();
        progressBar.setVisibility(View.VISIBLE);
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Successful(task);
                            Log.d(TAG, "onComplete: Successful");
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            unSuccessful(task);
                            Log.d(TAG, "onComplete: unSuccessful");
//                            updateUI(null);
                        }
                    }

                    private void unSuccessful(Task<AuthResult> task) {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(SignInActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }

                    private void Successful(Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = auth.getCurrentUser();
                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                });
        Log.d(TAG, "login: end");
    }

    private boolean validate(String email, String password) {
        try {
            if (email.isEmpty() && password.isEmpty()) throw new IllegalArgumentException("email and password is empty");
            else if (email.isEmpty()) throw new IllegalArgumentException("email is empty");
            else if (password.isEmpty()) throw new IllegalArgumentException("password is empty");
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("email and password is empty")) {
                Toast.makeText(this, "Email and Password are empty", Toast.LENGTH_SHORT).show();
            }
            else if (e.getMessage().equals("email is empty")) {
                Toast.makeText(this, "Email is empty", Toast.LENGTH_SHORT).show();
            }
            else if (e.getMessage().equals("password is empty")) {
                Toast.makeText(this, "password empty", Toast.LENGTH_SHORT).show();
            }
            e.printStackTrace();
            return false;
        }
        return true;

    }
}
package com.example.thucphamxanh.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thucphamxanh.Model.User;
import com.example.thucphamxanh.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "SignUpActivity";
    private EditText etEmail, etPassword, etConfirmPassword;
    private Button btnSignUp;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initUI();
    }

    private void initUI() {
        etEmail = findViewById(R.id.et_SignUpActivity_email);
        etPassword = findViewById(R.id.et_SignUpActivity_password);
        etConfirmPassword = findViewById(R.id.et_SignUpActivity_confirmPassword);
        btnSignUp = findViewById(R.id.btn_SignUpActivity_signUp);
        progressDialog = new ProgressDialog(this);
        setOnclickListener();
    }

    private void setOnclickListener() {
        btnSignUp.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_SignUpActivity_signUp:
                signUp();
                break;
        }
    }

    private void signUp() {
        String strEmail = etEmail.getText().toString().trim();
        String strPassword = etPassword.getText().toString().trim();
        String strConfirmPassword = etConfirmPassword.getText().toString().trim();
        if (validate(strEmail, strPassword, strConfirmPassword)) {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            progressDialog.show();
            auth.createUserWithEmailAndPassword(strEmail, strPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser userAuth = auth.getCurrentUser();
                                User user = new User();
                                user.setEmail(etEmail.getText().toString());
                                user.setPassword(etPassword.getText().toString());
                                user.setId(userAuth.getUid());
                                writeNewUser(user);
                                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                                //
                                startActivity(intent);
                                finishAffinity();
//                            updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                            }
                        }

                        private void writeNewUser(User user) {
                            DatabaseReference databaseReference;
                            databaseReference = FirebaseDatabase.getInstance().getReference();
                            databaseReference
                                    .child("users")
                                    .child("id")
                                    .setValue(user.getId());
                            databaseReference
                                    .child("users")
                                    .child("email")
                                    .setValue(user.getEmail());
                            databaseReference
                                    .child("users")
                                    .child("role")
                                    .setValue("customer");
                        }
                    });
        } else {
            Toast.makeText(SignUpActivity.this, "Create account failed", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validate(String strEmail, String strPassword, String strConfirmPassword) {
        if (strEmail.isEmpty()
                || strPassword.isEmpty()
                || strConfirmPassword.isEmpty())
            return false;
        return true;
    }
}
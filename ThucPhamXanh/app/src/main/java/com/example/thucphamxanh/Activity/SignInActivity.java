package com.example.thucphamxanh.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thucphamxanh.Model.Partner;
import com.example.thucphamxanh.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "SignInActivity";
    private LinearLayout layoutSignUp;
    private TextInputLayout formEmail, formPassword;
    private Button btnSignIn;
    private ProgressBar progressBar;
    private List<Partner> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initUI();
        getDataSpf();
        list = getAllPartner();
        Log.d(TAG, "onCreate: " + list.toString());

        getSupportActionBar().hide(); // Ẩn actionbar
    }

    private void setOnclickListener() {
        layoutSignUp.setOnClickListener(this::onClick);
        btnSignIn.setOnClickListener(this::onClick);
    }


    private void initUI() {
        layoutSignUp = findViewById(R.id.layout_SignInActivity_signIn);
        btnSignIn = findViewById(R.id.btn_SignInActivity_signIn);
        progressBar = findViewById(R.id.progressBar_SignInActivity_loadingLogin);
        progressBar.setVisibility(View.INVISIBLE);
        formEmail = findViewById(R.id.form_SignInActivity_email);
        formPassword = findViewById(R.id.form_SignInActivity_password);
        formPassword.setErrorEnabled(true);
        formEmail.setErrorEnabled(true);
        setOnclickListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_SignInActivity_signIn:
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_SignInActivity_signIn:
                if (logins()== false){
                    userLogin();
                }
                logins();



                break;
            default:
                Toast.makeText(this, "Chức năng đang phát triển", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void userLogin() {
        formEmail.setError(null);
        formPassword.setError(null);
        String phoneNumber = formEmail.getEditText().getText().toString().trim();
        String passwordUser = formPassword.getEditText().getText().toString().trim();
        if (!validate(phoneNumber, passwordUser)) return;
        progressBar.setVisibility(View.VISIBLE);
        final DatabaseReference rootReference = FirebaseDatabase.getInstance().getReference();
        rootReference.child("User").child(phoneNumber)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        progressBar.setVisibility(View.INVISIBLE);
                        if (snapshot.exists()) {
                            String password = snapshot.child("password").getValue(String.class);
                            if (password.equals(passwordUser)) {
                                //TODO ĐĂNG NHẬP VÀO APP
                                remember("user", phoneNumber);
                                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                startActivity(intent);
                                finishAffinity();
                                return;
                            }
                            //TODO THÔNG BÁO MẬT KHẨU KHÔNG ĐÚNG
                            formPassword.setError("Mật khẩu không đúng");
                            return;
                        }
                        //TODO THÔNG BÁO TÀI KHOẢN CHƯA TỒN TẠI
                        formEmail.setError("Tài khoản không tồn tại");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        //TODO THÔNG BÁO LỖI KHI ĐĂNG NHẬP
                        progressBar.setVisibility(View.INVISIBLE);
                        Log.e(TAG, "onCancelled: ", error.toException());
                    }
                });
    }
    @Deprecated
    private void login() {
        String email = formEmail.getEditText().getText().toString().trim();
        String password = formPassword.getEditText().getText().toString().trim();
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
    public boolean logins(){
        //TODO validate partner login
        String email = formEmail.getEditText().getText().toString().trim();
        String password = formPassword.getEditText().getText().toString().trim();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUserPartner().equals(email) && list.get(i).getPasswordPartner().equals(password)){
                remember("partner", String.valueOf(list.get(i).getIdPartner()));
                Log.d("aaaaaa",list.get(i).getUserPartner());
                Log.d("aaaaaa",list.get(i).getPasswordPartner());
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        if (email.equals("admin") && password.equals("admin") ){
            remember("admin", "admin");
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }
    public void remember(String role, String id){
        String email = formEmail.getEditText().getText().toString().trim();
        String password = formPassword.getEditText().getText().toString().trim();
        SharedPreferences sharedPreferences = getSharedPreferences("My_User",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", email);
        editor.putString("password", password);
        editor.putString("role", role);
        editor.putString("id", id);
        editor.apply();
    }
    public void getDataSpf(){
        SharedPreferences sharedPreferences = getSharedPreferences("My_User",MODE_PRIVATE);
        formEmail.getEditText().setText(sharedPreferences.getString("username",""));
        formPassword.getEditText().setText(sharedPreferences.getString("password",""));
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
    public List<Partner> getAllPartner(){
        ProgressDialog progressDialog = new ProgressDialog(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Partner");
        List<Partner> list1 = new ArrayList<>();
        progressDialog.show();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1.clear();
                for (DataSnapshot snap : snapshot.getChildren()){
                    Partner partner = snap.getValue(Partner.class);
                    list1.add(partner);
                }
                progressDialog.dismiss();
                Log.d(TAG, "onDataChange: " + list.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });
        return list1;
    }

}
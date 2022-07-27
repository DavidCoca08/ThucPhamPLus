package com.example.thucphamxanh.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thucphamxanh.Model.User;
import com.example.thucphamxanh.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "SignUpActivity";
    private static final String NUMBER_PHONE_INVALID = "number phone invalid";
    private static final String FIELDS_EMPTY = "fields are empty";
    private static final String REGEX_PHONE_NUMBER = "^(((03||09)[2-8])\\d{7})$";
    private static final String PASSWORD_INVALID = "password less than 6 characters";
    private static final String PASSWORD_NOT_MATCH = "password less than 6 characters";
    private TextInputLayout mFormPhoneNumber,
            mFormUserName,
            mFormPassword,
            mFormConfirmPassword,
            mFormAddress;
    private Button mBtnSignUp;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initUI();
    }

    private void initUI() {
        mFormPhoneNumber = findViewById(R.id.form_SignUpActivity_phone_number);
        mFormUserName = findViewById(R.id.form_SignUpActivity_user_name);
        mFormPassword = findViewById(R.id.form_SignUpActivity_password);
        mFormConfirmPassword = findViewById(R.id.form_SignUpActivity_confirmPassword);
        mFormAddress = findViewById(R.id.form_SignUpActivity_address);
        mBtnSignUp = findViewById(R.id.btn_SignUpActivity_signUp);
        mProgressDialog = new ProgressDialog(this);
        setOnclickListener();
    }

    private void setOnclickListener() {
        mBtnSignUp.setOnClickListener(this::onClick);
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
        final DatabaseReference rootReference = FirebaseDatabase.getInstance().getReference();

        mFormPhoneNumber.setErrorEnabled(false);
        mFormUserName.setErrorEnabled(false);
        mFormPassword.setErrorEnabled(false);
        mFormConfirmPassword.setErrorEnabled(false);
        mFormAddress.setErrorEnabled(false);
        String strPhoneNumber = Objects.requireNonNull(mFormPhoneNumber.getEditText()).getText().toString().trim();
        String strUserName = mFormUserName.getEditText().getText().toString().trim();
        String strPassword = mFormPassword.getEditText().getText().toString().trim();
        String strConfirmPassword = mFormConfirmPassword.getEditText().getText().toString().trim();
        String strAddress = mFormAddress.getEditText().getText().toString().trim();
        try {
            validate(strPhoneNumber,
                    strUserName,
                    strPassword,
                    strConfirmPassword,
                    strAddress);
            User user = new User();
            user.setPhoneNumber(strPhoneNumber);
            user.setName(strUserName);
            user.setPassword(strPassword);
            user.setAddress(strAddress);
            user.setStrUriAvatar("");
            user.setId(strPhoneNumber);
            mProgressDialog.setTitle("Tạo tài khoản");
            mProgressDialog.setMessage("Vui lòng đợi trong giây lát");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
            rootReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.child("User").child(strPhoneNumber).exists()) {
                        Map<String, Object> userDataMap = user.toMap();
                        rootReference.child("User")
                                .child(strPhoneNumber)
                                .updateChildren(userDataMap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        mProgressDialog.dismiss();
                                        Toast.makeText(SignUpActivity.this
                                                        , "Tạo tài khoản thành công"
                                                        , Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(SignUpActivity.this
                                                        , "Tạo tài khoản thất bại"
                                                        , Toast.LENGTH_LONG)
                                                .show();
                                        mProgressDialog.dismiss();
                                    }
                                });
                    } else {
                        //TODO thông báo số điện thoại đã tồn tại tới view
                        mProgressDialog.dismiss();
                        mFormPhoneNumber.setErrorEnabled(true);
                        mFormPhoneNumber.setError("Số điện thoại đã tồn tại");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(SignUpActivity.this
                                    , "Có lỗi khi tạo tài khoản, vui lòng thử lại sau"
                                    , Toast.LENGTH_LONG)
                            .show();
                    Log.e(TAG, "onCancelled: ",error.toException() );
                }
            });
        } catch (NullPointerException e) {
            if (e.getMessage().equals(FIELDS_EMPTY)) {
                setErrorEmpty();
                //TODO thông báo lỗi khi empty
            } else {
                Log.e(TAG, "signUp: ", e);
            }
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals(NUMBER_PHONE_INVALID)) {
                mFormPhoneNumber.setErrorEnabled(true);
                mFormPhoneNumber.setError("Số điện thoại không hợp lệ");
            } else if (e.getMessage().equals(PASSWORD_INVALID)) {
                mFormPassword.setErrorEnabled(true);
                mFormPassword.setError("Mật khẩu không hợp lệ");
            } else if (e.getMessage().equals(PASSWORD_NOT_MATCH)) {
                mFormConfirmPassword.setErrorEnabled(true);
                mFormConfirmPassword.setError("Mật khẩu không trùng nhau");
            } else {
                Log.e(TAG, "signUp: ", e);
            }
        } catch (Exception e) {
            //TODO ngoại lệ gì đó chưa bắt được
            Log.e(TAG, "signUp: ", e);
        }

        /*try {
            validate(strPhoneNumber,
                    strUserName,
                    strPassword,
                    strConfirmPassword,
                    strAddress);
            FirebaseAuth auth = FirebaseAuth.getInstance();
            mProgressDialog.show();
            auth.createUserWithEmailAndPassword(strPhoneNumber, strPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            mProgressDialog.dismiss();
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser userAuth = auth.getCurrentUser();
                                User user = new User();
                                user.setEmail(mEtPhoneNumber.getText().toString());
                                user.setPassword(mEtPassword.getText().toString());
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
                                    .child(user.getId())
                                    .child("id")
                                    .setValue(user.getId());
                            databaseReference
                                    .child("users")
                                    .child(user.getId())
                                    .child("email")
                                    .setValue(user.getEmail());
                            databaseReference
                                    .child("users")
                                    .child(user.getId())
                                    .child("role")
                                    .setValue("customer");
                        }
                    });

        } catch (NullPointerException e) {
            if (e.getMessage().equals(FIELDS_EMPTY)) {
                //TODO thông báo lỗi khi empty
            } else {
                Log.e(TAG, "signUp: ", e);
            }
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals(NUMBER_PHONE_INVALID)) {
                //TODO thông báo lỗi khi số điện thoại không đúng định dạng
            } else if (e.getMessage().equals(PASSWORD_INVALID)) {
                //TODO thông báo lỗi khi mật khẩu không hợp lệ
            } else if (e.getMessage().equals(PASSWORD_NOT_MATCH)) {
                //TODO thông báo lỗi số điện thoại không trùng nhau
            } else {
                Log.e(TAG, "signUp: ", e);
            }
        } catch (Exception e) {
            //TODO ngoại lệ gì đó chưa bắt được
            Log.e(TAG, "signUp: ", e);
        }*/

    }

    private void setErrorEmpty() {
        if (mFormPhoneNumber.getEditText().getText().toString().isEmpty()) {
            mFormPhoneNumber.setErrorEnabled(true);
            mFormPhoneNumber.setError("Số điện thoại không được để trống");
        }
        if (mFormUserName.getEditText().getText().toString().isEmpty()) {
            mFormUserName.setErrorEnabled(true);
            mFormUserName.setError("Họ tên không được để trống");
        }
        if (mFormPassword.getEditText().getText().toString().isEmpty()) {
            mFormPassword.setErrorEnabled(true);
            mFormPassword.setError("Mật khẩu không được để trống");
        }
        if (mFormConfirmPassword.getEditText().getText().toString().isEmpty()) {
            mFormConfirmPassword.setErrorEnabled(true);
            mFormConfirmPassword.setError("Xác nhận mật khẩu không được để trống");
        }
        if (mFormAddress.getEditText().getText().toString().isEmpty()) {
            mFormAddress.setErrorEnabled(true);
            mFormAddress.setError("Địa chỉ không được để trống");
        }

    }

    private void validate(String strPhoneNumber,
                          String strUserName,
                          String strPassword,
                          String strConfirmPassword,
                          String strAddress) {
        if (strPhoneNumber.isEmpty()
                || strPassword.isEmpty()
                || strConfirmPassword.isEmpty()
                || strUserName.isEmpty()
                || strAddress.isEmpty())
            throw new NullPointerException(FIELDS_EMPTY);
        if (!strPhoneNumber.matches(REGEX_PHONE_NUMBER))
            throw  new IllegalArgumentException(NUMBER_PHONE_INVALID);
        if (strPassword.length() < 6)
            throw  new IllegalArgumentException(PASSWORD_INVALID);
        if (!strConfirmPassword.equals(strPassword))
            throw new IllegalArgumentException(PASSWORD_NOT_MATCH);
    }
}
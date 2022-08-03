package com.example.thucphamxanh.Fragment;

import static com.example.thucphamxanh.constant.Profile.*;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.thucphamxanh.Fragment.Profile.ProfileViewModel;
import com.example.thucphamxanh.Model.Partner;
import com.example.thucphamxanh.databinding.FragmentChangePasswordBinding;
import com.google.android.material.textfield.TextInputLayout;

public class ChangePasswordFragment extends Fragment {
    private final String TAG = "ChangePasswordFragment";
    private FragmentChangePasswordBinding binding;
    private TextInputLayout oldPass, newPass, reNewPass;
    private Button btnChangePass;
    private Partner mPartner;
    private ProfileViewModel mProfileFragment;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false);
        initUi();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initListener();
    }

    private void initListener() {
        oldPass.setErrorEnabled(true);
        newPass.setErrorEnabled(true);
        reNewPass.setErrorEnabled(true);
        btnChangePass.setOnClickListener(view -> {
            oldPass.setError(null);
            newPass.setError(null);
            reNewPass.setError(null);
            try {
                String strOldPass = oldPass.getEditText().getText().toString();
                String strNewPass = newPass.getEditText().getText().toString();
                String strConfirmPass = reNewPass.getEditText().getText().toString();
                validate(strOldPass, strNewPass, strConfirmPass);
                changePass();
            } catch (NullPointerException e) {
                if (e.getMessage().equals(FIELDS_EMPTY)) {
                    setErrorEmpty();
                } else {
                    Log.e(TAG, "signUp: ", e);
                }
            } catch (IllegalArgumentException e) {
                if (e.getMessage().equals(PASSWORD_INVALID)) {
                    newPass.setError("Mật khẩu phải từ 6 kí tự trở lên");
                } else if (e.getMessage().equals(PASSWORD_NOT_MATCH)) {
                    reNewPass.setError("Mật khẩu mới không trùng nhau");
                } else if (e.getMessage().equals(PASSWORD_INVALID_2)) {
                    newPass.setError("Mật khẩu mới không được giống mật khẩu cũ");
                } else if (e.getMessage().equals(PASSWORD_INVALID_3)) {
                    oldPass.setError("Mật khẩu cũ không đúng");
                } else {
                    Log.e(TAG, "signUp: ", e);
                }
            } catch (Exception e) {
                Log.e(TAG, "signUp: ", e);
            }
        });
    }

    private void setErrorEmpty() {
        if (oldPass.getEditText().getText().toString().isEmpty()) oldPass.setError("Không được để trống");
        if (newPass.getEditText().getText().toString().isEmpty()) newPass.setError("Không được để trống");
        if (reNewPass.getEditText().getText().toString().isEmpty()) reNewPass.setError("Không được để trống");

    }

    private void changePass() {
        Log.d(TAG, "changePass: change password");
    }

    private void initViewModel() {
        mProfileFragment = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        mPartner = mProfileFragment.getPartner().getValue();
    }

    public void initUi(){
        oldPass = binding.textChangePasswordFragmentOldPass;
        newPass = binding.textChangePasswordFragmentNewPass;
        reNewPass = binding.textChangePasswordFragmentReNewPass;
        btnChangePass = binding.btnChangePasswordFragmentChange;
    }
    @Deprecated
    public boolean validate(){
        if (oldPass.getEditText().getText().toString().isEmpty() && newPass.getEditText().getText().toString().isEmpty() && reNewPass.getEditText().getText().toString().isEmpty()){

            oldPass.setError("không được để trống");
            newPass.setError("không được để trống");
            reNewPass.setError("không được để trống");
            return false;
        } else {
            oldPass.setError(null);
            newPass.setError(null);
            reNewPass.setError(null);
        }
        if (oldPass.getEditText().getText().toString().isEmpty()){
            oldPass.setError("không được để trống");
            return false;
        } else {
            oldPass.setError(null);
        }

        if (newPass.getEditText().getText().toString().isEmpty()){
            newPass.setError("không được để trống");
            return false;
        } else {
            newPass.setError(null);
        }

        if (reNewPass.getEditText().getText().toString().isEmpty()){
            reNewPass.setError("không được để trống");
            return false;
        } else if (!reNewPass.getEditText().getText().toString().equals(newPass.getEditText().getText().toString())){
            reNewPass.setError("Mật khẩu nhập lại không trùng khớp");
            return false;
        } else {
            reNewPass.setError(null);
        }
        return true;
    }
    private void validate(String oldPassword,
                          String newPassword,
                          String ConfirmPassword) {
        if (oldPassword.isEmpty() ||
                newPassword.isEmpty() ||
                ConfirmPassword.isEmpty())
            throw new NullPointerException(FIELDS_EMPTY);
        if (newPassword.length() < 6) throw  new IllegalArgumentException(PASSWORD_INVALID);
        if (!ConfirmPassword.equals(newPassword)) throw new IllegalArgumentException(PASSWORD_NOT_MATCH);
        if (newPassword.equals(oldPassword)) throw new IllegalArgumentException(PASSWORD_INVALID_2);
        if (!oldPassword.equals(mPartner.getPasswordPartner())) throw new IllegalArgumentException(PASSWORD_INVALID_3);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
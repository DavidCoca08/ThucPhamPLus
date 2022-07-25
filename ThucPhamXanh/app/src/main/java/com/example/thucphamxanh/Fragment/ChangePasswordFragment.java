package com.example.thucphamxanh.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.thucphamxanh.R;
import com.example.thucphamxanh.databinding.FragmentChangePasswordBinding;
import com.google.android.material.textfield.TextInputLayout;

public class ChangePasswordFragment extends Fragment {
    FragmentChangePasswordBinding binding;
    TextInputLayout oldPass, newPass, reNewPass;
    Button btnChangePass;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false);
        initUi();

        btnChangePass.setOnClickListener(view -> {
            if(validate()==true){
                Toast.makeText(getContext(), "Test oke...",Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }

    public void initUi(){
        oldPass = binding.textChangePasswordFragmentOldPass;
        newPass = binding.textChangePasswordFragmentNewPass;
        reNewPass = binding.textChangePasswordFragmentReNewPass;
        btnChangePass = binding.btnChangePasswordFragmentChange;
    }

    public boolean validate(){
        if(oldPass.getEditText().getText().toString().isEmpty() && newPass.getEditText().getText().toString().isEmpty() && reNewPass.getEditText().getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Vui lòng nhập vào hết các trường",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(oldPass.getEditText().getText().toString().isEmpty()){
            oldPass.setError("Vui lòng nhập mật khẩu cũ");
            return false;
        }else {
            oldPass.setError("");
        }

        if(newPass.getEditText().getText().toString().isEmpty()){
            newPass.setError("Vui lòng nhập mật khẩu mới");
            return false;
        }else {
            newPass.setError("");
        }

        if(reNewPass.getEditText().getText().toString().isEmpty()){
            reNewPass.setError("Vui lòng nhập lại mật khẩu mới");
            return false;
        }else if(!reNewPass.getEditText().getText().toString().equals(newPass.getEditText().getText().toString())){
            reNewPass.setError("Mật khẩu nhập lại không trùng khớp");
            return false;
        } else {
            reNewPass.setError("");
        }
        return true;
    }

}
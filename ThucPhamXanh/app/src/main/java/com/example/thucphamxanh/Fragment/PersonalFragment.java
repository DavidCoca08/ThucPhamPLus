package com.example.thucphamxanh.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thucphamxanh.Activity.MainActivity;
import com.example.thucphamxanh.Activity.SignInActivity;
import com.example.thucphamxanh.Fragment.Profile.ProfileFragment;
import com.example.thucphamxanh.R;
import com.example.thucphamxanh.databinding.FragmentPersonalBinding;
import com.google.firebase.auth.FirebaseAuth;

public class PersonalFragment extends Fragment {
    FragmentPersonalBinding binding;
    Button btn_logout_personal, btn_changepassword_personal;
    TextView tvNumberPhoneUser, tvEdit;
    ImageView imgUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        binding = FragmentPersonalBinding.inflate(inflater, container, false);
        btn_logout_personal = binding.btnPersonalFragmentLogoutPersonal;
        btn_changepassword_personal = binding.btnPersonalFragmentChangePasswordPersonal;
        tvNumberPhoneUser = binding.tvPersonalFragmentNumberPhoneUser;
        tvEdit = binding.tvPersonalFragmentEditUser;
        imgUser = binding.imgPersonalFragmentImgUser;
        imgUser.setImageResource(R.drawable.ic_avatar_default);
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        imgUser.setOnClickListener(view -> {
            Toast.makeText(getContext(), "imgUser.getS00ourceLayoutResId()",Toast.LENGTH_SHORT).show();
        });

        btn_logout_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SignInActivity.class);
                startActivity(intent);
                getActivity().finishAffinity();
            }
        });

        btn_changepassword_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.drawer_layout, new ChangePasswordFragment()).addToBackStack(null).commit();
            }
        });

        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.drawer_layout, new ProfileFragment()).addToBackStack(null).commit();
            }
        });

        return binding.getRoot();
    }
}
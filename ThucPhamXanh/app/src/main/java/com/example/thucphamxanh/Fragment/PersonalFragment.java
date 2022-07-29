package com.example.thucphamxanh.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.thucphamxanh.Activity.MainActivity;
import com.example.thucphamxanh.Activity.SignInActivity;
import com.example.thucphamxanh.R;
import com.google.firebase.auth.FirebaseAuth;

public class PersonalFragment extends Fragment {

    Button btn_logout_personal;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_personal, container, false);

        btn_logout_personal = view.findViewById(R.id.btn_logout_personal);

        btn_logout_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(getActivity(), SignInActivity.class);
                startActivity(intent);
                getActivity().finishAffinity();

            }
        });


        return view;
    }
}
package com.example.thucphamxanh.Fragment.ProductFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thucphamxanh.R;


public class FoodFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("My_User", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("username","");
        if(user.equals("admin")){
           view.findViewById(R.id.fab_addFood_fragment).setVisibility(View.GONE);
        }
        return view;
    }
}
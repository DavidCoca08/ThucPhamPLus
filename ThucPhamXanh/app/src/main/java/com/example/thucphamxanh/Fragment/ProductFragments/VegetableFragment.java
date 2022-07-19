package com.example.thucphamxanh.Fragment.ProductFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.thucphamxanh.Model.Product;
import com.example.thucphamxanh.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class VegetableFragment extends Fragment {
    private FloatingActionButton btn_addVegetable;
    private List<Product> list;
    private List<Product> listVegetable;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vegetable, container, false);

        return view;
    }
}
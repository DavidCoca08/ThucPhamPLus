package com.example.thucphamxanh.Fragment.StatisticFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.thucphamxanh.databinding.FragmentStatisticsByMonthBinding;

public class StatisticsByMonthFragment extends Fragment {
    FragmentStatisticsByMonthBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStatisticsByMonthBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
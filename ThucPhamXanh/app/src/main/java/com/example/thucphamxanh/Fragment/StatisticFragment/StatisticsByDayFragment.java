package com.example.thucphamxanh.Fragment.StatisticFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.thucphamxanh.databinding.FragmentStatisticsByDayBinding;

public class StatisticsByDayFragment extends Fragment {
    FragmentStatisticsByDayBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStatisticsByDayBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
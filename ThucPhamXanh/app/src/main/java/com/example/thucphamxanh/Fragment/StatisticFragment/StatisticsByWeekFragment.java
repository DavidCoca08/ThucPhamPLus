package com.example.thucphamxanh.Fragment.StatisticFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import com.example.thucphamxanh.databinding.FragmentStatisticsByWeekBinding;

public class StatisticsByWeekFragment extends Fragment {
    FragmentStatisticsByWeekBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStatisticsByWeekBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
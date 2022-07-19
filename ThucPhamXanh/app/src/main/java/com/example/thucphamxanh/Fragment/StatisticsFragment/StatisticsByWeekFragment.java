package com.example.thucphamxanh.Fragment.StatisticsFragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.thucphamxanh.R;
import com.example.thucphamxanh.databinding.FragmentStatisticsByDayBinding;
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
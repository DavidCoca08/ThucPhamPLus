package com.example.thucphamxanh.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.thucphamxanh.Fragment.StatisticFragment.StatisticsByDayFragment;
import com.example.thucphamxanh.Fragment.StatisticFragment.StatisticsByMonthFragment;
import com.example.thucphamxanh.Fragment.StatisticFragment.StatisticsByWeekFragment;
import com.example.thucphamxanh.Fragment.StatisticFragment.MainStatisticsFragment;

public class StatisticsFragment_Adapter extends FragmentStateAdapter {
    public StatisticsFragment_Adapter(@NonNull MainStatisticsFragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0){
            return new StatisticsByDayFragment();
        }else if(position==1){
            return new StatisticsByWeekFragment();
        }else {
            return new StatisticsByMonthFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

package com.example.thucphamxanh.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.thucphamxanh.Fragment.StatisticsFragment.StatisticsByDayFragment;
import com.example.thucphamxanh.Fragment.StatisticsFragment.StatisticsByMonthFragment;
import com.example.thucphamxanh.Fragment.StatisticsFragment.StatisticsByWeekFragment;
import com.example.thucphamxanh.Fragment.StatisticsFragment.StatisticsFragment;

public class StatisticsFragment_Adapter extends FragmentStateAdapter {
    public StatisticsFragment_Adapter(@NonNull StatisticsFragment fragmentActivity) {
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

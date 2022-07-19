package com.example.thucphamxanh.Fragment.StatisticFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.thucphamxanh.Adapter.StatisticsFragment_Adapter;
import com.example.thucphamxanh.databinding.FragmentStatisticsBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainStatisticsFragment extends Fragment {
    FragmentStatisticsBinding binding;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    StatisticsFragment_Adapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStatisticsBinding.inflate(inflater, container, false);

        tabLayout = binding.tabLayoutStatisticsFragment;
        viewPager2 = binding.viewPager2StatisticsFragment;
        adapter = new StatisticsFragment_Adapter(MainStatisticsFragment.this);
        viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position==0) tab.setText("Hôm nay");
                else if(position==1) tab.setText("Tuần này");
                else tab.setText("Tháng này");
            }
        }).attach();
        return binding.getRoot();
    }
}
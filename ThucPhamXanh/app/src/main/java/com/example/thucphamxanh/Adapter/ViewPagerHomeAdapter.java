package com.example.thucphamxanh.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.thucphamxanh.Fragment.HomeFragment;
import com.example.thucphamxanh.Fragment.HomePageFragment;
import com.example.thucphamxanh.Fragment.OrderFragment;
import com.example.thucphamxanh.Fragment.PersonalFragment;
import com.example.thucphamxanh.Fragment.VoucherFragment;

public class ViewPagerHomeAdapter extends FragmentStatePagerAdapter {
    public ViewPagerHomeAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomePageFragment();
            case 1:
                return new VoucherFragment();
            case 2:
                return new OrderFragment();
            default:
                return new PersonalFragment();
        }

    }

    @Override
    public int getCount() {
        return 4;
    }
}

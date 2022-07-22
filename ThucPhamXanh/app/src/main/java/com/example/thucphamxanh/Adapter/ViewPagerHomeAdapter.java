package com.example.thucphamxanh.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

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
            case 1:
                return new VoucherFragment();
            case 2:
                return new OrderFragment();
            case 3:
                return new PersonalFragment();
            case 0:
                return new HomePageFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}

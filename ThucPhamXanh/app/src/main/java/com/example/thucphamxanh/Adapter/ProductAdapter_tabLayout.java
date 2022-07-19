package com.example.thucphamxanh.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.thucphamxanh.Fragment.ProductFragments.FruitFragment;
import com.example.thucphamxanh.Fragment.ProductFragments.MeatFragment;
import com.example.thucphamxanh.Fragment.ProductFragments.VegetableFragment;

public class ProductAdapter_tabLayout extends FragmentStateAdapter {
    public ProductAdapter_tabLayout(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new VegetableFragment();
            case 1: return new FruitFragment();
            default: return new MeatFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

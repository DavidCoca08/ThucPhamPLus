package com.example.thucphamxanh.Fragment.ProductFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.thucphamxanh.Adapter.ProductAdapter_tabLayout;
import com.example.thucphamxanh.Model.Partner;
import com.example.thucphamxanh.databinding.FragmentProductBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProductFragment extends Fragment {

    private FragmentProductBinding binding;
    private TabLayout tabLayoutProduct;
    private ViewPager2 viewPagerProduct;
    private ProductAdapter_tabLayout adapter_tabLayout;
    private Partner partner = new Partner();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductBinding.inflate(inflater, container, false);
        tabLayout();


        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void tabLayout(){
        tabLayoutProduct = binding.tabProductFragment;
        viewPagerProduct = binding.viewPagerProductFragment;
        adapter_tabLayout = new ProductAdapter_tabLayout(this);
        viewPagerProduct.setAdapter(adapter_tabLayout);
        new TabLayoutMediator(tabLayoutProduct, viewPagerProduct, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0: tab.setText("Rau củ");
                        break;
                    case 1: tab.setText("Hoa quả");
                        break;
                    case 2:tab.setText("Thịt");
                        break;
                }
            }
        }).attach();
    }

}
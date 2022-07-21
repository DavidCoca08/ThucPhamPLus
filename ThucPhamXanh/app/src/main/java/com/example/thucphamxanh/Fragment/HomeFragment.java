package com.example.thucphamxanh.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.thucphamxanh.Adapter.ViewPagerHomeAdapter;
import com.example.thucphamxanh.R;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment {

    ViewPager viewPager_Home;
    BottomNavigationView bottomNavigationView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_home, container, false);

        viewPager_Home = view.findViewById(R.id.viewPager_Home);
        bottomNavigationView = view.findViewById(R.id.bottom_nav_Home);

        setUpViewPager();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_home:
                        viewPager_Home.setCurrentItem(0);
                        break;
                    case R.id.action_voucher:
                        viewPager_Home.setCurrentItem(1);
                        break;
                    case R.id.action_order:
                        viewPager_Home.setCurrentItem(2);
                        break;
                    case R.id.action_user:
                        viewPager_Home.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });

        return  view;

    }

    private void setUpViewPager(){
        ViewPagerHomeAdapter viewPagerHomeAdapter = new ViewPagerHomeAdapter(getActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager_Home.setAdapter(viewPagerHomeAdapter);

        viewPager_Home.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.action_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.action_voucher).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.action_order).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.action_user).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
package com.example.thucphamxanh.Fragment;

import android.os.Bundle;
import android.os.FileObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thucphamxanh.Adapter.AdapterBill;
import com.example.thucphamxanh.Adapter.AdapterItemBill;
import com.example.thucphamxanh.Adapter.OderViewPagerAdapter;
import com.example.thucphamxanh.Model.Bill;
import com.example.thucphamxanh.Model.Cart;
import com.example.thucphamxanh.R;
import com.example.thucphamxanh.databinding.FragmentBillBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import com.example.thucphamxanh.databinding.FragmentBillBinding;

public class BillFragment extends Fragment {

    private FragmentBillBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBillBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Fragment newFragment = new OrderFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_bill, newFragment);
        transaction.commit();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
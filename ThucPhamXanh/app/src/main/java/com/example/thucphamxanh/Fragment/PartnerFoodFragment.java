package com.example.thucphamxanh.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.thucphamxanh.Adapter.Partner_FoodAdapter;
import com.example.thucphamxanh.Model.Partner;
import com.example.thucphamxanh.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class PartnerFoodFragment extends Fragment {
    RecyclerView recyclerView_Partner_Food;
    LinearLayoutManager linearLayoutManager;
    List<Partner> list;
    Partner_FoodAdapter partner_foodAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_partner_food, container, false);
        recyclerView_Partner_Food = view.findViewById(R.id.recyclerView_Partner_Food);

        list = getAllPartner();
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView_Partner_Food.setLayoutManager(linearLayoutManager);

        partner_foodAdapter = new Partner_FoodAdapter(list);
        recyclerView_Partner_Food.setAdapter(partner_foodAdapter);

        return view;
    }

    public List<Partner> getAllPartner(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Partner");
        List<Partner> list1 = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1.clear();
                for (DataSnapshot snap : snapshot.getChildren()){
                    Partner partner = snap.getValue(Partner.class);
                    list1.add(partner);
                }
                partner_foodAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list1;
    }
}
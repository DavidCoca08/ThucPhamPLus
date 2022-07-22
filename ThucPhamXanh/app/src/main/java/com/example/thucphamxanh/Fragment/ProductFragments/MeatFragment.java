package com.example.thucphamxanh.Fragment.ProductFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thucphamxanh.Adapter.ProductAdapter;
import com.example.thucphamxanh.Model.Product;
import com.example.thucphamxanh.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MeatFragment extends Fragment {

    private List<Product> listMeat;
    private RecyclerView rvMeat;
    private LinearLayoutManager linearLayoutManager;
    private ProductAdapter adapter;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_meat, container, false);
        unitUI();
        return view;
    }
    public void unitUI(){
        listMeat = getVegetableProduct();
        rvMeat = view.findViewById(R.id.rvMeat);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rvMeat.setLayoutManager(linearLayoutManager);
        adapter = new ProductAdapter(listMeat);
        rvMeat.setAdapter(adapter);
    }

    public  List<Product> getVegetableProduct(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Product");
        List<Product> list1 = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                    Product product = snap.getValue(Product.class);
                    if (product.getCodeCategory()==3){
                        list1.add(product);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list1;
    }
}
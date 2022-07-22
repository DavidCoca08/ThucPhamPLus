package com.example.thucphamxanh.Fragment.ProductFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thucphamxanh.Adapter.ProductAdapter;
import com.example.thucphamxanh.Model.Partner;
import com.example.thucphamxanh.Model.Product;
import com.example.thucphamxanh.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FoodFragment extends Fragment {

    private List<Product> listFood;
    private RecyclerView rvFood;
    private LinearLayoutManager linearLayoutManager;
    private ProductAdapter adapter;
    private View view;
    private SharedPreferences sharedPreferences;
    private String user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_food, container, false);
        unitUI();
        sharedPreferences = getContext().getSharedPreferences("My_User", Context.MODE_PRIVATE);
        user = sharedPreferences.getString("username","");
        if(user.equals("admin")){
           view.findViewById(R.id.fab_addFood_fragment).setVisibility(View.GONE);
        }else {
            view.findViewById(R.id.fab_addFood_fragment).setVisibility(View.VISIBLE);
        }


        return view;
    }public void unitUI(){

        listFood = getProductPartner();
        rvFood = view.findViewById(R.id.rvFood);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rvFood.setLayoutManager(linearLayoutManager);
        adapter = new ProductAdapter(listFood);
        adapter.notifyDataSetChanged();
        rvFood.setAdapter(adapter);
    }

    public  List<Product> getProductPartner(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Product");
        List<Product> list1 = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                    Product product = snap.getValue(Product.class);
                    if (user.equals("admin") && product.getCodeCategory()==4){
                        list1.add(product);
                    }else if (product.getUserPartner().equals(user) && product.getCodeCategory()==4 ){
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
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list1;
    }
}
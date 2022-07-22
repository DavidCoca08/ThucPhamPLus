package com.example.thucphamxanh.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.thucphamxanh.Adapter.CartAdapter;
import com.example.thucphamxanh.Model.Cart;
import com.example.thucphamxanh.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView rvCart;
    private List<Cart> list;
    private LinearLayoutManager linearLayoutManager;
    private CartAdapter adapter;
    private TextView tvTotalPrice;
    private Button btn_senBill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        unitUi();
        getCartProduct();
        btn_senBill.setOnClickListener(view -> {

        });
    }
    public void unitUi(){
        rvCart = findViewById(R.id.recyclerView_CartActivity_listCart);
        list=getCartProduct();
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvCart.setLayoutManager(linearLayoutManager);
        adapter = new CartAdapter(list);
        rvCart.setAdapter(adapter);
        tvTotalPrice = findViewById(R.id.tv_CartActivity_totalPrice);
        btn_senBill = findViewById(R.id.btn_CartActivity_btnPay);

    }
    public  List<Cart> getCartProduct(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Cart");
        List<Cart> list1 = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                    Cart cart = snap.getValue(Cart.class);
                    if (cart.getUserClient().equals(firebaseUser.getUid())){
                        list1.add(cart);
                    }
                    int sum = 0;
                    for (int i = 0; i < list.size(); i++) {
                        sum += list1.get(i).getTotalPrice();
                    }
                    tvTotalPrice.setText(String.valueOf(sum));
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
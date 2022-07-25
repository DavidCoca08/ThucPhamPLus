package com.example.thucphamxanh.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.MenuItem;
import android.view.View;

import com.example.thucphamxanh.Adapter.CartAdapter;
import com.example.thucphamxanh.Model.Bill;
import com.example.thucphamxanh.Model.Cart;
import com.example.thucphamxanh.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView rvCart;
    private List<Cart> list;
    private LinearLayoutManager linearLayoutManager;
    private CartAdapter adapter;
    private TextView tvTotalPrice, tvEmptyProduct;
    private Button btn_senBill;
    private List<Bill> listBill;
    private NumberFormat numberFormat = new DecimalFormat("#,##0");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#73DE4E")));
        getSupportActionBar().setTitle("Giỏ hàng");
        unitUi();
        btn_senBill.setOnClickListener(view -> {
        addBill();
        deleteCart();
        });
    }
    public void unitUi(){
        rvCart = findViewById(R.id.recyclerView_CartActivity_listCart);
        list = getCartProduct();
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvCart.setLayoutManager(linearLayoutManager);
        adapter = new CartAdapter(list);
        rvCart.setAdapter(adapter);
        tvTotalPrice = findViewById(R.id.tv_CartActivity_totalPrice);
        btn_senBill = findViewById(R.id.btn_CartActivity_btnPay);
        tvEmptyProduct = findViewById(R.id.tv_CartActivity_emptyProduct);
        listBill = getAllBill();
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

                }
                int sum = 0;
                for (int i = 0; i < list1.size(); i++) {
                    sum += list1.get(i).getTotalPrice();
                }
                tvTotalPrice.setText(numberFormat.format(sum) +" đ");
                if (list1.size()==0){
                    btn_senBill.setEnabled(false);
                }else  btn_senBill.setEnabled(true);
                if(list1.size()<=0){
                    tvEmptyProduct.setVisibility(View.VISIBLE);
                    rvCart.setVisibility(View.INVISIBLE);
                }else {
                    tvEmptyProduct.setVisibility(View.INVISIBLE);
                    rvCart.setVisibility(View.VISIBLE);
                }
                
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list1;
    }
    public void addBill(){
        Bill bill = new Bill();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Bill");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(Calendar.getInstance().getTime());
        SimpleDateFormat timeFormat = new SimpleDateFormat("k:mm");
        String time = timeFormat.format(Calendar.getInstance().getTime());
        if (listBill.size()==0){
            bill.setIdBill(1);
            bill.setIdClient(firebaseUser.getUid());
            bill.setDayOut(date);
            bill.setTimeOut(time);
            bill.setStatus("đang chuẩn bị");
            reference.child(""+1).setValue(bill);
            reference.child(""+1).child("Cart").setValue(list);
        }else {
            int i = listBill.size()-1;
            int id = listBill.get(i).getIdBill()+1;
            bill.setIdBill(id);
            bill.setIdClient(firebaseUser.getUid());
            bill.setDayOut(date);
            bill.setTimeOut(time);
            bill.setStatus("đang chuẩn bị");
            reference.child(""+id).setValue(bill);
            reference.child(""+id).child("Cart").setValue(list);
        }

    }
    public void deleteCart(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Cart");
        for (int i = 0; i < list.size(); i++) {
            reference.child(""+list.get(i).getIdCart()).removeValue();
        }
        if (list.size()==0){
            btn_senBill.setEnabled(false);
        }else  btn_senBill.setEnabled(true);

    }
    public  List<Bill> getAllBill(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Bill");
        List<Bill> list1 = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                    Bill bill = snap.getValue(Bill.class);
                    list1.add(bill);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list1;
    }
}
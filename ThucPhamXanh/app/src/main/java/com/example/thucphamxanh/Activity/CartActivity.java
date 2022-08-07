package com.example.thucphamxanh.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

import com.example.thucphamxanh.Adapter.CartAdapter;
import com.example.thucphamxanh.Model.Bill;
import com.example.thucphamxanh.Model.Cart;
import com.example.thucphamxanh.Model.ProductTop;
import com.example.thucphamxanh.R;
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
    private List<ProductTop> listTop = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private CartAdapter adapter;
    private TextView tvTotalPrice, tvEmptyProduct,tv1, tvHide1, tvHide2;
    private Button btn_senBill, btnEmptyProduct;
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
        for (int i = 0; i < list.size(); i++) {
            addProductTop(list.get(i).getIdProduct(),list.get(i).getNumberProduct());
        }
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
        tv1 = findViewById(R.id.tv1_CartActivity_totalPrice);
        btn_senBill = findViewById(R.id.btn_CartActivity_btnPay);
        tvEmptyProduct = findViewById(R.id.tv_CartActivity_emptyProduct);
        btnEmptyProduct = findViewById(R.id.btn_CartActivity_emptyProduct);
        listBill = getAllBill();
        getProductTop();

        tvHide1 = findViewById(R.id.tvHide1);
        tvHide2 = findViewById(R.id.tvHide2);
    }
    public  List<Cart> getCartProduct(){
        SharedPreferences preferences = getSharedPreferences("My_User",MODE_PRIVATE);
        String user = preferences.getString("username","");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Cart");
        List<Cart> list1 = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                    Cart cart = snap.getValue(Cart.class);
                    if (cart.getUserClient().equals(user)){
                        list1.add(cart);

                    }

                }
                int sum = 0;
                for (int i = 0; i < list1.size(); i++) {
                    sum += list1.get(i).getTotalPrice();
                }
                tvTotalPrice.setText(numberFormat.format(sum));
                tv1.setText(""+sum);
                if(list1.size()==0){
                    tvHide1.setVisibility(View.GONE);
                    tvHide2.setVisibility(View.GONE);
                    btn_senBill.setVisibility(View.GONE);
                    tvTotalPrice.setVisibility(View.GONE);
                    tvEmptyProduct.setVisibility(View.VISIBLE);
                    btnEmptyProduct.setVisibility(View.VISIBLE);
                    btnEmptyProduct.setOnClickListener(view -> {
                        startActivity(new Intent(CartActivity.this, MainActivity.class));
                        finish();
                    });
                    rvCart.setVisibility(View.INVISIBLE);
                }else {
                    tvHide1.setVisibility(View.VISIBLE);
                    tvHide2.setVisibility(View.VISIBLE);
                    btn_senBill.setVisibility(View.VISIBLE);
                    tvEmptyProduct.setVisibility(View.INVISIBLE);
                    btnEmptyProduct.setVisibility(View.INVISIBLE);
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
        SharedPreferences preferences = getSharedPreferences("My_User",MODE_PRIVATE);
        String user = preferences.getString("username","");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Bill");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(Calendar.getInstance().getTime());
        SimpleDateFormat timeFormat = new SimpleDateFormat("k:mm");
        String time = timeFormat.format(Calendar.getInstance().getTime());
        if (listBill.size()==0){
            bill.setIdBill(1);
            bill.setIdClient(user);
            bill.setDayOut(date);
            bill.setTimeOut(time);
            bill.setIdPartner(list.get(0).getIdPartner());
            bill.setTotal(Integer.parseInt(tv1.getText().toString()));
            if (user.equals("admin")){
                bill.setStatus("Yes");
            }else bill.setStatus("No");
            reference.child(""+1).setValue(bill);
            reference.child(""+1).child("Cart").setValue(list);
        }else {
            int i = listBill.size()-1;
            int id = listBill.get(i).getIdBill()+1;
            bill.setIdBill(id);
            bill.setIdClient(user);
            bill.setDayOut(date);
            bill.setTimeOut(time);
            bill.setIdPartner(list.get(0).getIdPartner());
            bill.setTotal(Integer.parseInt(tv1.getText().toString()));
            if (user.equals("admin")){
                bill.setStatus("Yes");
            }else bill.setStatus("No");
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
    public void addProductTop(int id, int amount){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("ProductTop");
        ProductTop top = new ProductTop();
        top.setIdProduct(id);
        top.setAmountProduct(amount);
        if (listTop.size()==0){
            reference.child(""+id).setValue(top);
        }else {
            for (int i = 0; i < listTop.size(); i++) {
                if (id == listTop.get(i).getIdProduct()) {
                    int amounts = listTop.get(i).getAmountProduct() + amount;
                    reference.child("" + id).child("amountProduct").setValue(amounts);
                } else {
                    reference.child("" + id).setValue(top);
                }
            }
        }
    }
    public void getProductTop(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("ProductTop");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listTop.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    ProductTop top = snapshot1.getValue(ProductTop.class);
                    listTop.add(top);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
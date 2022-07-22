package com.example.thucphamxanh.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thucphamxanh.Model.Cart;
import com.example.thucphamxanh.Model.Product;
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
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.viewHolder> {
    private List<Cart> list;
    public CartAdapter(List<Cart> list) {
        this.list=list;
    }
    public static int id =0;
    NumberFormat numberFormatormat = new DecimalFormat("#,##0");
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false);
        return new viewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
            Cart cart = list.get(position);
            byte[] imgByte = Base64.getDecoder().decode(cart.getImgProduct());
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte,0,imgByte.length);
            holder.img_ItemCart_imgProduct.setImageBitmap(bitmap);
            holder.tv_ItemCart_nameProduct.setText(String.valueOf(cart.getNameProduct()));
            holder.tv_ItemCart_priceProduct.setText("Giá: "+numberFormatormat.format(cart.getPriceProduct())+" đ");
            holder.tvAmountProduct.setText(String.valueOf(cart.getNumberProduct()));
            holder.tvTotalProduct.setText("Tổng: "+numberFormatormat.format(cart.getNumberProduct()*cart.getPriceProduct())+" đ");
            holder.imgPlus.setOnClickListener(view -> {
                int amount = Integer.parseInt(holder.tvAmountProduct.getText().toString())+1;
                holder.tvAmountProduct.setText(String.valueOf(amount));
            });
            holder.imgMinus.setOnClickListener(view -> {
                int amount = Integer.parseInt(holder.tvAmountProduct.getText().toString()) - 1;
                if (amount == 0){
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("Cart");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            Cart cart1 = snap.getValue(Cart.class);
                            if (cart1.getUserClient().equals(cart.getUserClient()) && cart1.getIdProduct() == cart.getIdProduct()) {
                                id = Integer.parseInt(snap.getKey());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                deleteProduct(String.valueOf(id));
            }else holder.tvAmountProduct.setText(String.valueOf(amount));
            });
            holder.imgDelete.setOnClickListener(view -> {
                deleteProduct(String.valueOf(cart.getIdProduct()));
            });


    }

    @Override
    public int getItemCount() {
        if (list!=null){
            return list.size();
        }
        return 0;
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        private TextView tv_ItemCart_nameProduct,tv_ItemCart_priceProduct,tvAmountProduct,tvTotalProduct;
        private ImageView img_ItemCart_imgProduct,imgPlus,imgMinus,imgDelete;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvAmountProduct = itemView.findViewById(R.id.tv_ItemCart_numberProduct);
            tvTotalProduct = itemView.findViewById(R.id.tv_ItemCart_totalPrice);
            tv_ItemCart_nameProduct = itemView.findViewById(R.id.tv_ItemCart_nameProduct);
            tv_ItemCart_priceProduct = itemView.findViewById(R.id.tv_ItemCart_priceProduct);
            img_ItemCart_imgProduct = itemView.findViewById(R.id.img_ItemCart_imgProduct);
            imgPlus = itemView.findViewById(R.id.btn_ItemCart_plus);
            imgMinus = itemView.findViewById(R.id.btn_ItemCart_minus);
            imgDelete = itemView.findViewById(R.id.btn_ItemCart_deleteProduct);
        }
    }
    public void deleteProduct(String id){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Cart");
        reference.child(""+id).removeValue();
    }


}

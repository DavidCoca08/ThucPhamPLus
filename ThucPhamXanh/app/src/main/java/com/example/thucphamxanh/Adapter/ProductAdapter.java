package com.example.thucphamxanh.Adapter;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
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

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.viewHolder> {
    private List<Product> list;
    private List<Cart> listCart;
    public static int id = 0;
    public ProductAdapter(List<Product> list) {
        this.list=list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        return new viewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
            Product product = list.get(position);
            listCart=getAllCart();
            byte[] imgByte = Base64.getDecoder().decode(product.getImgProduct());
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte,0,imgByte.length);
            holder.imgProduct.setImageBitmap(bitmap);
            holder.tvNameProduct.setText(String.valueOf(product.getNameProduct()));
            holder.tvPriceProduct.setText(String.valueOf(product.getPriceProduct()));
            holder.cardProuct.setOnClickListener(view -> {
                if (holder.btnUpdateProduct.getVisibility()==View.VISIBLE || holder.btnDeleteProduct.getVisibility()==View.VISIBLE){
                    holder.btnUpdateProduct.setVisibility(View.GONE);
                    holder.btnDeleteProduct.setVisibility(View.GONE);
                }else {
                    holder.btnUpdateProduct.setVisibility(View.GONE);
                    holder.btnDeleteProduct.setVisibility(View.GONE);
                }
            });
            holder.btnUpdateProduct.setOnClickListener(view -> {

            });
            holder.btnDeleteProduct.setOnClickListener(view -> {

            });
            holder.btn_addCart.setOnClickListener(view -> {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                Cart cart = new Cart();
                cart.setUserClient(firebaseUser.getUid());
                cart.setIdProduct(product.getCodeProduct());
                cart.setImgProduct(product.getImgProduct());
                cart.setNameProduct(product.getNameProduct());
                cart.setPriceProduct(product.getPriceProduct());
                cart.setNumberProduct(1);
                cart.setTotalPrice(cart.getPriceProduct()*cart.getNumberProduct());
                addProductCart(cart);
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

        private TextView tvNameProduct,tvPriceProduct;
        private ImageView imgProduct;
        private CardView cardProuct;
        private Button btnUpdateProduct,btnDeleteProduct;
        private ImageButton btn_addCart;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameProduct = itemView.findViewById(R.id.tvNameProduct_item);
            tvPriceProduct = itemView.findViewById(R.id.tvPriceProduct_item);
            imgProduct = itemView.findViewById(R.id.imgProduct_item);
            cardProuct = itemView.findViewById(R.id.cardProduct);
            btnUpdateProduct = itemView.findViewById(R.id.btn_updateProduct_item);
            btnDeleteProduct = itemView.findViewById(R.id.btn_deleteProduct_item);
            btn_addCart = itemView.findViewById(R.id.btn_addCart_item);
        }
    }
    public  List<Product> getAllProduct(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Product");
        List<Product> list1 = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                    Product product = snap.getValue(Product.class);
                    list1.add(product);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list1;
    }

    public void addProductCart(Cart cart){
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
        int id1 = id+1;
        if (listCart.size()==0){
            cart.setIdProduct(cart.getIdProduct());
            cart.setUserClient(cart.getUserClient());
            cart.setNameProduct(cart.getNameProduct());
            cart.setPriceProduct(cart.getPriceProduct());
            cart.setImgProduct(cart.getImgProduct());
            cart.setNumberProduct(cart.getNumberProduct());
            cart.setTotalPrice(cart.getTotalPrice());
            reference.child("1").setValue(cart);

        }else {
            cart.setIdProduct(cart.getIdProduct());
            cart.setUserClient(cart.getUserClient());
            cart.setNameProduct(cart.getNameProduct());
            cart.setPriceProduct(cart.getPriceProduct());
            cart.setImgProduct(cart.getImgProduct());
            cart.setNumberProduct(cart.getNumberProduct());
            cart.setTotalPrice(cart.getTotalPrice());
            reference.child(""+id1).setValue(cart);
        }
    }
    public  List<Cart> getAllCart(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Cart");
        List<Cart> list1 = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                    Cart cart = snap.getValue(Cart.class);
                    list1.add(cart);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list1;
    }

}

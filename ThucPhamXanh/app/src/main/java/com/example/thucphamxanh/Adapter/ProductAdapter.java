package com.example.thucphamxanh.Adapter;

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

import com.example.thucphamxanh.Model.Product;
import com.example.thucphamxanh.R;

import java.util.Base64;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.viewHolder> {
    private List<Product> list;

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

    }

    @Override
    public int getItemCount() {
        if (list!=null){
            return list.size();
        }
        return 0;
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        private ImageButton btnAddCart;
        private TextView tvNameProduct,tvPriceProduct;
        private ImageView imgProduct;
        private CardView cardProuct;
        private Button btnUpdateProduct,btnDeleteProduct;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            btnAddCart = itemView.findViewById(R.id.btn_addCart_item);
            tvNameProduct = itemView.findViewById(R.id.tvNameProduct_item);
            tvPriceProduct = itemView.findViewById(R.id.tvPriceProduct_item);
            imgProduct = itemView.findViewById(R.id.imgProduct_item);
            cardProuct = itemView.findViewById(R.id.cardProduct);
            btnUpdateProduct = itemView.findViewById(R.id.btn_updateProduct_item);
            btnDeleteProduct = itemView.findViewById(R.id.btn_deleteProduct_item);
        }
    }

}

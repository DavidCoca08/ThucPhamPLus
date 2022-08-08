package com.example.thucphamxanh.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thucphamxanh.Model.ProductTop;
import com.example.thucphamxanh.R;
import com.google.firebase.database.core.Context;

import java.util.List;

public class ChildFavoriteAdapter extends RecyclerView.Adapter<ChildFavoriteAdapter.ChildFavoriteHolder> {

    private List<ProductTop> topList;


    public ChildFavoriteAdapter(List<ProductTop> topList) {
        this.topList = topList;
    }

    @NonNull
    @Override
    public ChildFavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_favorite_list,parent,false);
        return new ChildFavoriteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildFavoriteHolder holder, int position) {
        ProductTop productTop = topList.get(position);

        holder.name_top_product.setText(productTop.getIdProduct());
        holder.amount_top_product.setText(productTop.getAmountProduct());


    }

    @Override
    public int getItemCount() {

        if (topList!=null){
            return topList.size();
        }
        return 0;
    }

    public class ChildFavoriteHolder extends RecyclerView.ViewHolder{

        private TextView name_top_product, amount_top_product;
        private ImageView img_top_product;
        public ChildFavoriteHolder(@NonNull View itemView) {
            super(itemView);
//            img_top_product = itemView.findViewById(R.id.img_top_product);
            name_top_product = itemView.findViewById(R.id.name_top_product);
            amount_top_product = itemView.findViewById(R.id.amount_top_product);

        }
    }
}

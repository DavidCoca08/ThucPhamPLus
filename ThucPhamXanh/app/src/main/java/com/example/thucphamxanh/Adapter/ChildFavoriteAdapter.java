package com.example.thucphamxanh.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thucphamxanh.R;

import java.util.List;

public class ChildFavoriteAdapter extends RecyclerView.Adapter<ChildFavoriteAdapter.ChildFavoriteHolder> {

    private List<String> listChild;

    public ChildFavoriteAdapter(List<String> listChild) {
        this.listChild = listChild;
    }

    @NonNull
    @Override
    public ChildFavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_favorite_list,parent,false);
        return new ChildFavoriteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildFavoriteHolder holder, int position) {
        holder.show_favorite_item_tv.setText(listChild.get(position));
    }

    @Override
    public int getItemCount() {
        return listChild.size();
    }

    public class ChildFavoriteHolder extends RecyclerView.ViewHolder{

        private TextView show_favorite_item_tv;
        public ChildFavoriteHolder(@NonNull View itemView) {
            super(itemView);
            show_favorite_item_tv = itemView.findViewById(R.id.show_favorite_item_tv);
        }
    }
}

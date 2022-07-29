package com.example.thucphamxanh.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thucphamxanh.Model.Partner;
import com.example.thucphamxanh.R;

import java.util.List;

public class Partner_FoodAdapter extends RecyclerView.Adapter<Partner_FoodAdapter.ViewHolder> {
    List<Partner> list;
    public Partner_FoodAdapter(List<Partner> list){

        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_partner_food,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Partner partner = list.get(position);
        holder.namePartner_food.setText(partner.getNamePartner());
        holder.addressPartner_food.setText(partner.getAddressPartner());
        holder.cardView_partner_food.setOnClickListener(view -> {


        });


    }

    @Override
    public int getItemCount() {
        if (list!=null){
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView_partner_food;
        TextView namePartner_food, addressPartner_food;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView_partner_food = itemView.findViewById(R.id.cardView_partner_food);
            namePartner_food = itemView.findViewById(R.id.namePartner_food);
            addressPartner_food = itemView.findViewById(R.id.addressPartner_food);


        }
    }

}

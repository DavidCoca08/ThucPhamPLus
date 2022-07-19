package com.example.thucphamxanh.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thucphamxanh.Model.Partner;
import com.example.thucphamxanh.R;

import java.util.List;

public class PartnerAdapter extends RecyclerView.Adapter<PartnerAdapter.viewHolder> {
    List<Partner> list;
    public PartnerAdapter(List<Partner> list){
        this.list=list;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_partner,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Partner partner = list.get(position);
        holder.tvCodePartner_adapter.setText("Mã ĐT: "+partner.getCodePartner());
        holder.tvNamePartner_adapter.setText("Tên ĐT: "+partner.getNamePartner());
        holder.tvAddressPartner_adapter.setText("Địa chỉ: "+partner.getAddressPartner());
        holder.tvUserPartner_adapter.setText("Tài khoản: "+partner.getUserPartner());
        holder.tvPasswordPartnre_adapter.setText("Mật khẩu: "+partner.getPasswordPartner());
        holder.itemPartner.setOnClickListener(view -> {
            if (holder.btn_UpdatePartner.getVisibility()==View.VISIBLE || holder.btn_DeletePartner.getVisibility()==View.VISIBLE){
                holder.btn_DeletePartner.setVisibility(View.GONE);
                holder.btn_UpdatePartner.setVisibility(View.GONE);
            }else {
                holder.btn_DeletePartner.setVisibility(View.VISIBLE);
                holder.btn_UpdatePartner.setVisibility(View.VISIBLE);
            }
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
        private TextView tvCodePartner_adapter,tvNamePartner_adapter,tvAddressPartner_adapter,
                tvNumberPhonePartner_adapter,tvUserPartner_adapter,tvPasswordPartnre_adapter;
        private Button btn_UpdatePartner,btn_DeletePartner;
        private CardView itemPartner;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvCodePartner_adapter = itemView.findViewById(R.id.tv_CodePartner_item);
            tvNamePartner_adapter = itemView.findViewById(R.id.tv_NamePartner_item);
            tvAddressPartner_adapter = itemView.findViewById(R.id.tv_AddressPartner_item);
            tvUserPartner_adapter = itemView.findViewById(R.id.tv_UserPartner_item);
            tvPasswordPartnre_adapter = itemView.findViewById(R.id.tv_PasswordPartner_item);
            btn_DeletePartner = itemView.findViewById(R.id.btn_DeletePartner);
            btn_UpdatePartner = itemView.findViewById(R.id.btn_UpdatePartner);
            itemPartner = itemView.findViewById(R.id.itemPartner);

        }

    }
}

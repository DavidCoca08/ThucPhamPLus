package com.example.thucphamxanh.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thucphamxanh.Model.Voucher;
import com.example.thucphamxanh.R;

import java.util.Base64;
import java.util.List;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.ViewHolder> {
    private List<Voucher> list;
    private Context context;

    public VoucherAdapter(List<Voucher> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voucher,parent,false);

        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Voucher voucher = list.get(position);
        byte[] imgByte = Base64.getDecoder().decode(voucher.getImgVoucher());
        Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte,0,imgByte.length);
        holder.item_imgVoucher.setImageBitmap(bitmap);
        holder.item_codeVoucher.setText(voucher.getCodeVoucher());

    }

    @Override
    public int getItemCount() {
        if (list != null){
            return  list.size();
        }

        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView item_codeVoucher;
        private ImageView item_imgVoucher;
        private CardView item_cardView_voucher;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item_codeVoucher = itemView.findViewById(R.id.item_codeVoucher);
            item_imgVoucher = itemView.findViewById(R.id.item_imgVoucher);
            item_cardView_voucher = itemView.findViewById(R.id.item_cardView_voucher);

        }
    }
}

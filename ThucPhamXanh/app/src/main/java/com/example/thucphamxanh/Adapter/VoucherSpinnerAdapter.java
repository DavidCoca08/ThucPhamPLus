package com.example.thucphamxanh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.thucphamxanh.Model.Voucher;
import com.example.thucphamxanh.R;

import java.util.ArrayList;
import java.util.List;

public class VoucherSpinnerAdapter extends ArrayAdapter<Voucher> {
    Context context;
    ArrayList<Voucher> arrayList;
    TextView tvCodeVoucher;
//    LayoutInflater layoutInflater;

    public VoucherSpinnerAdapter(@NonNull Context context, ArrayList<Voucher> arrayList) {
        super(context, 0, arrayList);
        this.context = context;
        this.arrayList = arrayList;

//        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v= inflater.inflate(R.layout.spinner_voucher,null);
        }
        final Voucher item = arrayList.get(position);
        if (item != null){
             tvCodeVoucher= v.findViewById(R.id.tvCodeVoucher);
            tvCodeVoucher.setText(item.getCodeVoucher());

        }

        return v;

//        View rowView = layoutInflater.inflate(R.layout.spinner_voucher,null,true);
//        Voucher voucher = getItem(position);
//        TextView textView = rowView.findViewById(R.id.tvCodeVoucher);
//        textView.setText(voucher.getCodeVoucher());
//
//
//        return rowView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v= inflater.inflate(R.layout.spinner_voucher,null);
        }
        final Voucher item = arrayList.get(position);
        if (item != null){
            tvCodeVoucher= v.findViewById(R.id.tvCodeVoucher);
            tvCodeVoucher.setText(item.getCodeVoucher());

        }

        return v;

//        if (convertView == null){
//            convertView= layoutInflater.inflate(R.layout.spinner_voucher,parent,false);
//        }
//
//        Voucher voucher = getItem(position);
//        TextView textView = convertView.findViewById(R.id.tvCodeVoucher);
//        textView.setText(voucher.getCodeVoucher());
//
//
//        return convertView;

    }
}

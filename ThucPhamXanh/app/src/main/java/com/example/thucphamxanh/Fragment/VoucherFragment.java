package com.example.thucphamxanh.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thucphamxanh.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class VoucherFragment extends Fragment {

    FloatingActionButton fab_voucher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_voucher, container, false);

        fab_voucher = view.findViewById(R.id.fab_voucher);

        fab_voucher.setOnClickListener(view1 -> {
            openDialog(getActivity());
        });
        return view;
    }

    private void openDialog(final Context context){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_voucher);

        dialog.show();
    }
}
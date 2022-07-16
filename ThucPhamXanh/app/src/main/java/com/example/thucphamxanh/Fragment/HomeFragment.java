package com.example.thucphamxanh.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.thucphamxanh.DAO.DAOTheLoai;
import com.example.thucphamxanh.Model.TheLoai;
import com.example.thucphamxanh.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    EditText maLoai,tenLoai;
    Button btnAdd,btnUpdate,btnDelete;
    DAOTheLoai daoTheLoai;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        maLoai= binding.maloai;
        tenLoai = binding.tenloai;
        btnAdd=binding.btnAdd;
        btnUpdate=binding.btnUpdate;
        btnDelete=binding.btnDelete;
        daoTheLoai = new DAOTheLoai();
        btnAdd.setOnClickListener(view -> {
            TheLoai tl = new TheLoai();
            tl.setTenLoai(tenLoai.getText().toString());
            daoTheLoai.addTheLoai(tl);
        });
        btnUpdate.setOnClickListener(view -> {

        });
        btnDelete.setOnClickListener(view -> {

        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
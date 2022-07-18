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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    EditText maLoai,tenLoai;
    Button btnAdd,btnUpdate,btnDelete;
    DAOTheLoai daoTheLoai;
    List<TheLoai> list;
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
        list=new ArrayList<>();
        daoTheLoai = new DAOTheLoai(list);
        btnAdd.setOnClickListener(view -> {
            TheLoai tl = new TheLoai();
            tl.setTenLoai(tenLoai.getText().toString());
            daoTheLoai.addTheLoai(tl);
        });
        btnUpdate.setOnClickListener(view -> {
            daoTheLoai.getAll();
        });
        btnDelete.setOnClickListener(view -> {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference();
            reference.child("TheLoai").removeValue();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
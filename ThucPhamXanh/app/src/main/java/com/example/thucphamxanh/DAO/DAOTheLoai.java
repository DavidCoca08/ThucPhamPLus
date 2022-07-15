package com.example.thucphamxanh.DAO;

import androidx.annotation.NonNull;

import com.example.thucphamxanh.Model.TheLoai;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DAOTheLoai {
    FirebaseDatabase database;
    DatabaseReference reference;

    public DAOTheLoai() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }
    public void addTheLoai(TheLoai tl){
        List<TheLoai> list = new ArrayList<>();
//        lấy dữ liệu từ trên firebase về list. để lấy mã id cuối cùng làm id tiếp theo cho thằng thể loại tiếp theo.
        reference.child("TheLoai").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    TheLoai theLoai = (TheLoai) snapshot1.getValue();
                    list.add(theLoai);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // nếu list chưa có gì thì id sản phẩm đầu tiên =1
        if (list.size()==0){
            tl.setMaLoai(1);
            tl.setTenLoai(tl.getTenLoai());
            reference.child("1").setValue(tl);
        }else { // nếu list đã có thì lấy cái thằng id của sản phẩm cuối cùng +1 làm mã id của thằng kế tiếp.(tự sinh ID như SQL)
            int i = list.size()-1;
            int id = list.get(i).getMaLoai()+1;
            tl.setMaLoai(id);
            tl.setTenLoai(tl.getTenLoai());
            reference.child(""+id).setValue(tl);
        }
    }
    public void editTheLoai(TheLoai tl){
        reference.child("TheLoai/"+tl.getMaLoai()).updateChildren((Map<String, Object>) tl);
    }
}

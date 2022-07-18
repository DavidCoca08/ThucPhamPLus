package com.example.thucphamxanh.DAO;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.thucphamxanh.Fragment.HomeFragment;
import com.example.thucphamxanh.Model.TheLoai;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class DAOTheLoai {
    FirebaseDatabase database;
    DatabaseReference reference;
    List<TheLoai> list;
    public DAOTheLoai(List<TheLoai> list) {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("TheLoai");
        this.list = list;

    }
    @Deprecated
    public void addTheLoai(TheLoai tl){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                    for (DataSnapshot snap: snapshot.getChildren()){
                        TheLoai theLoai = snap.getValue(TheLoai.class);
                        list.add(theLoai);
                    }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if (list.size()==0){
            tl.setMaLoai(1);
            tl.setTenLoai(tl.getTenLoai());
            reference.child("1").setValue(tl);
        }else if (list.size()!=0){ // nếu list đã có thì lấy cái thằng id của sản phẩm cuối cùng +1 làm mã id của thằng kế tiếp.(tự sinh ID như SQL)
            int i = list.size()-1;
            int id = list.get(i).getMaLoai() + 1;
            tl.setMaLoai(id);
            tl.setTenLoai(tl.getTenLoai());
            reference.child(""+id).setValue(tl);
            Log.d("zzzzzzzzzzzz", String.valueOf(list.size()));
        }

    }
    public void deleteTheLoai(TheLoai tl){
        reference.child(""+tl.getMaLoai()).removeValue();
//        reference.child("theLoai").child(String.valueOf(tl.getMaLoai())).removeValue();
    }
    public void updateTheLoai(TheLoai tl,String tenLoai){
        tl.setTenLoai(tenLoai);
        reference.child(""+tl.getMaLoai()).updateChildren(tl.toMap());
    }
    public List<TheLoai> getAll(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                    for (DataSnapshot snapshot1: snapshot.getChildren()) {
                        TheLoai theLoai = snapshot1.getValue(TheLoai.class);
                        list.add(theLoai);
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Log.d("cccc", String.valueOf(list.size()));
        return list;
    }
}

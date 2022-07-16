package com.example.thucphamxanh.DAO;

import android.util.Log;
import android.widget.EditText;

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
    List<TheLoai> list;
    public DAOTheLoai() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        list = new ArrayList<>();
    }
    public void addTheLoai(TheLoai tl){
        reference.child("TheLoai").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snap: snapshot.getChildren()){
                        TheLoai theLoai = snap.getValue(TheLoai.class);
                        list.add(theLoai);
                        Log.d("zzzzz",list.get(0).getTenLoai());
                        Log.d("zzzzz", String.valueOf(list.get(0).getMaLoai()));
                        Log.d("zzzzzzzzzzzz", String.valueOf(list.size()));
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        Log.d("aaaaaaaaaa", String.valueOf(getAll().size()));

        if (list.size()==0){
            tl.setMaLoai(1);
            tl.setTenLoai(tl.getTenLoai());
            reference.child("TheLoai/1").setValue(tl);
        }else if (list.size()!=0){ // nếu list đã có thì lấy cái thằng id của sản phẩm cuối cùng +1 làm mã id của thằng kế tiếp.(tự sinh ID như SQL)
            int i = list.size()-1;
            int id = list.get(i).getMaLoai() + 1;
            tl.setMaLoai(id);
            tl.setTenLoai(tl.getTenLoai());
            reference.child("TheLoai").child(""+id).setValue(tl);
            Log.d("zzzzzzzzzzzz", String.valueOf(id));
        }
    }
    public void deleteTheLoai(TheLoai tl){
        reference.child("TheLoai/"+tl.getMaLoai()).removeValue();
//        reference.child("theLoai").child(String.valueOf(tl.getMaLoai())).removeValue();
    }
    public void updateTheLoai(TheLoai tl,String tenLoai){
        tl.setTenLoai(tenLoai);
        reference.child("TheLoai/"+tl.getMaLoai()).updateChildren(tl.toMap());
    }
    public List<TheLoai> getAll(){
        List<TheLoai> list = new ArrayList<>();
        reference.child("TheLoai").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    for (DataSnapshot snapshot1: snapshot.getChildren()){
                        TheLoai theLoai = snapshot1.getValue(TheLoai.class);
                        list.add(theLoai);
                    }
                }catch (Exception e){
                    Log.d("zzzzzzz","list dữ liệu rỗng k thể đọc");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list;
    }
}

package com.example.thucphamxanh.DAO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.thucphamxanh.Model.Category;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class CategoryDAO {
    FirebaseDatabase database;
    DatabaseReference reference;
    List<Category> list;
    public CategoryDAO(List<Category> list) {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("TheLoai");
        this.list = list;

    }
    @Deprecated
    public void addTheLoai(Category tl){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                    for (DataSnapshot snap: snapshot.getChildren()){
                        Category theLoai = snap.getValue(Category.class);
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
    public void deleteTheLoai(Category tl){
        reference.child(""+tl.getMaLoai()).removeValue();
//        reference.child("theLoai").child(String.valueOf(tl.getMaLoai())).removeValue();
    }
    public void updateTheLoai(Category tl){
        reference.child(""+tl.getMaLoai()).updateChildren(tl.toMap());
    }
    public List<Category> getAll(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                    for (DataSnapshot snapshot1: snapshot.getChildren()) {
                        Category theLoai = snapshot1.getValue(Category.class);
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

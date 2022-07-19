package com.example.thucphamxanh.DAO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.thucphamxanh.Adapter.PartnerAdapter;
import com.example.thucphamxanh.Model.Partner;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class PartnerDAO {
    FirebaseDatabase database;
    DatabaseReference reference;
    List<Partner> list;
    PartnerAdapter adapter;
    public PartnerDAO(List<Partner> list,PartnerAdapter adapter){
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Partner");
        this.list=list;
        this.adapter=adapter;
    }
    public void getAllPartner(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snap : snapshot.getChildren()){
                    Partner partner = snap.getValue(Partner.class);
                    list.add(partner);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addPartner(Partner partner){
        if (list.size()==0){
            partner.setCodePartner(1);
            partner.setNamePartner(partner.getNamePartner());
            partner.setAddressPartner(partner.getAddressPartner());
            partner.setUserPartner(partner.getUserPartner());
            partner.setPasswordPartner(partner.getPasswordPartner());
            reference.child("1").setValue(partner);
        }else if (list.size()!=0){ // nếu list đã có thì lấy cái thằng id của sản phẩm cuối cùng +1 làm mã id của thằng kế tiếp.(tự sinh ID như SQL)
            int i = list.size()-1;
            int id = list.get(i).getCodePartner() + 1;
            partner.setCodePartner(id);
            partner.setNamePartner(partner.getNamePartner());
            partner.setAddressPartner(partner.getAddressPartner());
            partner.setUserPartner(partner.getUserPartner());
            partner.setPasswordPartner(partner.getPasswordPartner());
            reference.child(""+id).setValue(partner);
        }
    }
    public void updatePartner(Partner partner){
        reference.child(""+partner.getCodePartner()).updateChildren(partner.toMap());
    }
    public void deletePartner(Partner partner){
        reference.child(""+partner.getCodePartner()).removeValue();
    }
}

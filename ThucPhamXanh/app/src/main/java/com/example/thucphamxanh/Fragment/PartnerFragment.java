package com.example.thucphamxanh.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.thucphamxanh.Adapter.PartnerAdapter;
import com.example.thucphamxanh.Model.Partner;
import com.example.thucphamxanh.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class PartnerFragment extends Fragment {
    private RecyclerView rvPartner;
    private LinearLayoutManager linearLayoutManager;
    private List<Partner> list;
    private PartnerAdapter adapter;
    private TextInputLayout til_namePartner,til_addressPartner,til_UserPartner,til_PasswordPartner,til_rePasswordPartner;
    private Button btnAddPartner,btnCancelPartner;
    private String namePartner,addressPartner,userPartner,passwordPartner,rePasswordPartner;
    private FloatingActionButton btn_addPartner;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Partner");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_partner, container, false);
        rvPartner = view.findViewById(R.id.rvDoiTac_fragment);
        list =  getAllPartner();
        linearLayoutManager=new LinearLayoutManager(getContext());
        rvPartner.setLayoutManager(linearLayoutManager);
        adapter = new PartnerAdapter(list);
        rvPartner.setAdapter(adapter);
        btn_addPartner = view.findViewById(R.id.btn_AddPartner_fragment);
        btn_addPartner.setOnClickListener(view1 -> {
            addPartner();
        });
        return view;

    }
    public void addPartner(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Thêm đối tác");
        View view1 = getLayoutInflater().inflate(R.layout.dialog_partner,null);
        builder.setView(view1);
        AlertDialog dialog = builder.create();
        dialog.show();
        unitUi(view1);
        btnAddPartner.setOnClickListener(view -> {
            getText();
            validate();

        });
        btnCancelPartner.setOnClickListener(view -> {
            dialog.dismiss();
        });



    }
    public boolean isEmptys(String str,TextInputLayout til){
        if (str.isEmpty()){
            til.setError("Không được để trống");
            return false;
        }else {
            til.setError("");
            return true;
        }
    }
    public boolean checkNumberPhone(){
        if(userPartner.length()!=10){
            til_UserPartner.setError("Số điện thoại phải đủ 10 số.");
            return false;
        }else {
            til_UserPartner.setError("");
            return true;
        }

    }
    public boolean checkPass(){
        if (!passwordPartner.equals(rePasswordPartner)){
            til_PasswordPartner.setError("Pass nhập không khớp");
            til_rePasswordPartner.setError("Pass nhập không khớp");
            return false;
        }else{
            til_PasswordPartner.setError("");
            til_rePasswordPartner.setError("");
            return true;
        }
    }

    public void validate(){
        if(isEmptys(namePartner,til_namePartner) && isEmptys(addressPartner,til_addressPartner)
        && isEmptys(userPartner,til_UserPartner) && isEmptys(passwordPartner,til_PasswordPartner) && checkNumberPhone() && checkPass() ){
            setDataPartner();
            removeAll();
        }
    }

    public void unitUi(View view){

        btnAddPartner = view.findViewById(R.id.btn_addPartner_dialog);
        btnCancelPartner = view.findViewById(R.id.btn_cancelPartner_dialog);
        til_namePartner =view.findViewById(R.id.til_namePartner_dialog);
        til_addressPartner =view.findViewById(R.id.til_addressPartner_dialog);
        til_UserPartner =view.findViewById(R.id.til_userPartner_dialog);
        til_PasswordPartner =view.findViewById(R.id.til_passwordPartner_dialog);
        til_rePasswordPartner =view.findViewById(R.id.til_rePasswordPartner_dialog);
    }
    public void getText(){
        namePartner = til_UserPartner.getEditText().getText().toString();
        addressPartner = til_addressPartner.getEditText().getText().toString();
        userPartner  = til_UserPartner.getEditText().getText().toString();
        passwordPartner = til_PasswordPartner.getEditText().getText().toString();
        rePasswordPartner = til_rePasswordPartner.getEditText().getText().toString();
    }
    public void setDataPartner(){
        Partner partner = new Partner();
        partner.setNamePartner(namePartner);
        partner.setAddressPartner(addressPartner);
        partner.setUserPartner(userPartner);
        partner.setPasswordPartner(passwordPartner);
        addPartnerFirebase(partner);
    }
    public void removeAll(){
        til_UserPartner.getEditText().setText("");
        til_addressPartner.getEditText().setText("");
        til_UserPartner.getEditText().setText("");
        til_PasswordPartner.getEditText().setText("");
        til_PasswordPartner.getEditText().setText("");
        til_rePasswordPartner.getEditText().setText("");
    }
    public List<Partner> getAllPartner(){
        List<Partner> list1 = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1.clear();
                for (DataSnapshot snap : snapshot.getChildren()){
                    Partner partner = snap.getValue(Partner.class);
                    list1.add(partner);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list1;
    }
    public void addPartnerFirebase(Partner partner){
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

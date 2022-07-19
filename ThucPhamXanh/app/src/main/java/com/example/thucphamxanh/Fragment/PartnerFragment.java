package com.example.thucphamxanh.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.thucphamxanh.Adapter.PartnerAdapter;
import com.example.thucphamxanh.DAO.PartnerDAO;
import com.example.thucphamxanh.Model.Partner;
import com.example.thucphamxanh.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;


public class PartnerFragment extends Fragment {
    private RecyclerView rvPartner;
    private LinearLayoutManager linearLayoutManager;
    private List<Partner> list;
    private PartnerAdapter adapter;
    private PartnerDAO dao;
    private TextInputLayout til_namePartner,til_addressPartner,til_UserPartner,til_PasswordPartner,til_rePasswordPartner;
    private TextInputEditText edNamePartner,edAddressPartner,edUserPartner,edPasswordPartner,edRePassword;
    private Button btnAddPartner,btnCancelPartner;
    private String namePartner,addressPartner,numberPhonePartner,userPartner,passwordPartner,rePasswordPartner;
    private FloatingActionButton btn_addPartner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_partner, container, false);
        rvPartner = view.findViewById(R.id.rvDoiTac_fragment);
        list = new ArrayList<>();
        linearLayoutManager=new LinearLayoutManager(getContext());
        rvPartner.setLayoutManager(linearLayoutManager);
        adapter = new PartnerAdapter(list);
        dao = new PartnerDAO(list,adapter);
        rvPartner.setAdapter(adapter);
        dao.getAllPartner();
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
        mapped(view1);
        btnAddPartner.setOnClickListener(view -> {
            getText();
            validate();
            for (int i = 0; i <list.size() ; i++) {
                if(list.get(i).getCodePartner()==1){
                    Log.d("aaaaaaa",list.get(i).getNamePartner());
            }

            }
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
        if(numberPhonePartner.length()!=10){
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
            addPartners();
            setText();
        }
    }

    public void mapped(View view){
        edNamePartner = view.findViewById(R.id.ed_NamePartner_dialog);
        edAddressPartner = view.findViewById(R.id.ed_AddressPartner_dialog);
        edUserPartner = view.findViewById(R.id.ed_UserPartner_dialog);
        edPasswordPartner = view.findViewById(R.id.ed_PasswordPartner_dialog);
        edRePassword = view.findViewById(R.id.ed_RePasswordPartner_dialog);
        btnAddPartner = view.findViewById(R.id.btn_addPartner_dialog);
        btnCancelPartner = view.findViewById(R.id.btn_cancelPartner_dialog);
        til_namePartner =view.findViewById(R.id.til_namePartner_dialog);
        til_addressPartner =view.findViewById(R.id.til_addressPartner_dialog);
        til_UserPartner =view.findViewById(R.id.til_userPartner_dialog);
        til_PasswordPartner =view.findViewById(R.id.til_passwordPartner_dialog);
        til_rePasswordPartner =view.findViewById(R.id.til_rePasswordPartner_dialog);
    }
    public void getText(){
        namePartner = edNamePartner.getText().toString();
        addressPartner = edAddressPartner.getText().toString();
        userPartner  = edUserPartner.getText().toString();
        passwordPartner = edPasswordPartner.getText().toString();
        rePasswordPartner = edRePassword.getText().toString();
    }
    public void addPartners(){
        Partner partner = new Partner();
        partner.setNamePartner(namePartner);
        partner.setAddressPartner(addressPartner);
        partner.setUserPartner(userPartner);
        partner.setPasswordPartner(passwordPartner);
        dao.addPartner(partner);
    }
    public void setText(){
        edNamePartner.setText("");
        edAddressPartner.setText("");
        edUserPartner.setText("");
        edPasswordPartner.setText("");
        edPasswordPartner.setText("");
        edRePassword.setText("");
    }
}

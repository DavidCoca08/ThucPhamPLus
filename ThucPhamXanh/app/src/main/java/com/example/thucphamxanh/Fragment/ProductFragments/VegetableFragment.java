package com.example.thucphamxanh.Fragment.ProductFragments;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.thucphamxanh.Adapter.PartnerAdapter;
import com.example.thucphamxanh.Adapter.ProductAdapter;

import com.example.thucphamxanh.Model.Partner;
import com.example.thucphamxanh.Model.Product;
import com.example.thucphamxanh.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


public class VegetableFragment extends Fragment {
    private FloatingActionButton fab_addVegetable;
    private List<Product> listProduct;
    private List<Product> listVegetable;
    private RecyclerView rvVegetable;
    private LinearLayoutManager linearLayoutManager;
    private ProductAdapter adapter;
    private View view;
    private TextInputLayout til_nameProduct,til_priceProduct,til_amountProduct;
    private ImageView img_Product,img_addImageCamera,img_addImageDevice;
    private String nameProduct,imgProduct,userPartner;
    private int codeProduct,codeCategory,priceProduct,amountProduct;
    private Button btn_addVegetable,btn_cancleVegetable;
    private List<Partner> listPartner;
    private PartnerAdapter adapterPartner;
    private static final int REQUEST_ID_IMAGE_CAPTURE =1;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_vegetable, container, false);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Product");
        listProduct = getAllProduct();
        listVegetable = getVegetableProduct();
        fab_addVegetable = view.findViewById(R.id.btn_addVegetable_fragment);
        rvVegetable = view.findViewById(R.id.rvVegetable);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rvVegetable.setLayoutManager(linearLayoutManager);
        adapter = new ProductAdapter(listVegetable);
        rvVegetable.setAdapter(adapter);
        listPartner = getAllPartner();
        adapterPartner = new PartnerAdapter(listPartner);
         fab_addVegetable.setOnClickListener(view1 -> {
             dialogProduct();
         });
        return view;
    }





    private void dialogProduct() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Thêm sản phẩm");
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_product,null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        initUi(view);
        img_addImageCamera.setOnClickListener(view1 -> {
            captureImage();
        });
        btn_addVegetable.setOnClickListener(view1 -> {
                getTexts();
                setDataProduct();
        });
        btn_cancleVegetable.setOnClickListener(view1 -> {
            alertDialog.dismiss();
        });
    }
    public void initUi(View view){
        img_Product = view.findViewById(R.id.imgProduct_dialog);
        img_addImageCamera = view.findViewById(R.id.img_addImageCamera_dialog);
        img_addImageDevice = view.findViewById(R.id.img_addImageDevice_dialog);
        til_nameProduct =  view.findViewById(R.id.til_NameProduct_dialog);
        til_priceProduct =  view.findViewById(R.id.til_PriceProduct_dialog);
        til_amountProduct =  view.findViewById(R.id.til_AmountProduct_dialog);
        btn_addVegetable =  view.findViewById(R.id.btn_addVegetable_dialog);
        btn_cancleVegetable =  view.findViewById(R.id.btn_cancleVegetable_dialog);

    }

    public void getTexts(){
        nameProduct = til_nameProduct.getEditText().getText().toString();
        Bitmap bitmap = ((BitmapDrawable)img_Product.getDrawable()).getBitmap();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[] imgByte = outputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            imgProduct = Base64.getEncoder().encodeToString(imgByte);
        }
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("My_User", Context.MODE_PRIVATE);

        userPartner = sharedPreferences.getString("username","");
        codeCategory = Integer.parseInt(String.valueOf(1));
        priceProduct = Integer.parseInt(til_priceProduct.getEditText().getText().toString());
        amountProduct = Integer.parseInt(til_amountProduct.getEditText().getText().toString());

    }
    public void setDataProduct(){
        Product product = new Product();
        product.setUserPartner(userPartner);
        product.setCodeCategory(codeCategory);
        product.setNameProduct(nameProduct);
        product.setPriceProduct(priceProduct);
        product.setAmountProduct(amountProduct);
        product.setImgProduct(imgProduct);
        addProduct(product);

    }

    private void captureImage() {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            this.startActivityForResult(intent, REQUEST_ID_IMAGE_CAPTURE);
        }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ID_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                this.img_Product.setImageBitmap(bp);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getContext(), "Action canceled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Action Failed", Toast.LENGTH_LONG).show();
            }
        }
    }
    public List<Product> getVegetableProduct(){
        List<Product> list1 = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                    Product product = snap.getValue(Product.class);
                    if (product.getCodeCategory()==1){
                        list1.add(product);
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list1;
    }
    public List<Product> getAllProduct(){
        List<Product> list1 = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                    Product product = snap.getValue(Product.class);
                        list1.add(product);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list1;
    }

    public void addProduct(Product product){
        if (listProduct.size()==0){
            product.setCodeProduct(1);
            product.setCodeCategory(product.getCodeCategory());
            product.setUserPartner(product.getUserPartner());
            product.setImgProduct(product.getImgProduct());
            product.setNameProduct(product.getNameProduct());
            product.setPriceProduct(product.getPriceProduct());
            product.setAmountProduct(product.getAmountProduct());
            reference.child("1").setValue(product);

        }else {
            int i = listProduct.size()-1;
            int id = listProduct.get(i).getCodeProduct()+1;
            product.setCodeProduct(id);
            product.setCodeCategory(product.getCodeCategory());
            product.setUserPartner(product.getUserPartner());
            product.setImgProduct(product.getImgProduct());
            product.setNameProduct(product.getNameProduct());
            product.setPriceProduct(product.getPriceProduct());
            product.setAmountProduct(product.getAmountProduct());
            reference.child(""+id).setValue(product);
        }
    }
    public void updateProduct(Product product){
        reference.child(""+product.getCodeProduct()).updateChildren(product.toMap());
    }
    public void deleteProduct(Product product){
        reference.child(""+product.getCodeProduct()).removeValue();
    }
    public List<Partner> getAllPartner(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Partner");
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

}
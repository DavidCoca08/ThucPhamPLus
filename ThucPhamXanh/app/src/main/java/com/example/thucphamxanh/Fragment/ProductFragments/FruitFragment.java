package com.example.thucphamxanh.Fragment.ProductFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.thucphamxanh.Adapter.PartnerAdapter;
import com.example.thucphamxanh.Adapter.ProductAdapter;
import com.example.thucphamxanh.Model.Partner;
import com.example.thucphamxanh.Model.Product;
import com.example.thucphamxanh.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class FruitFragment extends Fragment {
    private FloatingActionButton fab_addFruit;
    private List<Product> listProduct;
    private List<Product> listFruit;
    private RecyclerView rvFruit;
    private LinearLayoutManager linearLayoutManager;
    private ProductAdapter adapter;
    private View view;
    private TextInputLayout til_nameProduct,til_priceProduct,til_amountProduct;
    private ImageView img_Product,img_addImageCamera,img_addImageDevice;
    private String nameProduct,imgProduct,userPartner;
    private int codeCategory,priceProduct,amountProduct;
    private Button btn_addFruit,btn_cancleFruit;
    private List<Partner> listPartner;
    private PartnerAdapter adapterPartner;
    private static final int REQUEST_ID_IMAGE_CAPTURE =1;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fruit, container, false);
        return view;
    }
}
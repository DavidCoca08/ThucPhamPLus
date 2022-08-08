package com.example.thucphamxanh.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.thucphamxanh.Adapter.ChildFavoriteAdapter;
import com.example.thucphamxanh.Adapter.FavoriteItemAdapter;
import com.example.thucphamxanh.Fragment.ProductFragments.FoodFragment;
import com.example.thucphamxanh.Fragment.ProductFragments.FruitFragment;
import com.example.thucphamxanh.Fragment.ProductFragments.MeatFragment;
import com.example.thucphamxanh.Fragment.ProductFragments.VegetableFragment;
import com.example.thucphamxanh.Model.Bill;
import com.example.thucphamxanh.Model.Cart;
import com.example.thucphamxanh.Model.FavoviteModel;
import com.example.thucphamxanh.Model.Product;
import com.example.thucphamxanh.Model.ProductTop;
import com.example.thucphamxanh.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class HomePageFragment extends Fragment {

//    private RecyclerView favorite_recyclerView_home;
//    private List<FavoviteModel> favoviteModelList;
//    private FavoriteItemAdapter favoriteItemAdapter;

    private List<ProductTop> productTopListVegetable = new ArrayList<>();
    private List<ProductTop> productTopListMeat= new ArrayList<>();
    private List<ProductTop> productTopListFruit= new ArrayList<>();
    private List<ProductTop> productTopListFood= new ArrayList<>();

    ChildFavoriteAdapter childFavoriteAdapter;

// cái này là để lr     Mỗi cái item sản phẩm top ấy
//    List<Cart> list = new ArrayList<>();
    CardView card_vegetable_home,card_fruit_home,card_meat_home,card_food_home;

    CardView card_Top_Vegetable,card_Top_Fruit,card_Top_Meat,card_Top_Food;

    RecyclerView rv_VegetableTop_Home,rv_FruitTop_Home,rv_MeatTop_Home,rv_FoodTop_Home;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        getTopProduct();


        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        card_vegetable_home = view.findViewById(R.id.card_vegetable_home);
        card_fruit_home = view.findViewById(R.id.card_fruit_home);
        card_meat_home = view.findViewById(R.id.card_meat_home);
        card_food_home = view.findViewById(R.id.card_food_home);

        card_Top_Vegetable = view.findViewById(R.id.card_Top_Vegetable);
        card_Top_Fruit = view.findViewById(R.id.card_Top_Fruit);
        card_Top_Meat = view.findViewById(R.id.card_Top_Meat);
        card_Top_Food = view.findViewById(R.id.card_Top_Food);

        rv_VegetableTop_Home = view.findViewById(R.id.rv_VegetableTop_Home);
        rv_FruitTop_Home = view.findViewById(R.id.rv_FruitTop_Home);
        rv_MeatTop_Home = view.findViewById(R.id.rv_MeatTop_Home);
        rv_FoodTop_Home = view.findViewById(R.id.rv_FoodTop_Home);


//        favorite_recyclerView_home = view.findViewById(R.id.favorite_recyclerView_home);
//        favorite_recyclerView_home.setLayoutManager(new LinearLayoutManager(getContext()));

//        favoviteModelList = new ArrayList<>();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            Collections.sort(favoviteModelList, Comparator.comparing(FavoviteModel::getItemText));
//        }

//        productTopListVegetable = new ArrayList<>();//đây ô chưa lấy list ra này mới khởi tạo, đã lấy dữ liêu ra đâu
        Collections.sort(productTopListVegetable,Comparator.comparing(ProductTop::getAmountProduct));

//        productTopListFruit = new ArrayList<>();
        Collections.sort(productTopListFruit,Comparator.comparing(ProductTop::getAmountProduct));

//        productTopListMeat = new ArrayList<>();
        Collections.sort(productTopListMeat,Comparator.comparing(ProductTop::getAmountProduct));

//        productTopListFood = new ArrayList<>();
        Collections.sort(productTopListFood,Comparator.comparing(ProductTop::getAmountProduct));





//        favoriteItemAdapter = new FavoriteItemAdapter(favoviteModelList);
//        favorite_recyclerView_home.setAdapter(favoriteItemAdapter);
// ô chuyển đâu rồi
        card_vegetable_home.setOnClickListener(view1 -> {

            fragmentManager.beginTransaction().replace(R.id.frame_Home, new VegetableFragment(),null).addToBackStack(null).commit();

        });
        card_fruit_home.setOnClickListener(view1 -> {
            fragmentManager.beginTransaction().replace(R.id.frame_Home, new FruitFragment(),null).addToBackStack(null).commit();
        });
        card_meat_home.setOnClickListener(view1 -> {

            fragmentManager.beginTransaction().replace(R.id.frame_Home, new MeatFragment(),null).addToBackStack(null).commit();
        });
        card_food_home.setOnClickListener(view1 -> {

            fragmentManager.beginTransaction().replace(R.id.frame_Home, new PartnerFoodFragment(),null).addToBackStack(null).commit();
        });


        card_Top_Vegetable.setOnClickListener(view1 -> {

            if (rv_VegetableTop_Home.getVisibility() == View.GONE){
                rv_VegetableTop_Home.setVisibility(View.VISIBLE);
            }
            else {
                rv_VegetableTop_Home.setVisibility(View.GONE);
            }

            rv_VegetableTop_Home.setLayoutManager(new LinearLayoutManager(getContext()));
            childFavoriteAdapter = new ChildFavoriteAdapter(productTopListVegetable);
            rv_VegetableTop_Home.setAdapter(childFavoriteAdapter);
        });

        card_Top_Fruit.setOnClickListener(view1 -> {

        });



        return view;
    }

    public void getTopProduct(){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("ProductTop");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                   productTopListVegetable.clear();
                    productTopListFood.clear();
                    productTopListFruit.clear();
                    productTopListMeat.clear();

                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        ProductTop top = snapshot1.getValue(ProductTop.class);
                        if (top.getIdCategory()==1){
                            productTopListVegetable.add(top);
                        }else  if (top.getIdCategory()==2){
                            productTopListFruit.add(top);
                        }else  if (top.getIdCategory()==3){
                            productTopListMeat.add(top);
                        }else {productTopListFood.add(top);
                        }
                    }

                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }









}
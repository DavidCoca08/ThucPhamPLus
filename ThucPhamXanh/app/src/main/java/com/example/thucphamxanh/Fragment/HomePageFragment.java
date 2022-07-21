package com.example.thucphamxanh.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thucphamxanh.Adapter.FavoriteItemAdapter;
import com.example.thucphamxanh.Model.FavoviteModel;
import com.example.thucphamxanh.R;

import java.util.ArrayList;
import java.util.List;


public class HomePageFragment extends Fragment {

    private RecyclerView favorite_recyclerView_home;
    private List<FavoviteModel> favoviteModelList;
    private FavoriteItemAdapter favoriteItemAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        favorite_recyclerView_home = view.findViewById(R.id.favorite_recyclerView_home);
        favorite_recyclerView_home.setLayoutManager(new LinearLayoutManager(getContext()));

        favoviteModelList = new ArrayList<>();

        List<String> favoriteVegetable = new ArrayList<>();
        favoriteVegetable.add("Hành tây");
        favoriteVegetable.add("Cải bắp");
        favoriteVegetable.add("Su hào");


        List<String> favoriteFruit = new ArrayList<>();
        favoriteFruit.add("Táo");
        favoriteFruit.add("Na");
        favoriteFruit.add("Dưa hấu");


        List<String> favoriteMeat = new ArrayList<>();
        favoriteMeat.add("Thịt gà");
        favoriteMeat.add("Thịt lợn");
        favoriteMeat.add("Thịt bê");


        List<String> favoriteFood = new ArrayList<>();
        favoriteFood.add("Pizza");
        favoriteFood.add("Phở");
        favoriteFood.add("Cơm rang");

        favoviteModelList.add(new FavoviteModel(favoriteVegetable, "Các loại rau, củ được yêu thích"));
        favoviteModelList.add(new FavoviteModel(favoriteFruit, "Các loại quả được yêu thích"));
        favoviteModelList.add(new FavoviteModel(favoriteMeat, "Các loại thịt được yêu thích"));
        favoviteModelList.add(new FavoviteModel(favoriteFood, "Các loại đồ ăn được yêu thích"));

        favoriteItemAdapter = new FavoriteItemAdapter(favoviteModelList);
        favorite_recyclerView_home.setAdapter(favoriteItemAdapter);


        return view;
    }
}
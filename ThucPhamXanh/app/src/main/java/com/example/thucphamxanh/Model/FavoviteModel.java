package com.example.thucphamxanh.Model;

import java.util.List;

public class FavoviteModel {

    private List<String> favoriteList;
    private String itemText;
    private boolean isHidden;

    public FavoviteModel(List<String> favoriteList, String itemText) {
        this.favoriteList = favoriteList;
        this.itemText = itemText;
        isHidden = false;
    }

    public List<String> getFavoriteList() {
        return favoriteList;
    }

    public String getItemText() {
        return itemText;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }
}

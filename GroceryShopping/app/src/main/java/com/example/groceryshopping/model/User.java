package com.example.groceryshopping.model;

import android.graphics.Bitmap;

public class User {
    private Bitmap bitmapAvatar;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String name;
    private String email;

    public Bitmap getBitmapAvatar() {
        return bitmapAvatar;
    }

    public void setBitmapAvatar(Bitmap bitmapAvatar) {
        this.bitmapAvatar = bitmapAvatar;
    }
}

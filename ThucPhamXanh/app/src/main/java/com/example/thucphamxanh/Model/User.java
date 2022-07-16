package com.example.thucphamxanh.Model;

import android.graphics.Bitmap;

public class User {
    private Bitmap bitmapAvatar;
    private String uriAvatar;
    private String name;
    private String email;
    public String getUriAvatar() {
        return uriAvatar;
    }

    public void setUriAvatar(String uriAvatar) {
        this.uriAvatar = uriAvatar;
    }

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

    public Bitmap getBitmapAvatar() {
        return bitmapAvatar;
    }

    public void setBitmapAvatar(Bitmap bitmapAvatar) {
        this.bitmapAvatar = bitmapAvatar;
    }

    @Override
    public String toString() {
        return "User{" +
                "bitmapAvatar=" + bitmapAvatar +
                ", uriAvatar='" + uriAvatar + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

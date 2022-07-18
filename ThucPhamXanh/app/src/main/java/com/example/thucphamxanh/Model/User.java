package com.example.thucphamxanh.Model;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;

public class User implements Serializable {

    private String strUriAvatar;
    private String id;
    private Bitmap bitmapAvatar;
    private Uri uriAvatar;
    private String name;
    private String email;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStrUriAvatar() {
        return strUriAvatar;
    }

    public void setStrUriAvatar(String strUriAvatar) {
        this.strUriAvatar = strUriAvatar;
    }
    private String password;
    public Uri getUriAvatar() {
        return uriAvatar;
    }

    public void setUriAvatar(Uri uriAvatar) {
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
    public String
    toString() {
        return "User{" +
                "strUriAvatar='" + strUriAvatar + '\'' +
                ", id='" + id + '\'' +
                ", bitmapAvatar=" + bitmapAvatar +
                ", uriAvatar=" + uriAvatar +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

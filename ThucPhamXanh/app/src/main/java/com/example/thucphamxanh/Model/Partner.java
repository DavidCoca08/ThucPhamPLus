package com.example.thucphamxanh.Model;

import java.util.HashMap;
import java.util.Map;

public class Partner {
    private int idPartner;
    private String namePartner,addressPartner,userPartner,passwordPartner,imgPartner;

    public Partner() {
    }

    public String getImgPartner() {
        return imgPartner;
    }

    public void setImgPartner(String imgPartner) {
        this.imgPartner = imgPartner;
    }

    public int getIdPartner() {
        return idPartner;
    }

    public void setIdPartner(int idPartner) {
        this.idPartner = idPartner;
    }

    public String getNamePartner() {
        return namePartner;
    }

    public void setNamePartner(String namePartner) {
        this.namePartner = namePartner;
    }

    public String getAddressPartner() {
        return addressPartner;
    }

    public void setAddressPartner(String adressPartner) {
        this.addressPartner = adressPartner;
    }


    public String getUserPartner() {
        return userPartner;
    }

    public void setUserPartner(String userPartner) {
        this.userPartner = userPartner;
    }

    public String getPasswordPartner() {
        return passwordPartner;
    }

    public void setPasswordPartner(String passwordPartner) {
        this.passwordPartner = passwordPartner;
    }

    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("namePartner",getNamePartner());
        result.put("addressPartner",getAddressPartner());
        result.put("userPartner",getUserPartner());
        result.put("passwordPartner",getPasswordPartner());
        return result;



    }
}

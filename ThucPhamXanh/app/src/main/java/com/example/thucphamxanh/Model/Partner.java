package com.example.thucphamxanh.Model;

import java.util.HashMap;
import java.util.Map;

public class Partner {
    private int codePartner;
    private String namePartner,addressPartner,userPartner,passwordPartner;

    public Partner() {
    }

    public int getCodePartner() {
        return codePartner;
    }

    public void setCodePartner(int codePartner) {
        this.codePartner = codePartner;
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

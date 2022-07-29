package com.example.thucphamxanh.Model;

import java.util.HashMap;
import java.util.Map;

public class Voucher {
    private int idVoucher;
    private String codeVoucher, imgVoucher;

    public Voucher() {
    }

    public int getIdVoucher() {
        return idVoucher;
    }

    public void setIdVoucher(int idVoucher) {
        this.idVoucher = idVoucher;
    }

    public String getCodeVoucher() {
        return codeVoucher;
    }

    public void setCodeVoucher(String codeVoucher) {
        this.codeVoucher = codeVoucher;
    }

    public String getImgVoucher() {
        return imgVoucher;
    }

    public void setImgVoucher(String imgVoucher) {
        this.imgVoucher = imgVoucher;
    }

    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("idVoucher",getIdVoucher());
        result.put("codeVoucher",getCodeVoucher());
        result.put("imgVoucher",getImgVoucher());

        return result;
    }
}

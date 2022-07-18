package com.example.thucphamxanh.Model;

import java.util.HashMap;
import java.util.Map;

public class Category {
    private int maLoai;
    private String tenLoai;

    public Category() {
    }

    public Category(int maLoai, String tenLoai) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
    public Map<String, Object> toMap(){
        HashMap<String,Object > reslut = new HashMap<>();
        reslut.put("tenLoai",getTenLoai());
        return reslut;
    }
}

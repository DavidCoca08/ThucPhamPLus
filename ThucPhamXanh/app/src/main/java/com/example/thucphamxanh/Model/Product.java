package com.example.thucphamxanh.Model;

public class Product {
    private int maSP,soLuong,maTL,maDT,giaBan;
    private String tenSP,hinhSP;

    public Product() {
    }

    public Product(int maSP, int soLuong, int maTL, int maDT, int giaBan, String tenSP, String hinhSP) {
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.maTL = maTL;
        this.maDT = maDT;
        this.giaBan = giaBan;
        this.tenSP = tenSP;
        this.hinhSP = hinhSP;
    }

    public String getHinhSP() {
        return hinhSP;
    }

    public void setHinhSP(String hinhSP) {
        this.hinhSP = hinhSP;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getMaTL() {
        return maTL;
    }

    public void setMaTL(int maTL) {
        this.maTL = maTL;
    }

    public int getMaDT() {
        return maDT;
    }

    public void setMaDT(int maDT) {
        this.maDT = maDT;
    }

    public int getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }
}

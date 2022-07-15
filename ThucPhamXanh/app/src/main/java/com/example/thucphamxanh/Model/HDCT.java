package com.example.thucphamxanh.Model;

public class HDCT {
    private int maSP,maHD,soLuong,giaBan;

    public HDCT() {
    }

    public HDCT(int maSP, int maHD, int soLuong, int giaBan) {
        this.maSP = maSP;
        this.maHD = maHD;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }
}

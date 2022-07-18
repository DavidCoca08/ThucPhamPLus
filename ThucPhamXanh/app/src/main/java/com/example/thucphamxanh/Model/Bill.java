package com.example.thucphamxanh.Model;

public class Bill {
    private int maHD,maKH;
    private String ngayXuat,trangThai;

    public Bill() {
    }

    public Bill(int maHD, int maKH, String ngayXuat, String trangThai) {
        this.maHD = maHD;
        this.maKH = maKH;
        this.ngayXuat = ngayXuat;
        this.trangThai = trangThai;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getNgayXuat() {
        return ngayXuat;
    }

    public void setNgayXuat(String ngayXuat) {
        this.ngayXuat = ngayXuat;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}

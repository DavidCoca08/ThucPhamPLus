package com.example.thucphamxanh.Model;

public class DoiTac {
    private int maDT;
    private String tenDT,diaChi,sdt;

    public DoiTac() {
    }

    public DoiTac(int maDT, String tenDT, String diaChi, String sdt) {
        this.maDT = maDT;
        this.tenDT = tenDT;
        this.diaChi = diaChi;
        this.sdt = sdt;
    }

    public int getMaDT() {
        return maDT;
    }

    public void setMaDT(int maDT) {
        this.maDT = maDT;
    }

    public String getTenDT() {
        return tenDT;
    }

    public void setTenDT(String tenDT) {
        this.tenDT = tenDT;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}

package com.example.thucphamxanh.Model;

import java.util.List;

public class Cart {
    private int idProduct;
    private String imgProduct;
    private String nameProduct;
    private int priceProduct;
    private int numberProduct;
    private int totalPrice;

    public Cart() {
    }

    public Cart(int idProduct, String imgProduct, String nameProduct, int priceProduct, int numberProduct, int totalPrice) {
        this.idProduct = idProduct;
        this.imgProduct = imgProduct;
        this.nameProduct = nameProduct;
        this.priceProduct = priceProduct;
        this.numberProduct = numberProduct;
        this.totalPrice = totalPrice;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(String imgProduct) {
        this.imgProduct = imgProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(int priceProduct) {
        this.priceProduct = priceProduct;
    }

    public int getNumberProduct() {
        return numberProduct;
    }

    public void setNumberProduct(int numberProduct) {
        this.numberProduct = numberProduct;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}

package com.example.moneymanager;

import java.lang.ref.SoftReference;

public class Note {
    //класс затраты, содержит все ее характеристики, используется как шаблон для преобразования с БД
    private int id;
    private double price;
    private String userId;
    private String category;
    private String date;

    public Note(int id, double price, String userId, String category, String date) {
        this.id = id;
        this.price = price;
        this.userId = userId;
        this.category = category;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

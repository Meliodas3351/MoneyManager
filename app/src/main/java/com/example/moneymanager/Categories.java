package com.example.moneymanager;

public enum Categories {
    //множества категорий

    PRODUCTS("food"),
    CAFE("cafe"),
    LEISURE("leisure"),
    HEALTH("health"),
    FAMILY("family"),
    GIFTS("gifts"),
    PURCHASES("purchases"),
    TRANSPORT("transport");

    private final String categoryName;

    // private enum constructor
    private Categories(String pizzaSize) {
        this.categoryName = pizzaSize;
    }

    public String getCategoryName() {
        return categoryName;
    }

}

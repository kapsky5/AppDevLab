package com.example.restaurant;

class Dish {
    private Cuisine cuisine;
    private String dishName;
    private int price;

    Dish(Cuisine cuisine, String dish, int price) {
        this.cuisine = cuisine;
        this.dishName = dish;
        this.price = price;
    }

    String getDish() {
        return this.dishName;
    }

    int getPrice() {
        return this.price;
    }

    Cuisine getCuisine() {
        return this.cuisine;
    }
}
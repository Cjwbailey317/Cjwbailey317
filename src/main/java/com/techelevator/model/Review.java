package com.techelevator.model;

public class Review {

    private int id;
    private int userId;
    private int coffeeShopId;
    private String comment;
    private int rating;

    public Review() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCoffeeShopId() {
        return coffeeShopId;
    }

    public void setCoffeeShopId(int coffeeShopId) {
        this.coffeeShopId = coffeeShopId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    // Optional: toString, equals, hashcode methods if necessary
    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", userId=" + userId +
                ", coffeeShopId=" + coffeeShopId +
                ", comment='" + comment + '\'' +
                ", rating=" + rating +
                '}';
    }
}
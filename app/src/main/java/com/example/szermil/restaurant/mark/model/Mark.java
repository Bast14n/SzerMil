package com.example.szermil.restaurant.mark.model;

import android.graphics.Bitmap;

public class Mark {
    private long userId;
    private long restaurantId;
    private String mealName;
    private String comment;
    private int rating;
    private Bitmap photo;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRaiting() {
        return rating;
    }

    public void setRaiting(int rating) {
        this.rating = rating;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Mark{" +
                "userId=" + userId +
                ", restaurantId=" + restaurantId +
                ", mealName='" + mealName + '\'' +
                ", comment='" + comment + '\'' +
                ", rating=" + rating +
                ", photo=" + photo +
                '}';
    }
}

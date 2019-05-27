package com.example.szermil.restaurant.mark.model;

public class Mark {
    private String userId;
    private long restaurantId;
    private String mealName;
    private String comment;
    private int rating;
    private String photoBase64;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


    @Override
    public String toString() {
        return "Mark{" +
                "userId=" + userId +
                ", restaurantId=" + restaurantId +
                ", mealName='" + mealName + '\'' +
                ", comment='" + comment + '\'' +
                ", rating=" + rating +
                ", photo=" + photoBase64 +
                '}';
    }

    public String getPhotoBase64() {
        return photoBase64;
    }

    public void setPhotoBase64(String photoBase64) {
        this.photoBase64 = photoBase64;
    }
}

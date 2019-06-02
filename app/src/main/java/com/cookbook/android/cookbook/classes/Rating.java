package com.cookbook.android.cookbook.classes;

public class Rating {
    int ratingID;
    int recipeID;
    double rating;

    public Rating(int ratingID, int recipeId, double rating) {
        this.ratingID = ratingID;
        this.recipeID = recipeId;
        this.rating = rating;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "ratingID=" + ratingID +
                ", recipeID=" + recipeID +
                ", rating=" + rating +
                '}';
    }

    public int getRecipeId() {
        return recipeID;
    }

    public void setRecipeId(int recipeId) {
        this.recipeID = recipeId;
    }

    public int getRatingID() {
        return ratingID;
    }

    public void setRatingID(int ratingID) {
        this.ratingID = ratingID;
    }
}
package com.cookbook.android.cookbook.classes;

import android.graphics.Bitmap;

public class Image {
    private int imageId;
    private Bitmap bitmap;
    private int recipeId;

    public Image(int imageId, Bitmap bitmap, int recipeId) {
        this.imageId = imageId;
        this.bitmap = bitmap;
        this.recipeId = recipeId;
    }

    public int getImageId() {
        return imageId;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getRecipeId() {
        return recipeId;
    }
}

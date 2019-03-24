package com.cookbook.android.cookbook;

public class Ingredient {
    private Product product;
    //    private Recipe recipe;
    private String quantity;
    private int ingedientID;
    private int recipeId;


    public Ingredient(int ingedientID,Product product, String quantity, int recipeId) {
        this.ingedientID = ingedientID;
        this.product = product;
        this.quantity = quantity;
        this.recipeId = recipeId;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public int getIngedientID() {
        return ingedientID;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public String toString(){
        return ""+product.getName()+", "+quantity+", "+recipeId;
    }


}

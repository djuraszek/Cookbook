package com.cookbook.android.cookbook.classes;

public class Product {
    private String category;
    private String name;

    private int productID;

    public Product(int productID, String name, String category) {
        this.productID = productID;
        this.category = category;
        this.name = name;
    }

    public String toString(){
        return name + " <" + category+">";
    }

    public int getProductID() {
        return productID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // categories:
    // dairy - cheeses, eggs, milk, yogurt, butter, cream,
    // vegetables -     \
    // fruits           /
    // dry / staple - flour, sugar, baking powder,
    // beverage - coffe/tea, juice, water,
    // fats
    // nuts

}

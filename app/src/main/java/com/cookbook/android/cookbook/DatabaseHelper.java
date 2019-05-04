package com.cookbook.android.cookbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.util.Log;

import com.cookbook.android.cookbook.classes.Image;
import com.cookbook.android.cookbook.classes.Ingredient;
import com.cookbook.android.cookbook.classes.Product;
import com.cookbook.android.cookbook.classes.Recipe;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    boolean showLogs = false;
    public static final String SELECT_ALL_FROM = "Select*FROM ";

    //initialize the databaseHelper

    //information of databaseHelper
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "cookbook.db";
//    public static final String DATABASE_PATH =  "/data/data/com.kinokometa.android.kinokometa/databases/";

    //Recipe(int recipeID, String name, double rating, String portion, String preparation)
    public static final String TABLE_RECIPE_NAME = "Recipe";
    public static final String COLUMN_RECIPE_ID = "RecipeId";   //int
    public static final String COLUMN_RECIPE_NAME = "RecipeName";
    public static final String COLUMN_RECIPE_RATING = "Rating";
    public static final String COLUMN_RECIPE_PORTION = "Portion";
    public static final String COLUMN_RECIPE_PREPARATION = "Preparation";

    //Ingredient(int ingedientID,Product product, String quantity, int recipeId)
    public static final String TABLE_INGREDIENT_NAME = "Ingredient";
    public static final String COLUMN_INGREDIENT_ID = "IngredientId";
    public static final String COLUMN_INGREDIENT_PRODUCT = "ProductId";
    public static final String COLUMN_INGREDIENT_QUANTITY = "Quantity";
    public static final String COLUMN_INGREDIENT_RECIPE = "RecipeId";

    //Product(int productID, String name, String category) {
    public static final String TABLE_PRODUCT_NAME = "Product";
    public static final String COLUMN_PRODUCT_ID= "ProductId";
    public static final String COLUMN_PRODUCT_NAME= "ProductName";
    public static final String COLUMN_PRODUCT_CATEGORY = "Category";

    //Image(int imageId, Bitmap[] bitmap)
    public static final String TABLE_IMAGE_NAME = "Image";
    public static final String COLUMN_IMAGE_ID = "ImageId";
    public static final String COLUMN_IMAGE_BLOB = "Blob";
    public static final String COLUMN_IMAGE_RECIPE = "Recipe";


    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("DatabaseHelper","onCreate");
        String SQL_CREATE_TABLE_RECIPE =
                "CREATE TABLE " + TABLE_RECIPE_NAME + " ( "
                        + COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_RECIPE_NAME + " TEXT, "
                        + COLUMN_RECIPE_RATING + " NUMBER, "
                        + COLUMN_RECIPE_PORTION + " TEXT, "
                        + COLUMN_RECIPE_PREPARATION + " TEXT "
                        +"); ";

        String SQL_CREATE_TABLE_PRODUCT =
                "CREATE TABLE " + TABLE_PRODUCT_NAME + " ( "
                        + COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_PRODUCT_NAME + " TEXT, "
                        + COLUMN_PRODUCT_CATEGORY + " TEXT "
                        +"); ";

        String SQL_CREATE_TABLE_INGREDIENT =
                "CREATE TABLE " + TABLE_INGREDIENT_NAME + " ( "
                        + COLUMN_INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_INGREDIENT_PRODUCT + " INTEGER, "
                        + COLUMN_INGREDIENT_QUANTITY + " TEXT, "
                        + COLUMN_INGREDIENT_RECIPE + " INTEGER "
                        +"); ";

        String SQL_CREATE_TABLE_IMAGE =
                "CREATE TABLE " + TABLE_IMAGE_NAME + " ( "
                        + COLUMN_IMAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_IMAGE_BLOB + " BLOB, "
                        + COLUMN_IMAGE_RECIPE + " INTEGER "
                        +"); ";

        db.execSQL(SQL_CREATE_TABLE_IMAGE);
        db.execSQL(SQL_CREATE_TABLE_INGREDIENT);
        db.execSQL(SQL_CREATE_TABLE_PRODUCT);
        db.execSQL(SQL_CREATE_TABLE_RECIPE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addRecipe(Recipe r) {
        Log.e("MyDBHandler","addRecipe");
        ContentValues values = new ContentValues();
        System.out.println("DB Recipe: "+r.toString());


        values.put(COLUMN_RECIPE_ID, r.getRecipeID());
        values.put(COLUMN_RECIPE_NAME, r.getName());
        values.put(COLUMN_RECIPE_RATING, r.getRating());
        values.put(COLUMN_RECIPE_PORTION, r.getPortion());
        values.put(COLUMN_RECIPE_PREPARATION, r.getPreparation());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_RECIPE_NAME, null, values);
        db.close();
    }

    public void addIngredient(Ingredient i) {
        Log.e("MyDBHandler","addIngredient");
        ContentValues values = new ContentValues();
        System.out.println("DB Ingredient: "+i.toString());


        values.put(COLUMN_INGREDIENT_ID, i.getIngedientID());
        values.put(COLUMN_INGREDIENT_PRODUCT, i.getProduct().getProductID());
        values.put(COLUMN_INGREDIENT_QUANTITY, i.getQuantity());
        values.put(COLUMN_INGREDIENT_RECIPE, i.getRecipeId());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_INGREDIENT_NAME, null, values);
        db.close();
    }

    public void addProduct(Product p) {
        Log.e("MyDBHandler","addProduct");
        ContentValues values = new ContentValues();
        System.out.println("DB Product : "+p.toString());


        values.put(COLUMN_PRODUCT_ID, p.getProductID());
        values.put(COLUMN_PRODUCT_NAME, p.getName());
        values.put(COLUMN_PRODUCT_CATEGORY, p.getCategory());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_PRODUCT_NAME, null, values);
        db.close();
    }

    public void addImage(Image i) {
        Log.e("MyDBHandler","addImage");
        ContentValues values = new ContentValues();
//        System.out.println("DB Image : "+i.toString());

        byte[] data = getBitmapAsByteArray(i.getBitmap()); // this is a function

        values.put(COLUMN_IMAGE_ID, i.getImageId());
        values.put(COLUMN_IMAGE_BLOB, data);
        values.put(COLUMN_IMAGE_RECIPE, i.getRecipeId());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_IMAGE_NAME, null, values);
        db.close();
    }
    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public static Bitmap getByteArrayAsBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    public int getProductId(String productName){
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT_NAME;
        selectQuery += " WHERE "+COLUMN_PRODUCT_NAME+" = "+productName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all records and adding to the list
        int productID = -1;

        if (c.moveToFirst()) {
            productID = c.getInt(c.getColumnIndex(COLUMN_PRODUCT_ID));
        }
        //if returns -1 than there is no such a product in database
        //so you have to add it....
        return productID;
    }

    public static void main(String[] args){
        DatabaseHelper db = new DatabaseHelper(null,null,null,1);
        db.getProductId("Masło");
    }


    public List<Recipe> getAllRecipeList() {
        Log.e("DatabaseHelper","getAllRecipeList");
        List<Recipe> recipes = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_RECIPE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all records and adding to the list
        if (c.moveToFirst()) {
            do {
                int recipeID= c.getInt(c.getColumnIndex(COLUMN_RECIPE_ID));
                String name= c.getString(c.getColumnIndex(COLUMN_RECIPE_NAME));
                double rating= c.getDouble(c.getColumnIndex(COLUMN_RECIPE_RATING));
                String portion= c.getString(c.getColumnIndex(COLUMN_RECIPE_PORTION));
                String prep= c.getString(c.getColumnIndex(COLUMN_RECIPE_PREPARATION));

                Recipe r = new Recipe(recipeID, name, rating, portion, prep );
                recipes.add(r);
            } while (c.moveToNext());
        }
//        Log.e("DatabaseHelper","recipes.size()" +recipes.size());
//        Log.e("DatabaseHelper","" +recipes);
        return recipes;
    }

    public List<Ingredient> getAllIngredientList() {
        Log.e("DatabaseHelper","getAllIngredientList");
        List<Ingredient> list = new ArrayList<>();
        List<Product> prodList = getAllProductsList();
//        List<Recipe> recipesList = getAllRecipeList();

        String selectQuery = "SELECT  * FROM " + TABLE_INGREDIENT_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        boolean productFound = false;
        // looping through all records and adding to the list
        if (c.moveToFirst()) {
            do {
                int ingredientID= c.getInt(c.getColumnIndex(COLUMN_INGREDIENT_ID));
                int product= c.getInt(c.getColumnIndex(COLUMN_INGREDIENT_PRODUCT));
                String quantity= c.getString(c.getColumnIndex(COLUMN_INGREDIENT_QUANTITY));
                int recipeFK= c.getInt(c.getColumnIndex(COLUMN_INGREDIENT_RECIPE));

                for(int i = 0; i < prodList.size(); i++) {
                    if(product == prodList.get(i).getProductID()) {
                        Ingredient r = new Ingredient(ingredientID, prodList.get(i),quantity,recipeFK);
                        list.add(r);
                        productFound = true;
                    }
                }
                if(!productFound)
                    Log.e("DatabaseHelper","getAllIngredientList - product not found - ingredient not added");
//                recipesListView.get(recipeFK).addIngredient(r);

            } while (c.moveToNext());
        }
        Log.d("DatabaseHelper","ingredientList.size()"+list.size());
        return list;
    }

    public List<Product> getAllProductsList() {
        Log.e("DatabaseHelper", "getAllProductsList");
        List<Product> list = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all records and adding to the list
        if (c.moveToFirst()) {
            do {
                int productID = c.getInt(c.getColumnIndex(COLUMN_PRODUCT_ID));
                String name = c.getString(c.getColumnIndex(COLUMN_PRODUCT_NAME));
                String category = c.getString(c.getColumnIndex(COLUMN_PRODUCT_CATEGORY));
                Product r = new Product(productID, name, category);
                list.add(r);
            } while (c.moveToNext());
        }
        return list;
    }

    public List<Image> getAllImagesList(){
        Log.e("DatabaseHelper", "getAllProductsList");
        List<Image> list = new ArrayList<>();
//        List<Recipe> recipesList = getAllRecipeList();

        String selectQuery = "SELECT  * FROM " + TABLE_IMAGE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all records and adding to the list
        System.out.println(""+c.getColumnName(0)+
                ""+c.getColumnName(1)+
                ""+c.getColumnName(2));
        if (c.moveToFirst()) {
            do {
                int imgID = c.getInt(c.getColumnIndex(COLUMN_IMAGE_ID));
                byte[] byteArray = c.getBlob(c.getColumnIndex(COLUMN_IMAGE_BLOB));
                int recipeFK = c.getInt(c.getColumnIndex(COLUMN_IMAGE_RECIPE));

                Bitmap blob = getByteArrayAsBitmap(byteArray);

                Image r = new Image(imgID, blob, recipeFK);
                list.add(r);
            } while (c.moveToNext());
        }
        return list;
    }

    public List<String> getDistinctCategories(){
        String SELECT_DISTINCT_CATEGORIES = "SELECT Category FROM( SELECT distinct Category, COUNT(ProductName) \n" +
                "FROM Product WHERE Category<>'NULL' Group by Category Order by COUNT(ProductName) desc, Category)";

        List<String> categoriesList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(SELECT_DISTINCT_CATEGORIES, null);
        // looping through all records and adding to the list
        if (c.moveToFirst()) {
            do {
                String category = c.getString(c.getColumnIndex(COLUMN_PRODUCT_CATEGORY));
                categoriesList.add(category);
            } while (c.moveToNext());
        }
        return categoriesList;
    }
}

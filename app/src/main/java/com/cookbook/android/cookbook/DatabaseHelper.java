package com.cookbook.android.cookbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.ByteArrayOutputStream;

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
        System.out.println("DB Image : "+i.toString());

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

}

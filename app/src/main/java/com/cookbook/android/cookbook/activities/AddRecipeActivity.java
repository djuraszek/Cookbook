package com.cookbook.android.cookbook.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cookbook.android.cookbook.DatabaseHelper;
import com.cookbook.android.cookbook.R;
//import com.cookbook.android.cookbook.adapters.AddedIngredientsListAdapter;
import com.cookbook.android.cookbook.classes.Image;
import com.cookbook.android.cookbook.classes.Ingredient;
import com.cookbook.android.cookbook.classes.Product;
import com.cookbook.android.cookbook.classes.Recipe;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class AddRecipeActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    //    ImageView addImgIV;
    EditText nameEditText, portionEditText, preparationEditText, ingredientName, ingredientCapacity;
    ListView ingredientsListView;
    Button addRecipeBtn;
    ImageButton addIngredientIB;
    private ImageView mImageView;

    ArrayList<Ingredient> addedIngredients;

    // Constant that's used as a parameter to assist with the permission requesting process.
    private static final int PERMISSION_CODE = 100;
    // Int constant that's used to handle the result back when an image is selected from the
    // device's gallery.
    private static final int RESULT_LOAD_IMAGE = 1;

    private List<Product> productsToAddToDB = new ArrayList<>();
    private List<Product> recipeProducts = new ArrayList<>();
    private List<Ingredient> recipeIngredients = new ArrayList<>();

//    AddedIngredientsListAdapter listAdapter;
    boolean imageSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        databaseHelper = new DatabaseHelper(this);
        initVals();

        runUi();
    }

    public void initVals() {
//        addImgIV = (ImageView)findViewById(R.id.addImgIV);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        portionEditText = (EditText) findViewById(R.id.portionEditText);
        preparationEditText = (EditText) findViewById(R.id.preparationEditText);
        ingredientName = (EditText) findViewById(R.id.ingredientName);
        ingredientCapacity = (EditText) findViewById(R.id.ingredientCapacity);
        ingredientsListView = (ListView) findViewById(R.id.ingredientsListView);
        addRecipeBtn = (Button) findViewById(R.id.addRecipeBtn);
        addIngredientIB = (ImageButton) findViewById(R.id.addIngredientIB);
        mImageView = (ImageView) findViewById(R.id.addImgIV);
        addedIngredients = new ArrayList<>();

//        listAdapter = new AddedIngredientsListAdapter(AddRecipeActivity.this, recipeIngredients);
//        ingredientsListView.setAdapter(listAdapter);

        buttonListeners();
    }


    public void buttonListeners() {
        //add ingredient to ListView
        addIngredientIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ingredientName==null || ingredientName.getText().equals("") ||
                     ingredientCapacity==null  || ingredientCapacity.getText().equals("")) return;
                else{
                    String productName = ingredientName.getText().toString();
                    String ingrCapacity = ingredientCapacity.getText().toString();
                    Product p = findProduct(productName);
                    if(p!= null && ingredientsListView!= null) {
                        recipeProducts.add(p);
                        Ingredient i = new Ingredient(databaseHelper.getLastIngredientID() + 1,
                                p, ingrCapacity, databaseHelper.getLastRecipeID() + 1);
                        i.setProduct(p);
                        Log.e("AddRecipeActivity.buttonList()",i.toString());
                        recipeIngredients.add(i);
//                        listAdapter.notifyDataSetChanged();

                        ingredientName.setText("");
                        ingredientCapacity.setText("");
                    }
                    else{
                        Toast.makeText(AddRecipeActivity.this, "Nie ma produktu w bazie", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        addRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if this than add Recipe
                if (nameEditText.getText() != null && portionEditText.getText() != null &&
                        preparationEditText.getText() != null && addedIngredients.size() > 2) {
                    addRecipe();
                }
                else if(nameEditText.getText() == null){
                    Toast.makeText(getApplicationContext(),"Dodaj nazwę ciasta",Toast.LENGTH_SHORT).show();
                }
                else if(portionEditText.getText() == null){
                    Toast.makeText(getApplicationContext(),"Dodaj informację o procji",Toast.LENGTH_SHORT).show();
                }
                else if(preparationEditText.getText() == null){
                    Toast.makeText(getApplicationContext(),"Dodaj przygotowanie ciasta",Toast.LENGTH_SHORT).show();
                }
                else if(addedIngredients.size() < 2){
                    Toast.makeText(getApplicationContext(),"Dodaj przynajmniej 3 składniki",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addRecipe(){
        int recipeID = databaseHelper.getLastRecipeID() + 1;
        String recipeName = nameEditText.getText().toString();
        String recipePortion = portionEditText.getText().toString();
        String recipePreparation = preparationEditText.getText().toString();
        Recipe recipe = new Recipe(recipeID, recipeName, 0, recipePortion, recipePreparation);

        databaseHelper.addRecipe(recipe);

        if(imageSet) {
            Image image = new Image(recipeID, mImageView.getDrawingCache(), recipeID);
            databaseHelper.addImage(image);
        }
        //add ingredients
        for(int i=0;i<recipeIngredients.size();i++){
            databaseHelper.addIngredient(recipeIngredients.get(i));
        }
        //add products
        for(int i=0;i<productsToAddToDB.size();i++){
            databaseHelper.addProduct(productsToAddToDB.get(i));
        }

    }

    private Product findProduct(String name){
        int productID = databaseHelper.getProductId(name);
        if(productID>=0){
            for(int i=0;i<databaseHelper.getAllProductsList().size();i++){
                if(databaseHelper.getAllProductsList().get(i).getProductID() == productID){
                    return databaseHelper.getAllProductsList().get(i);
                }
            }
        }
        else{
            int newProductID = databaseHelper.getLastProductID()+1;
            Product p = new Product(newProductID,name,"Inne");
            productsToAddToDB.add(p);
            return p;
        }
        return null;
    }

    private void requestPermissions() {
        // Requests permission for devices with versions Marshmallow (M)/API 23 or above.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_CODE);
                return;
            }
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_CODE);
                return;
            }
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        PERMISSION_CODE);
                return;
            }
        }
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }


    private void runUi() {
        mImageView = (ImageView) findViewById(R.id.addImgIV);

        // Sets the image button clickable with the following functionality.
        findViewById(R.id.addImgIV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermissions();
                showPictureDialog();
                // Instantiates an Intent object for accessing the device's storage.
//                Intent intent = new Intent(
//                        Intent.ACTION_PICK,
//                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//
//                // Triggers the image gallery.
//                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });
    }

    //Displays a permission dialog when requested for devices M and above.
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSION_CODE) {

            // User accepts the permission(s).
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //todo add onclicklistener to image

            } else { // User denies the permission.
                Toast.makeText(this, "Please come back and then grant permissions!",
                        Toast.LENGTH_SHORT).show();

                // Runs a thread for a slight delay prior to shutting down the app.
                Thread mthread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(1500);
                            System.exit(0);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };

                mthread.start();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == RESULT_LOAD_IMAGE) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(AddRecipeActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    mImageView.setImageBitmap(bitmap);
                    imageSet = true;

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(AddRecipeActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            mImageView.setImageBitmap(thumbnail);
            String path = saveImage(thumbnail);
            Toast.makeText(AddRecipeActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
            mImageView.setImageBitmap(thumbnail);
            imageSet = true;
        }
    }

    String IMAGE_DIRECTORY = "";
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        Log.d("AddRecipeActivity",""+Environment.getExternalStorageDirectory());
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }
}

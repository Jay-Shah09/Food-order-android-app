package com.example.groceryshopjayshah;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AdminAddGroceryScreen extends Activity {
    EditText etAdminAddGroceryName, etAdminAddGroceryPrice, etAdminAddGroceryStock, etAdminAddGroceryMeasurement;
    Button btnAdminAddGroceryAddImage, btnAdminAddGroceryAddGrocery;
    ProgressBar pbAdminAddGrocery;
    Uri imagePath;
    DatabaseReference dbRef;
    StorageReference storage,store;

    String gName,measure,imgUrl,gid;
    int price,stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_grocery_screen);

        etAdminAddGroceryName = findViewById(R.id.etAdminAddGroceryName);
        etAdminAddGroceryPrice = findViewById(R.id.etAdminAddGroceryPrice);
        etAdminAddGroceryStock = findViewById(R.id.etAdminAddGroceryStock);
        etAdminAddGroceryMeasurement = findViewById(R.id.etAdminAddGroceryMeasurement);

        btnAdminAddGroceryAddImage = findViewById(R.id.btnAdminAddGroceryAddImage);
        btnAdminAddGroceryAddGrocery = findViewById(R.id.btnAdminAddGroceryAddGrocery);

        pbAdminAddGrocery = findViewById(R.id.pbAdminAddGrocery);
        pbAdminAddGrocery.setVisibility(pbAdminAddGrocery.GONE);

        btnAdminAddGroceryAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);  // This is used to take image from gallery
                startActivityForResult(gallery, 201);
            }
        });

        btnAdminAddGroceryAddGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gName = etAdminAddGroceryName.getText().toString();
                price = Integer.parseInt(etAdminAddGroceryPrice.getText().toString());
                stock = Integer.parseInt(etAdminAddGroceryStock.getText().toString());
                measure = etAdminAddGroceryMeasurement.getText().toString();

                dbRef = FirebaseDatabase.getInstance().getReference("grocery");
                storage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://groceryshopjayshah.appspot.com/grocery");

                store = storage.child(gName);
                gid = dbRef.push().getKey();

                store.putFile(imagePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                store.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Toast.makeText(getApplicationContext(),"IMAGE UPLOADED",Toast.LENGTH_LONG).show();

                                        Grocery g = new Grocery(gid,uri.toString(),gName,price,stock,measure);
                                        dbRef.child(gid).setValue(g);

                                        Toast.makeText(getApplicationContext(),"GROCERY REGISTERED SUCCESSFULLY",Toast.LENGTH_LONG).show();

                                        etAdminAddGroceryName.setText("");
                                        etAdminAddGroceryPrice.setText("");
                                        etAdminAddGroceryStock.setText("");
                                        etAdminAddGroceryMeasurement.setText("");
                                    }
                                });
                            }
                        });
            }
        });
    }

    @Override
    public void onActivityResult(int reqCode, int res, Intent data) {
        super.onActivityResult(reqCode, res, data);
        if (res == RESULT_OK && reqCode == 201)   // here res==RESULT_OK is used to check whether anything is selected from gallery or not and reqCode==201 is used to check image is selected or not.
        {
            imagePath = data.getData();

            Toast.makeText(getApplicationContext(), "IMAGE SELECTED", Toast.LENGTH_LONG).show();
        }
    }
}
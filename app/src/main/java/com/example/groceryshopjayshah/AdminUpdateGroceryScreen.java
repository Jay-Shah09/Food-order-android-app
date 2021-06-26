package com.example.groceryshopjayshah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class AdminUpdateGroceryScreen extends Activity {

    Spinner updateSpinner;
    EditText etAdminUpdateGroceryName, etAdminUpdateGroceryPrice, etAdminUpdateGroceryStock, etAdminUpdateGroceryMeasure;
    Button btnAdminUpdateGroceryImage, btnAdminUpdateGroceryGrocery;

    List listName = new ArrayList();
    List<Grocery> listObj = new ArrayList<Grocery>();
    DatabaseReference dbRef;
    StorageReference storage;
    String gName,gMeasure,gId,gUrl;
    int price,stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_grocery_screen);

        etAdminUpdateGroceryName = findViewById(R.id.etAdminUpdateGroceryName);
        etAdminUpdateGroceryPrice = findViewById(R.id.etAdminUpdateGroceryPrice);
        etAdminUpdateGroceryStock = findViewById(R.id.etAdminUpdateGroceryStock);
        etAdminUpdateGroceryMeasure = findViewById(R.id.etAdminUpdateGroceryMeasure);

        btnAdminUpdateGroceryImage = findViewById(R.id.btnAdminUpdateGroceryImage);
        btnAdminUpdateGroceryGrocery = findViewById(R.id.btnAdminUpdateGroceryGrocery);

        updateSpinner = findViewById(R.id.updateSpinner);

        dbRef = FirebaseDatabase.getInstance().getReference("grocery");
        storage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://groceryshopjayshah.appspot.com/grocery");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                listName.clear();
                listObj.clear();

                for(DataSnapshot snap : snapshot.getChildren())
                {
                    Grocery g = snap.getValue(Grocery.class);

                    listName.add(g.getgName());
                    listObj.add(g);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,listName);
                updateSpinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        updateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                gId = listObj.get(position).getGid();
                gName = listObj.get(position).getgName();
                gUrl = listObj.get(position).getImageUri();
                price = listObj.get(position).getPrice();
                stock = listObj.get(position).getStock();
                gMeasure = listObj.get(position).getMeasure();

                etAdminUpdateGroceryName.setText(""+gName);
                etAdminUpdateGroceryPrice.setText(""+price);
                etAdminUpdateGroceryStock.setText(""+stock);
                etAdminUpdateGroceryMeasure.setText(""+gMeasure);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAdminUpdateGroceryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnAdminUpdateGroceryGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbRef = FirebaseDatabase.getInstance().getReference("grocery");

                price = Integer.parseInt(etAdminUpdateGroceryPrice.getText().toString());
                stock = Integer.parseInt(etAdminUpdateGroceryStock.getText().toString());
                gMeasure = etAdminUpdateGroceryMeasure.getText().toString();

                Grocery gr = new Grocery(gId,gUrl,gName,price,stock,gMeasure);

                dbRef.child(gId).setValue(gr);

                Toast.makeText(getApplicationContext(),"GROCERY UPDATED",Toast.LENGTH_LONG).show();
            }
        });


    }
}
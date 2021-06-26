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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class AdminDeleteGroceryScreen extends Activity {

    Spinner spinner;
    EditText etAdminDeleteGroceryName,etAdminDeleteGroceryPrice,etAdminDeleteGroceryStock,etAdminDeleteGroceryMeasure;
    Button btnAdminDeleteGrocery;
    List listName = new ArrayList();
    List<Grocery> listObj = new ArrayList<Grocery>();
    DatabaseReference dbRef;
    StorageReference storage;
    String gId,gUrl,gName,measure;
    int price,stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete_grocery_screen);

        spinner = findViewById(R.id.spinner);
        etAdminDeleteGroceryName = findViewById(R.id.etAdminDeleteGroceryName);
        etAdminDeleteGroceryPrice = findViewById(R.id.etAdminDeleteGroceryPrice);
        etAdminDeleteGroceryStock = findViewById(R.id.etAdminDeleteGroceryStock);
        etAdminDeleteGroceryMeasure = findViewById(R.id.etAdminDeleteGroceryMeasure);
        btnAdminDeleteGrocery = findViewById(R.id.btnAdminDeleteGrocery);

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
                spinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                gId = listObj.get(position).getGid();
                gName = listObj.get(position).getgName();
                gUrl = listObj.get(position).getImageUri();
                price = listObj.get(position).getPrice();
                stock = listObj.get(position).getStock();
                measure = listObj.get(position).getMeasure();

                etAdminDeleteGroceryName.setText(""+gName);
                etAdminDeleteGroceryPrice.setText(""+price);
                etAdminDeleteGroceryStock.setText(""+stock);
                etAdminDeleteGroceryMeasure.setText(""+measure);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAdminDeleteGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbRef = FirebaseDatabase.getInstance().getReference("grocery");
                storage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://groceryshopjayshah.appspot.com/grocery");

                Query delQ = dbRef.orderByChild("gId").equalTo(gId);

                delQ.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for(DataSnapshot snap : snapshot.getChildren())
                        {
                            Grocery g1 = snap.getValue(Grocery.class);
                            snap.getRef().removeValue();  // Used to remove record from Realtime Database

                            storage = storage.child(gName);

                            storage.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(getApplicationContext(),gName+" HAS BEEN REMOVED SUCCESSFULLY",Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}
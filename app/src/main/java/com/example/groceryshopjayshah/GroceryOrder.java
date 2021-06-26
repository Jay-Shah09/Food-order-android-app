package com.example.groceryshopjayshah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GroceryOrder extends Activity {

    static RecyclerView myRecyclerView;
    RecyclerView.Adapter adapter;  // adapter is used to create only that much rows in the recycler view according to the number of grocery object.(dynamically)
    RecyclerView.LayoutManager layoutManager;
    Button btGroceryOrderConfirm;
    TextView tvGroceryOrderBill;
    DatabaseReference dbRef;
    ArrayList<Grocery> groceryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_order);  // this line shows that our current activity is connected to which xml file here groceryorder is connected to activity_grocery_order.

        btGroceryOrderConfirm = findViewById(R.id.btGroceryOrderConfirm);
        tvGroceryOrderBill = findViewById(R.id.tvGroceryOrderBill);

        myRecyclerView = findViewById(R.id.recyclerGroceryOrderList);
        myRecyclerView.setHasFixedSize(true);  //It prevents repeatation of same objects.

        layoutManager = new GridLayoutManager(getApplicationContext(),1); // spancount is used to set number of rows of groceryordercardview on recycler view of groceryorder.
        myRecyclerView.setLayoutManager(layoutManager);
        myRecyclerView.setItemAnimator(new DefaultItemAnimator());

        dbRef = FirebaseDatabase.getInstance().getReference("grocery");
        groceryList = new ArrayList<Grocery>();

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                groceryList.clear();

                for(DataSnapshot snap : snapshot.getChildren())
                {
                    Grocery g = snap.getValue(Grocery.class);
                    groceryList.add(g);
                }

                myRecyclerView.setItemViewCacheSize(groceryList.size());  //Exactly number of rows would be created as per object fetch of grocery

                adapter = new GroceryOrderAdapter(GroceryOrder.this,groceryList,tvGroceryOrderBill,btGroceryOrderConfirm);  // From here we will move to groceryorderadapter activity.
                myRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
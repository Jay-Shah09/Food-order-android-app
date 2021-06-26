package com.example.groceryshopjayshah;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminScreen extends Activity {
    Button btnAdScrShow,btnAdScrAdd,btnAdScrDelete,btnAdScrUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);

        btnAdScrShow=findViewById(R.id.btnAdScrShow);
        btnAdScrAdd=findViewById(R.id.btnAdScrAdd);
        btnAdScrDelete=findViewById(R.id.btnAdScrDelete);
        btnAdScrUpdate=findViewById(R.id.btnAdScrUpdate);

        btnAdScrShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(getApplicationContext(),UserScreen.class);
                startActivity(i1);
            }
        });

        btnAdScrAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(getApplicationContext(),AdminAddGroceryScreen.class);
                startActivity(i2);
            }
        });

        btnAdScrDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3 = new Intent(getApplicationContext(),AdminDeleteGroceryScreen.class);
                startActivity(i3);
            }
        });

        btnAdScrUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i4 = new Intent(getApplicationContext(),AdminUpdateGroceryScreen.class);
                startActivity(i4);
            }
        });
    }
}
package com.example.groceryshopjayshah;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class GroceryBill extends Activity {

    TextView tvGroceryGrandTotal,tvGroceryBillDate,tvGroceryBillTime;
    String grandTotal="500";
    ListView lvGroceryPurchased;
    Button btGroceryPayment;
    ArrayList<String> gNameList=new ArrayList<String>();
    ArrayList<String> gPriceList=new ArrayList<String>();
    ArrayList<String> gQtyList=new ArrayList<String>();
    ArrayList<String> gItemTotal=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_bill);

        Intent ii = getIntent();
        grandTotal = ii.getStringExtra("finalbill");
        gNameList = ii.getStringArrayListExtra("gname");
        gQtyList = ii.getStringArrayListExtra("gqty");
        gPriceList = ii.getStringArrayListExtra("gprice");
        gItemTotal = ii.getStringArrayListExtra("gitemtotal");

        tvGroceryBillDate = findViewById(R.id.tvGroceryBillDate);
        tvGroceryBillTime = findViewById(R.id.tvGroceryBillTime);
        btGroceryPayment = findViewById(R.id.btGroceryPayment);
        tvGroceryGrandTotal = findViewById(R.id.tvGroceryGrandTotal);
        lvGroceryPurchased = findViewById(R.id.lvGroceryPurchased);

        tvGroceryGrandTotal.setText("Rs. "+grandTotal+"/-");

        PaymentInfo payInfo = (PaymentInfo)getApplication();
        payInfo.amount = Float.parseFloat(grandTotal);

        GroceryBillAdapter generate = new GroceryBillAdapter(GroceryBill.this,R.layout.grocerybilllist,gNameList,gQtyList,gPriceList,gItemTotal,tvGroceryGrandTotal);

        lvGroceryPurchased.setAdapter(generate);

        btGroceryPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(getApplicationContext(),PaymentScreen.class);
                startActivity(ii);
            }
        });
    }
}
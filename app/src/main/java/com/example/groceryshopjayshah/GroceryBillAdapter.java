package com.example.groceryshopjayshah;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GroceryBillAdapter extends ArrayAdapter<String>
{
    Context cont;
    int resource;
    ArrayList gNameList,gQtyList,gPriceList,gItemTotal;
    TextView tvGroceryGrandTotal;

    GroceryBillAdapter(Context cont,int resource,ArrayList gNameList,ArrayList gQtyList,ArrayList gPriceList, ArrayList gitemTotal,TextView tvGroceryGrandTotal)
    {
        super(cont,resource,gNameList); // Passing gNameList decides how many rows will be created by adapter.

        this.cont = cont;
        this.resource=resource;
        this.gNameList = gNameList;
        this.gQtyList = gQtyList;
        this.gPriceList = gPriceList;
        this.gItemTotal  = gitemTotal;
        this.tvGroceryGrandTotal = tvGroceryGrandTotal;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflat = LayoutInflater.from(cont);  // It is used to connect non-activity with xml(grocerybilllist) file.
        View view = inflat.inflate(resource,null,false);

        TextView tName = view.findViewById(R.id.tvGroceryProductName);
        TextView tQty = view.findViewById(R.id.tvGroceryProductQty);
        TextView tPrice = view.findViewById(R.id.tvGroceryProductRate);
        TextView tTotal = view.findViewById(R.id.tvGroceryProductAmount);

        tName.setText(""+gNameList.get(position));
        tQty.setText(""+gQtyList.get(position));
        tPrice.setText(""+gPriceList.get(position));
        tTotal.setText("Rs. "+gItemTotal.get(position));

        return view;
    }
}

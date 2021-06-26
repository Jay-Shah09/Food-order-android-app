package com.example.groceryshopjayshah;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;

public class GroceryOrderAdapter extends RecyclerView.Adapter<GroceryOrderAdapter.MyViewHolder> // after reaching this line onCresteViewHolder method is called directly once.
{
    Context cont;  // Context means current application.  When we move from activity to non-activity then we can not use getApplicationContext(), to use current non-activity context is used.
    ArrayList<Grocery> dataset;
    TextView tvGroceryOrderBill;
    Button btGroceryOrderConfirm;
    int total[];
    int grandTotal = 0;
    int checkIndex[];   
    int totalNoOfGroceryBought = 0;
    ArrayList<String> gNameList = new ArrayList<String>();
    ArrayList<String> gQtyList = new ArrayList<String>();
    ArrayList<String> gPriceList = new ArrayList<String>();
    ArrayList<String> gTotalList = new ArrayList<String>();

    public static class MyViewHolder extends RecyclerView.ViewHolder  // this class is called for each object of grocery so in order to make single copy of this class for every call static is used.
    {
        ImageView groceryImage;
        TextView groceryName,groceryPrice;
        ElegantNumberButton groceryButton;

        MyViewHolder(View itemView)
        {
            super(itemView);

            groceryImage = itemView.findViewById(R.id.cardGroceyOrderImage);
            groceryName = itemView.findViewById(R.id.tvCardGroceryOrderName);
            groceryPrice = itemView.findViewById(R.id.tvCardGroceryOrderPrice);
            groceryButton = itemView.findViewById(R.id.elegantNumGroceryOrder);
        }
    }

    GroceryOrderAdapter(Context cont , ArrayList<Grocery> list, TextView tvGroceryOrderBill, Button btGroceryOrderConfirm)
    {
        this.cont = cont;
        this.dataset = list;  // groceryList and list are connected.
        this.btGroceryOrderConfirm = btGroceryOrderConfirm;
        this.tvGroceryOrderBill = tvGroceryOrderBill;

        checkIndex = new int[dataset.size()];
        total = new int[dataset.size()];

        for(int i=0;i<checkIndex.length;i++)
        {
            checkIndex[i] = -1;
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.groceryordercardview,parent,false);
        // It is used to connect any xml file with non-activity class in android.

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
        //By doing so our itemView is connected with grocerycardview.
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) // This method will be called no of times matches with the no getItemCount() returns.
    {
        ImageView groceryImage = holder.groceryImage;
        final TextView groceryName = holder.groceryName;
        TextView groceryPrice = holder.groceryPrice;
        ElegantNumberButton groceryButton = holder.groceryButton;
        // above lines are used to create unique copy for every row content.

        groceryName.setText(dataset.get(position).getgName());
        groceryPrice.setText("Rs. "+dataset.get(position).getPrice());

        Glide.with(cont)
                // Here Glide is used to get Image from storage dynamically.
                .load(dataset.get(position).getImageUri())
                .override(800,400)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(groceryImage);

        final int i = position; // we can not use int position (local variable) in inner class(onClickListener) .

        groceryButton.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(cont,"NAME : "+dataset.get(i).getgName(),Toast.LENGTH_LONG).show();

                int qty = Integer.parseInt(groceryButton.getNumber());

                if(qty==0)  // It will be executed when user shift stock to zero after buying grocery
                {

                    for(int i=0; i<checkIndex.length-1;i++)
                    {
                        if(checkIndex[i] == position)
                        {
                            for(int j=i;j<checkIndex.length-1;j++)
                            {
                                checkIndex[j] = checkIndex[j+1];
                                total[j] = total[j+1];

                                grandTotal = 0;
                                for(int r=0;r<gNameList.size();r++)
                                {
                                    grandTotal=grandTotal+total[r];
                                }
                                tvGroceryOrderBill.setText("Rs. "+grandTotal);
                            }
                            if(position == checkIndex.length-1)
                            {
                                checkIndex[position] = -1;
                                total[position] = 0;
                            }
                            totalNoOfGroceryBought--;
                            gNameList.remove(i);
                            gPriceList.remove(i);
                            gQtyList.remove(i);
                            gTotalList.remove(i);
                            break;
                        }
                    }
                }
                else
                {
                    // If user increase or decrease the qty of grocery.
                    int flag=0;
                    int exists=0;

                    for(exists=0;exists<checkIndex.length;exists++)  // loop is used to find grocery in checkIndex.
                    {
                        if(checkIndex[exists]==position)  // We found grocery in checkIndex ==> client has bought this grocery.
                        {
                            flag=1;
                            break;
                        }
                    }

                    if(flag==0)  // User has not bought grocery, he is buying now.
                    {
                        checkIndex[totalNoOfGroceryBought] = position;
                        total[totalNoOfGroceryBought++] = dataset.get(position).getPrice()*Integer.parseInt(groceryButton.getNumber());

                        gNameList.add(dataset.get(position).getgName()); // here all data of database is in dataset.
                        gPriceList.add(""+dataset.get(position).getPrice());
                        gQtyList.add(""+dataset.get(position).getStock());
                        gTotalList.add(""+dataset.get(position).getPrice()*Integer.parseInt(groceryButton.getNumber()));
                    }
                    else   // programing for grocery already bought with capacity.
                    {
                        gQtyList.set(exists,""+groceryButton.getNumber());
                        gTotalList.set(exists,""+dataset.get(position).getPrice()*Integer.parseInt(groceryButton.getNumber()));
                        total[exists] = dataset.get(position).getPrice()*Integer.parseInt(groceryButton.getNumber());
                    }

                    String str="";
                    for(int r=0;r<gNameList.size();r++)
                    {
                        str=str+gNameList.get(r)+" , "+gQtyList.get(r)+" , "+gPriceList.get(r)+"\n";
                    }

                    Toast.makeText(cont,str,Toast.LENGTH_LONG).show();

                    grandTotal = 0;

                    for(int r=0;r<gNameList.size();r++)
                    {
                        grandTotal=grandTotal+total[r];
                    }

                    tvGroceryOrderBill.setText("Rs. "+grandTotal);
                }

            }
        });

        btGroceryOrderConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(cont,GroceryBill.class);

                ii.putExtra("finalbill",""+grandTotal);

                ii.putStringArrayListExtra("gname",gNameList);
                ii.putStringArrayListExtra("gqty",gQtyList);
                ii.putStringArrayListExtra("gprice",gPriceList);
                ii.putStringArrayListExtra("gitemtotal",gTotalList);

                cont.startActivity(ii);
            }
        });

    }

    @Override
    public int getItemCount()  //It decides how many objects of grocery should be displayed on recyclerView
    {
        return dataset.size();  // According to this, it generates rows for recyclerView.
    }



}

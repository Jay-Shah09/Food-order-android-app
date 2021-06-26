package com.example.groceryshopjayshah;

import android.app.Application;

public class PaymentInfo extends Application  // Application class/global class gets instantiated by its own so in order to use them in other activity there is no need to make object.
{
    public String name;
    public String address;
    public String mobile;
    public String mail;
    public float amount;
}

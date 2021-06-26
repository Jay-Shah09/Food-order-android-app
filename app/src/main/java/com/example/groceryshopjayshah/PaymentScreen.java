package com.example.groceryshopjayshah;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class PaymentScreen extends Activity implements PaymentResultListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_screen);

        Checkout check = new Checkout();

        check.setKeyID("rzp_test_IUtkjoH4n0u3wr");

        JSONObject json = new JSONObject();

        PaymentInfo payInfo = (PaymentInfo)getApplication();

        try
        {
            json.put("Name",payInfo.name);
            json.put("description","THANK YOU FOR YOUR PURCHASE");
            json.put("theme.color","#0093DD");
            json.put("currency","INR");
            json.put("Amount",payInfo.amount*100);
            json.put("prefill.contact",payInfo.mobile);
            json.put("prefill.email",payInfo.mail);

            check.open(this,json);
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(),"PAYMENT ERROR : "+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPaymentSuccess(String s)
    {
        Toast.makeText(getApplicationContext(),"PAYMENT SUCCESSFULLY DONE : "+s,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPaymentError(int i, String s)
    {
//        Toast.makeText(getApplicationContext(),"",)
        finish();
    }
}
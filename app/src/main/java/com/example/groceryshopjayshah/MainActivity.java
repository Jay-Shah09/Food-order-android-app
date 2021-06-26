package com.example.groceryshopjayshah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends Activity {
    EditText emailet,passet;
    Button loginbtn,signupbtn;
    String email,pass;
    FirebaseAuth fAuth;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailet=findViewById(R.id.emailet);
        passet=findViewById(R.id.passet);
        loginbtn=findViewById(R.id.loginbtn);
        signupbtn=findViewById(R.id.signupbtn);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Registration.class);
                startActivity(intent);
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=emailet.getText().toString();
                pass=passet.getText().toString();

                if(email.equals("admin") && pass.equals("food"))
                {
                    Toast.makeText(getApplicationContext(),"WEL-COME ADMIN",Toast.LENGTH_LONG).show();

                    Intent i1=new Intent(getApplicationContext(),AdminScreen.class);
                    startActivity(i1);
                }
                else
                {
                        fAuth = FirebaseAuth.getInstance();

                        fAuth.signInWithEmailAndPassword(email, pass)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful())
                                        {
                                            Toast.makeText(getApplicationContext(), "AUTHORISED USER", Toast.LENGTH_LONG).show();

                                            dbRef = FirebaseDatabase.getInstance().getReference("User");

                                            Query findQ = dbRef.orderByChild("email").equalTo(email);  // To locate required record.

                                            findQ.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot)
                                                {
                                                    User u1=null;
                                                    for(DataSnapshot snap : snapshot.getChildren())
                                                    {
                                                        u1 = snap.getValue(User.class);
                                                    }
                                                    PaymentInfo payInfo = (PaymentInfo)getApplication();

                                                    payInfo.name = u1.getName();
                                                    payInfo.address = u1.getAddress();
                                                    payInfo.mobile = u1.getMobile();
                                                    payInfo.mail = u1.getEmail();
                                                    payInfo.amount = 0;
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });

                                            Intent ii = new Intent(getApplicationContext(),GroceryOrder.class);
                                            startActivity(ii);
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), "LOGIN FAIL", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                }
            }
        });
    }
}
package com.example.groceryshopjayshah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends Activity {

    EditText etRegEmail,etRegPass,etName,etAddress,etPhoneNo;
    Button btnReg,btnCancel;
    String uid,email,pass,name,address,phone;
    int flag=0;

    FirebaseAuth fAuth;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPass = findViewById(R.id.etRegPass);
        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etPhoneNo = findViewById(R.id.etPhoneNo);

        btnReg = findViewById(R.id.btnReg);
        btnCancel = findViewById(R.id.btnCancel);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = etRegEmail.getText().toString();
                pass = etRegPass.getText().toString();
                name = etName.getText().toString();
                address = etAddress.getText().toString();
                phone = etPhoneNo.getText().toString();

                String expName="^[A-Z a-z]{3,15}$";
                Pattern patName= Pattern.compile(expName);
                Matcher matName=patName.matcher(name);

                String expMail="^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
                Pattern patMail=Pattern.compile(expMail);
                Matcher matMail=patMail.matcher(email);

                String expPhone = "^[6-9]{1}[0-9]{9}";   // here [0-9]{9} means the size of phone no must be 9 and also from 0 to 9 digits , [6-9]{1} means the first digit must be from 6 to 9. If [6-9]{1} is written after [0-9]{9} then the condition will be the last digit must be from 6 to 9.
                Pattern patPhone = Pattern.compile(expPhone);
                Matcher matPhone = patPhone.matcher(phone);

                if(matName.matches()==false)
                {
                    Toast.makeText(getApplicationContext(),"EMPTY NAME OR INVALID INPUT",Toast.LENGTH_LONG).show();
                    etName.requestFocus();
                    flag=1;
                }
                if(matMail.matches()==false)
                {
                    Toast.makeText(getApplicationContext(),"EMPTY MAIL OR INVALID INPUT",Toast.LENGTH_LONG).show();
                    etRegEmail.requestFocus();
                    flag=1;
                }
                if(matPhone.matches()==false)
                {
                    Toast.makeText(getApplicationContext(),"EMPTY MOBILE OR INVALID INPUT",Toast.LENGTH_LONG).show();
                    etPhoneNo.requestFocus();
                    flag=1;
                }
                if(address.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"EMPTY ADDRESS",Toast.LENGTH_LONG).show();
                    etAddress.requestFocus();
                    flag=1;
                }
                if(pass.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"EMPTY PASSWORD",Toast.LENGTH_LONG).show();
                    etRegPass.requestFocus();
                    flag=1;
                }
                if(flag==0)
                {
                    fAuth = FirebaseAuth.getInstance();   //It is used to connect fAuth variable with Authentication feature of firebase

                    fAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        dbRef = FirebaseDatabase.getInstance().getReference("User");

                                        uid = dbRef.push().getKey();

                                        User user = new User(uid, email, pass, name, address, phone);
                                        dbRef.child(uid).setValue(user);

                                        Toast.makeText(getApplicationContext(), "USER REGISTERED SUCCESSFULLY", Toast.LENGTH_LONG).show();

                                        Intent i1 = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(i1);
                                    }

                                }
                            });
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etRegEmail.setText("");
                etRegPass.setText("");
                etName.setText("");
                etAddress.setText("");
                etPhoneNo.setText("");

                Intent i2 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i2);
            }
        });
    }
}
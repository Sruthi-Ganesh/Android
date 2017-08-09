package com.example.android.miwok;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.android.miwok.R.id.address;
import static com.example.android.miwok.R.id.emailid;

/**
 * Created by SruthiGanesh on 4/21/2017.
 */

public class placeOrder extends AppCompatActivity {
    String firstName;
    String lastName,emailId,postalAddress;
    TextView firstname,lastname,emailid,address;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.orderplacement);
        firstname = (TextView) findViewById(R.id.nameofperson);
         lastname = (TextView) findViewById(R.id.lastnameofperson);
        emailid = (TextView) findViewById(R.id.emailid);
         address = (TextView) findViewById(R.id.address);
        firstName = firstname.getText().toString();
        lastName = lastname.getText().toString();
        emailId = emailid.getText().toString();
        postalAddress = address.getText().toString();

        Toast.makeText(getApplicationContext(),"first name " +firstName,Toast.LENGTH_SHORT).show();
    }

    public void placeOrders(View v)
    {

        firstName = firstname.getText().toString();
        lastName = lastname.getText().toString();
        emailId = emailid.getText().toString();
        postalAddress = address.getText().toString();
        Toast.makeText(getApplicationContext(),"first name " + firstName + lastName,Toast.LENGTH_SHORT).show();

        Intent i = new Intent(placeOrder.this,purchaseproduct.class);
        i.putExtra("FirstName",firstName);
        i.putExtra("LastName",lastName);
        i.putExtra("Email",emailId);
        i.putExtra("Address",postalAddress);
        startActivity(i);
    }


}
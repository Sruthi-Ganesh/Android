package com.example.android.miwok;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.android.miwok.R.id.quantity;

/**
 * Created by SruthiGanesh on 4/21/2017.
 */

public class purchaseproduct extends AppCompatActivity {
String firstName,lastName,address,productName,quantity,mprice,mname,emailId;
    int totalPrice,resource;
    TextView t1,t2,t3,t4,t5,t6;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.purchaseproduct);
        firstName=getIntent().getStringExtra("FirstName");
        lastName=getIntent().getStringExtra("LastName");
        address=getIntent().getStringExtra("Address");
        emailId=getIntent().getStringExtra("Email Id");
        t1=(TextView)findViewById(R.id.textView1);
        t2=(TextView)findViewById(R.id.textView2);
        t3=(TextView)findViewById(R.id.textView3);
        t4=(TextView)findViewById(R.id.textView4);



        //t5.setText(quantity);
        //t6.setText(totalPrice);
        SharedPreferences shap = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mname = shap.getString("name", "No name defined");
        mprice = shap.getString("price", "null");
        emailId=shap.getString("emailId",null);
        resource=shap.getInt("resource",0);//0 is the default value. */
        quantity = shap.getString("quantity","null");

        int quant = Integer.parseInt(quantity);
        int price = Integer.parseInt(mprice);
        totalPrice = quant*price;

        t1.setText("Name:"  + firstName);
        t2.setText(lastName);
        t3.setText("Address : " + address);
        t4.setText("Product "+mname + "with $ " + quant*price);



        Log.i("Send email", "");

        String[] TO = {emailId};
        String[] CC = {"Order"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your order at Greekart is successful");
        emailIntent.putExtra(Intent.EXTRA_TEXT, createSummary());

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(),"There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
    public String createSummary()
 {
    String text = "Hi " + firstName;
    text = text + "Your order is getting shipped to " +address;
     text = text + "With total Price " +totalPrice;

     return text;
}
}
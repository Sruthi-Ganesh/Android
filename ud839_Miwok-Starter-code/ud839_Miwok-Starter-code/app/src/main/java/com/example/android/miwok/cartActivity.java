package com.example.android.miwok;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SruthiGanesh on 4/17/2017.
 */

public class cartActivity extends AppCompatActivity {
    String mname,mprice;
    int resource;
    String quantity;
    int totalPrice;
    //CartAdapter adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cartactivity);

        SharedPreferences shap = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        mname = shap.getString("name", "No name defined");//"No name defined" is the default value.
        mprice = shap.getString("price", "null");
        resource=shap.getInt("resource",0);//0 is the default value. */
        quantity = shap.getString("quantity","null");

        int quant = Integer.parseInt(quantity);
        int price = Integer.parseInt(mprice);
        totalPrice = quant*price;



       Toast.makeText(getApplicationContext(), "the stored value is" + mname + " " + totalPrice,Toast.LENGTH_SHORT  ).show();

        TextView t1 = (TextView) findViewById(R.id.nameofproduct);
        TextView t2 = (TextView) findViewById(R.id.priceofproduct);
        ImageView img = (ImageView) findViewById(R.id.imageofproduct);
        TextView t3 = (TextView) findViewById(R.id.quantity);
        TextView t4 = (TextView) findViewById(R.id.priceforquantity);

        //adapter = new CartAdapter(this, word,R.color.category_phrases);

       // ListView listView = (ListView)findViewById(R.id.list);
        //listView.setAdapter(adapter);

        t1.setText(mname);
        t2.setText("$"+ mprice);
        img.setImageResource(resource);
         t3.setText(quantity + "kg");
       t4.setText("$"+totalPrice);


    }
    public String defaultName()
    {
        return mname;
    }

    public String defaultPrice()
    {
        return mprice;
    }

    public int resourceId()
    {
        return resource;
    }

    public void placeOrder(View v)
    {

Intent i = new Intent(cartActivity.this,placeOrder.class);
        startActivity(i);
    }
}

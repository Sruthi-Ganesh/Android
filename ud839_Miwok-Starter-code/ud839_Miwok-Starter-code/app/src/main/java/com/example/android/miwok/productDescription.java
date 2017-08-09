package com.example.android.miwok;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.UserDictionary;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.value;
import static com.example.android.miwok.R.id.numberofproduct;
import static com.example.android.miwok.R.id.price;

/**
 * Created by SruthiGanesh on 4/13/2017.
 */

public class productDescription extends AppCompatActivity {
    public static final String MY_PREFS_NAME = "MyPrefsFile";
String sname,sprice;
    int resource;
    int numberofproduct = 1;
    TextView quantity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productdesc);
        sname = getIntent().getStringExtra("Name");
        sprice = getIntent().getStringExtra("Price");
         resource = getIntent().getIntExtra("Image",0);
        TextView name = (TextView) findViewById(R.id.name);
        TextView price =(TextView) findViewById(R.id.price);
        ImageView img = (ImageView) findViewById(R.id.imageproduct) ;
         quantity = (TextView) findViewById(R.id.numberofproduct);
        name.setText(sname);
        price.setText("Price $"+sprice + "  Offer Valid");
        img.setImageResource(resource);
Intent i = new Intent();
        Word cont = (Word) i.getParcelableExtra("obj");


//Toast.makeText(productDescription.this,"" + cont.getDefaultTranslation(),Toast.LENGTH_SHORT);




    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        return true;
    }

    public void increment(View view)
    {
        numberofproduct=numberofproduct+1;

        if(numberofproduct>=100)
        {
            Toast.makeText(productDescription.this,"Number of products cannot be more than 99",Toast.LENGTH_SHORT).show();
            numberofproduct=99;
        }
        displayQuantity(numberofproduct);

    }

    public void decrement(View view)
    {
        numberofproduct=numberofproduct-1;
        if(numberofproduct<=0)
        {
            Toast.makeText(productDescription.this,"Number of products cannot be less than 1",Toast.LENGTH_SHORT).show();
            numberofproduct=1;
        }
        displayQuantity(numberofproduct);
    }
    private void displayQuantity(int number) {

        TextView quantityTextView = (TextView) findViewById(
                R.id.numberofproduct);
        quantityTextView.setText(""+number);
    }

    public void buyNow(View view)
    {
Intent i = new Intent(productDescription.this,cartActivity.class);
        startActivity(i);



    }
    public void addToCart(View view)
    {
        //wait until 3 2 1...
        int quant = Integer.parseInt(quantity.getText().toString());
        int price = Integer.parseInt(sprice);


        SharedPreferences sharp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharp.edit();
        editor.putString("name", sname);
        editor.putString("price", sprice);
        editor.putInt("resource",resource);
        editor.putString("quantity",quantity.getText().toString());

        editor.apply();





        Toast.makeText(getApplicationContext(), "added" ,Toast.LENGTH_SHORT  ).show();
        SharedPreferences shap = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String name="";
        String idName="";

        name = shap.getString("name", "No name defined");//"No name defined" is the default value.
        idName = shap.getString("idName", "null"); //0 is the default value.


        Toast.makeText(getApplicationContext(), "the stored value is" + name + " id " +idName + quant*price ,Toast.LENGTH_SHORT  ).show();


    }

    }

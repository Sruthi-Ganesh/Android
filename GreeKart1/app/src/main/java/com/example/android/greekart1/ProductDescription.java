package com.example.android.greekart1;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import com.example.android.greekart1.data.ItemContract.ItemEntry;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.greekart1.data.ItemHelper;

import java.util.Set;

import static android.R.attr.value;
import static android.content.ContentUris.parseId;
import static android.location.Geocoder.isPresent;

/**
 * Created by SruthiGanesh on 6/5/2017.
 */

public class ProductDescription extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    Uri currentUri=null;
    ImageView imageView;
    private int addToCart=0;
    TextView textName,outOfStockText;
    ItemHelper mDbHelper;
    SQLiteDatabase database;
    TextView  textPrice,textQuantity;
    private int ITEM_LOADER=0;
    private long id;
    private int quantity,getQuantity;
    private int noOfItemQuantities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_description);
        Intent i = getIntent();
        getQuantity = i.getIntExtra("Quantity",0);
        currentUri = i.getData();
        Log.v("Product Description",currentUri + "");

         imageView = (ImageView) findViewById(R.id.image_product_desc);
         textName = (TextView) findViewById(R.id.text_product_desc_name);
          textPrice = (TextView) findViewById(R.id.text_product_desc_price);
          textQuantity = (TextView) findViewById(R.id.editTextQuantity);
        outOfStockText = (TextView) findViewById(R.id.outOfStock);
        getLoaderManager().initLoader(ITEM_LOADER,null,this);
         id = ContentUris.parseId(currentUri);
        Log.v("Product Description",String.valueOf(id));
        mDbHelper = new ItemHelper(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity);
        toolbar.setTitle("Product Description");
        toolbar.inflateMenu(R.menu.menu_bar);
        toolbar.setNavigationIcon(R.drawable.icons_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.action_cart:
                        showCart();
                        return true;
                    case R.id.search:
                        search();
                        return true;


                }
                return ProductDescription.super.onOptionsItemSelected(item);
            }






        });



    }
    public void showCart()
    {
        Intent intent = new Intent(getApplicationContext(),CheckOutActivity.class);
        startActivity(intent);
    }


    public void search()
    {
        Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
        startActivity(intent);
    }


    public void addToCart(String name, Double price , String image , int quantity)
    {
     /*SharedPreference sharedPreference = new SharedPreference(getApplicationContext());
        sharedPreference.putSharedPreference(id);
        Set<String> IdSet = sharedPreference.getSharedPreference();
        Log.v("Product Description",IdSet.toString());*/
     if(addToCart==1)
     {
         return;
     }
        addToCart = 1;
        ContentValues values = new ContentValues();
        values.put(ItemEntry.COLUMN_ITEM_NAME,name);
         noOfItemQuantities = Integer.parseInt(textQuantity.getText().toString());
        if(noOfItemQuantities>quantity ) {
            addToCart=2;
            outOfStockText.setText("Out of Stock");
            return;
        }
        double totalPrice = price * noOfItemQuantities;
        values.put(ItemEntry.COLUMN_ITEM_PRICE,totalPrice);
        values.put(ItemEntry.COLUMN_ITEM_IMAGE,image);
        values.put(ItemEntry.COLUMN_ITEM_QUANTITY,noOfItemQuantities);
        values.put(ItemEntry.URI,String.valueOf(currentUri));
        database = mDbHelper.getWritableDatabase();
        mDbHelper.onCreateDatabase(database,ItemEntry.TABLE_NAME_CART);
        Log.v("Item Adapter",currentUri + " ");

        Uri mNewUri;

        mNewUri = getContentResolver().insert(ItemEntry.CONTENT_CART_URI,values);
        if(mNewUri!=null)
        {
            Toast.makeText(this,"Added to cart: Product: "+name , Toast.LENGTH_SHORT).show();
            Log.v("Product Description","Added to cart" + name);
        }



    }

    public boolean isNotPresent(String name)
    {
        String[] selection = {ItemEntry.COLUMN_ITEM_NAME , ItemEntry._ID , ItemEntry.COLUMN_ITEM_PRICE , ItemEntry.COLUMN_ITEM_QUANTITY , ItemEntry.COLUMN_ITEM_IMAGE};
        database = mDbHelper.getWritableDatabase();

        mDbHelper.onCreateDatabase(database,ItemEntry.TABLE_NAME_CART);
        Cursor cursor = getContentResolver().query(ItemEntry.CONTENT_CART_URI,selection,null,null,null);

        if(cursor.moveToFirst())
        {
            do
            {

                String nameOfProduct = cursor.getString(cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_NAME));
                if(nameOfProduct.equals(name))
                {

                    int noOfItemQuantities = cursor.getInt(cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_QUANTITY));
                    int noOfItemQuantitiy  = Integer.parseInt(textQuantity.getText().toString());
                    Log.v("Product Description",noOfItemQuantitiy + " " + noOfItemQuantities);
                    if(noOfItemQuantitiy!=noOfItemQuantities)
                    {

                        noOfItemQuantities=noOfItemQuantitiy;
                        ContentValues values = new ContentValues();
                        Double priceOfProduct = cursor.getDouble(cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_PRICE));
                        int quantity = cursor.getInt(cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_QUANTITY));
                        int id1 = cursor.getInt(cursor.getColumnIndex(ItemEntry._ID));
                        String image = cursor.getString(cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_IMAGE));

                        Double price = (priceOfProduct/quantity) * noOfItemQuantities;
                        values.put(ItemEntry.COLUMN_ITEM_NAME,name);
                        values.put(ItemEntry.COLUMN_ITEM_PRICE,price);
                        values.put(ItemEntry.COLUMN_ITEM_IMAGE,image);
                        values.put(ItemEntry.COLUMN_ITEM_QUANTITY,noOfItemQuantities);
                        Uri uri = ContentUris.withAppendedId(ItemEntry.CONTENT_CART_URI, (long) id1);

                        try {


                            int noOfRowsUpdated = getContentResolver().update(uri, values, null, null);
                            if (noOfRowsUpdated != -1) {
                                Toast.makeText(getApplicationContext(), "Cart Updated", Toast.LENGTH_LONG).show();

                            }
                        }
                        catch (Exception E)
                        {
                            Log.e("Product Description" ,  " " + E);
                        }

                    }
                    return false;



                }


            }while(cursor.moveToNext());
        }

        return true;
    }
public boolean isNotPresentWishList(String name)
{
    String[] selection = {ItemEntry.COLUMN_ITEM_NAME , ItemEntry.COLUMN_ITEM_PRICE , ItemEntry.COLUMN_ITEM_QUANTITY , ItemEntry.COLUMN_ITEM_IMAGE};
    database = mDbHelper.getWritableDatabase();
    String tableName = "";



    mDbHelper.onCreateDatabase(database,ItemEntry.TABLE_NAME_WISHLIST);
    Cursor cursor = getContentResolver().query(ItemEntry.CONTENT_WISHLIST_URI,selection,null,null,null);

    if(cursor.moveToFirst())
    {
        do
        {

            String nameOfProduct = cursor.getString(cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_NAME));
            if(nameOfProduct.equals(name))
            {
                return false;



            }


        }while(cursor.moveToNext());
    }

    return true;
}
    public void addToDatabase(String name, Double price, String image) {
        ContentValues values = new ContentValues();
        values.put(ItemEntry.COLUMN_ITEM_NAME, name);
        values.put(ItemEntry.COLUMN_ITEM_PRICE, price);
        values.put(ItemEntry.URI,String.valueOf(currentUri));
        values.put(ItemEntry.COLUMN_ITEM_IMAGE, image);
        values.put(ItemEntry.COLUMN_ITEM_QUANTITY, 1);
        Log.v("Item Adapter",currentUri + " ");

        mDbHelper = new ItemHelper(getApplicationContext());
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        mDbHelper.onCreateDatabase(database, ItemEntry.TABLE_NAME_WISHLIST);
        Log.v("Item Adapter", ItemEntry.TABLE_NAME_WISHLIST + ItemEntry.CONTENT_WISHLIST_URI);

        Uri mNewUri;


            mNewUri = getContentResolver().insert(ItemEntry.CONTENT_WISHLIST_URI, values);
            if (mNewUri != null) {

                Toast.makeText(this,"Added to Wishlist: Product: "+name , Toast.LENGTH_SHORT).show();

                Log.v("Product Description", "Added to cart" + name);
            }
        }



@Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        String projection[] = { ItemEntry._ID,
                ItemEntry.COLUMN_ITEM_NAME,
                ItemEntry.COLUMN_ITEM_PRICE,
                ItemEntry.COLUMN_ITEM_IMAGE,
                ItemEntry.COLUMN_ITEM_QUANTITY};





    return new CursorLoader(this,currentUri,projection,null,null,null);




    }



    @Override
    public void onLoadFinished(Loader<Cursor> loader, final Cursor cursor) {
        final String name,image;
        final Double price;


        if(currentUri!=null) {
            if (cursor.moveToFirst()) {
                int nameColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_NAME);
                int priceColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_PRICE);
                int imageColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_IMAGE);
                int quantityColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_QUANTITY);

                if(getQuantity!=0)
                {
                    TextView ediText = (TextView) findViewById(R.id.editTextQuantity);
                    ediText.setText(String.valueOf(getQuantity));
                }


                // Extract out the value from the Cursor for the given column index
                name = cursor.getString(nameColumnIndex);
                 price = cursor.getDouble(priceColumnIndex);
                 image = cursor.getString(imageColumnIndex);
                 quantity = cursor.getInt(quantityColumnIndex);

                int resourceId = new ImageActivity(image,getApplicationContext()).getImageId();
                textName.setText(name);
                textPrice.setText("Rs. " + String.valueOf(price));
                imageView.setImageResource(resourceId);
                 final int addTocartValue=0;




                final Button addToCartButton = (Button) findViewById(R.id.addtoccart);
                addToCartButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final boolean  value =isNotPresent(name);

                        if(value) {

                            addToCart(name, price, image, quantity);
                        }
                        else
                        {
                            Toast.makeText(getBaseContext(),"Item Already in Cart",Toast.LENGTH_SHORT).show();
                        }



                    }
                });
                final Button addToCartWish = (Button) findViewById(R.id.addtowishlist);
                addToCartWish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final boolean  value =isNotPresentWishList(name);

                        if(value) {

                            addToDatabase(name,price,image);
                        }
                        else
                        {
                            Toast.makeText(getBaseContext(),"Item Already in WishList",Toast.LENGTH_SHORT).show();
                        }



                    }
                });

                Button BuyNowButton = (Button) findViewById(R.id.buyNow);
                BuyNowButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final boolean  value =isNotPresent(name);
                        if (addTocartValue == 0 && value) {
                            addToCart(name, price, image, quantity);
                        }
                        if (addToCart != 2 || !value) {
                            Intent intent = new Intent(ProductDescription.this, CheckOutActivity.class);
                            startActivity(intent);
                        }
                    }
                });

                }


            }


        }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        textName.setText("");
        textPrice.setText("");
        imageView.setImageResource(0);


    }

    public void increment(View v)
    {
        int oldQuantity = Integer.parseInt(textQuantity.getText().toString());
        int newQuantity = oldQuantity+1;
        if(newQuantity>quantity) {
            Toast.makeText(this, "Out of Stock", Toast.LENGTH_SHORT).show();
            TextView outOfStockText = (TextView) findViewById(R.id.outOfStock);
            outOfStockText.setText("Out Of Stock");
            return;
        }
        textQuantity.setText(String.valueOf(newQuantity));
    }
    public void decrement(View v)
    {
        int oldQuantity = Integer.parseInt(textQuantity.getText().toString());
        int newQuantity = oldQuantity-1;
        if(newQuantity<1)
        {
            Toast.makeText(this,"Quantity cannot be lesser than 1",Toast.LENGTH_SHORT);
            return;
        }
        textQuantity.setText(String.valueOf(newQuantity));
    }
}



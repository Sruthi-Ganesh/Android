package com.example.android.greekart1;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.greekart1.data.ItemContract.ItemEntry;
import com.example.android.greekart1.data.ItemHelper;

import java.util.ArrayList;

import static android.R.attr.id;

/**
 * Created by SruthiGanesh on 6/8/2017.
 */

public class CheckOutActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,ItemCursorAdapter.CostActivity {
    ItemHelper mDbHelper;
    ItemCursorAdapter itemCursorAdapter;
    private ListView ItemListView;
    private TextView textView;
    private Button checkoutButton;

    private static final int ITEM_LOADER = 0;
    private Uri currentUri = null;
    double totalPrice =0.0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_checkout);
       ItemListView = (ListView) findViewById(R.id.list_view_item2);


        mDbHelper = new ItemHelper(getApplicationContext());
        Log.v("Check out", getClass().getSimpleName());
        itemCursorAdapter = new ItemCursorAdapter(this, null,getClass().getSimpleName());
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        mDbHelper.onCreateDatabase(database,ItemEntry.TABLE_NAME_CART);

        View empty_view = findViewById(R.id.empty_view);
        ItemListView.setEmptyView(empty_view);
        ItemListView.setAdapter(itemCursorAdapter);
        getLoaderManager().initLoader(ITEM_LOADER, null, this);
        textView = (TextView) findViewById(R.id.total_price_text);










        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity);
        toolbar.setTitle("Your Cart");
        toolbar.inflateMenu(R.menu.cart_bar);

        toolbar.setNavigationIcon(R.drawable.icons_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_cart:
                        deleteCart();
                        return true;
                    case R.id.search:
                        search();
                        return true;


                }
                return CheckOutActivity.super.onOptionsItemSelected(item);
            }


        });



    }
    public void calculatecosts(Cursor cursor)
    {
        double totalPrice = 0.00;


        if (cursor.moveToFirst())
        {
            do
            {
                double price = cursor.getDouble(cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_PRICE));
                int quantity = cursor.getInt(cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_QUANTITY));
                totalPrice = totalPrice + (price * quantity);
            }while(cursor.moveToNext());

        }
       textView.setText(String.valueOf(totalPrice));



    }



    public void openProduct(int id,Cursor cursor)
    {
        if(cursor.moveToFirst())
        {
            do
            {
                int idProduct =  cursor.getInt(cursor.getColumnIndex(ItemEntry._ID));



                if(idProduct == id)
                {

                    String uri = cursor.getString(cursor.getColumnIndex(ItemEntry.URI));
                    int quantity = cursor.getInt(cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_QUANTITY));
                    Intent intent = new Intent(CheckOutActivity.this,ProductDescription.class);

                    Log.v("BREAK FAST ACTIVITY",  uri + "");

                    intent.setData(Uri.parse(uri));
                    intent.putExtra("Quantity",quantity);

                    startActivity(intent);
                }


            }while(cursor.moveToNext());
        }
    }


    public void deleteCart()
    {
        new AlertDialog.Builder(CheckOutActivity.this)
                .setTitle("Delete?")
                .setMessage("Are you want to delete all the items in the cart? ")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface arg0, int arg1) {
                        int noofRowsDeleted = getContentResolver().delete(ItemEntry.CONTENT_CART_URI,null,null);
                        if(noofRowsDeleted>0)
                        {
                            Toast.makeText(CheckOutActivity.this,"Cart is cleared",Toast.LENGTH_SHORT).show();
                        }

                        textView.setText("Rs. 0.00");
                    }
                }).create().show();


    }

    public void FinalCart(Cursor cursor)
    {
     Intent intent = new Intent(getApplicationContext(),FinalCheckOutActivity.class);
        ArrayList<String> products = new ArrayList<>();
        ArrayList<Integer> quantities = new ArrayList<>();
        ArrayList<String> prices = new ArrayList<>();
        ArrayList<String> uris = new ArrayList<>();


        if (cursor.moveToFirst())
        {
            do
            {
                int quantity = cursor.getInt(cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_QUANTITY));
                String product = cursor.getString(cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_NAME));
                Double price = cursor.getDouble(cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_PRICE));
                String uri = cursor.getString(cursor.getColumnIndex(ItemEntry.URI));
                products.add(product);
                quantities.add(quantity);
                prices.add(price.toString());
                uris.add(uri);
            }while(cursor.moveToNext());
            intent.putExtra("Product",products);

            intent.putExtra("Quantity",quantities);
            intent.putExtra("Price",prices);

            intent.putExtra("Total Price",totalPrice);
            intent.putExtra("Uri",uris);
            startActivity(intent);

        }
        else{

           Toast.makeText(this,"Cart is empty",Toast.LENGTH_SHORT).show();
            return;

        }




    }


    public void search()
    {
        Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String projection[] = {ItemEntry._ID,ItemEntry.COLUMN_ITEM_NAME,ItemEntry.COLUMN_ITEM_PRICE,ItemEntry.COLUMN_ITEM_IMAGE,ItemEntry.COLUMN_ITEM_QUANTITY,ItemEntry.URI};

        return new CursorLoader(this,ItemEntry.CONTENT_CART_URI,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader,final Cursor cursor) {

        ItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                openProduct((int) id,cursor);



            }
        });



        checkoutButton = (Button) findViewById(R.id.proceedtocheckout);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FinalCart(cursor);

            }
        });
        calculatecosts(cursor);






        itemCursorAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        itemCursorAdapter.swapCursor(null);

    }

    @Override
    public void setCost(Double totalPrice) {

        textView.setText(totalPrice.toString());
    }
}

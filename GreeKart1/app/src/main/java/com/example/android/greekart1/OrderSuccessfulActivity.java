package com.example.android.greekart1;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.android.greekart1.data.ItemContract.ItemEntry;
import com.example.android.greekart1.data.ItemHelper;

/**
 * Created by SruthiGanesh on 7/30/2017.
 */

public class OrderSuccessfulActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    ItemCursorAdapter itemCursorAdapter;
    private static final int ITEM_LOADER = 0;
    private String userID;
    ItemHelper mDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_successful);
        itemCursorAdapter = new ItemCursorAdapter(this, null,getClass().getSimpleName());
        mDbHelper = new ItemHelper(getApplicationContext());
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        mDbHelper.onCreateDatabaseOrder(database,ItemEntry.TABLE_NAME_ORDERS);
        userID = getIntent().getStringExtra("UserId");
        ListView ItemListView = (ListView) findViewById(R.id.list_view_successful);
        ItemListView.setAdapter(itemCursorAdapter);
        getLoaderManager().initLoader(ITEM_LOADER, null, this);
    }
    @Override
public void onDestroy()
    {
        super.onDestroy();


        Cursor cursor = getContentResolver().query(ItemEntry.CONTENT_CART_URI,null,null,null,null);
        ContentValues values = new ContentValues();
        int i =0;

        if (cursor.moveToFirst()) {
            do {
                {

                    int quantity = cursor.getInt(cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_QUANTITY));
                    String image = cursor.getString(cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_IMAGE));
                    String name = cursor.getString(cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_NAME));
                    Double price = cursor.getDouble(cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_PRICE));
                    String currentUri = cursor.getString(cursor.getColumnIndex(ItemEntry.URI));


                    values.put(ItemEntry.COLUMN_ITEM_PRICE, price);
                    values.put(ItemEntry.COLUMN_ITEM_NAME, name);
                    values.put(ItemEntry.COLUMN_ITEM_IMAGE, image);
                    values.put(ItemEntry.USERID,userID);
                    values.put(ItemEntry.COLUMN_ITEM_QUANTITY, quantity);
                    values.put(ItemEntry.URI, currentUri);
                    values.put(ItemEntry.PRODUCT_NAME,"product:"+ i);
                    Uri mNewUri = getContentResolver().insert(ItemEntry.CONTENT_ORDERS_URI,values);
                    if(mNewUri!=null)
                    {
                        Log.v("Check out",mNewUri.toString());
                    }
                    i++;



                }
            } while (cursor.moveToNext());
        }

        getContentResolver().delete(ItemEntry.CONTENT_CART_URI,null,null);




    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String projection[] = {ItemEntry._ID,ItemEntry.COLUMN_ITEM_NAME,ItemEntry.COLUMN_ITEM_PRICE,ItemEntry.COLUMN_ITEM_IMAGE};

        return new CursorLoader(this,ItemEntry.CONTENT_CART_URI,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        itemCursorAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        itemCursorAdapter.swapCursor(null);

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface arg0, int arg1) {
                        OrderSuccessfulActivity.super.onBackPressed();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }
                }).create().show();
    }

}

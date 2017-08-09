package com.example.android.greekart1;

import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.app.LoaderManager;
import android.widget.Toast;

import com.example.android.greekart1.data.ItemContract.ItemEntry;

import com.example.android.greekart1.data.ItemHelper;

/**
 * Created by SruthiGanesh on 6/17/2017.
 */

public class OrdersActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    ItemHelper mDbHelper;
    ItemCursorAdapter itemCursorAdapter;
    ListView ItemListView;
    private static final int ITEM_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        ItemListView = (ListView) findViewById(R.id.list_view_item1);

        mDbHelper = new ItemHelper(getApplicationContext());
        itemCursorAdapter = new ItemCursorAdapter(this, null,getClass().getSimpleName());

        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        mDbHelper.onCreateDatabaseOrder(database,ItemEntry.TABLE_NAME_ORDERS);


        View empty_view = findViewById(R.id.empty_view);
        ItemListView.setEmptyView(empty_view);

        ItemListView.setAdapter(itemCursorAdapter);
        getLoaderManager().initLoader(ITEM_LOADER,null,this);

        String userID= getIntent().getStringExtra("UserId");





        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity);
        toolbar.setTitle("My Orders");
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
                return OrdersActivity.super.onOptionsItemSelected(item);
            }


            public void showCart()
            {
                Intent intent = new Intent(getApplicationContext(),CheckOutActivity.class);
                startActivity(intent);
            }



        });







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
                    Intent intent = new Intent(OrdersActivity.this,ProductDescription.class);

                    Log.v("BREAK FAST ACTIVITY",  uri + "");

                    intent.setData(Uri.parse(uri));
                    intent.putExtra("Quantity",quantity);

                    startActivity(intent);
                }


            }while(cursor.moveToNext());
        }
    }




    public void search()
    {
        Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
        startActivity(intent);
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String projection[] = {ItemEntry._ID,ItemEntry.COLUMN_ITEM_NAME,ItemEntry.COLUMN_ITEM_PRICE,ItemEntry.PRODUCT_NAME,ItemEntry.COLUMN_ITEM_QUANTITY,ItemEntry.USERID,ItemEntry.COLUMN_ITEM_IMAGE,ItemEntry.URI};

        return new CursorLoader(this,ItemEntry.CONTENT_ORDERS_URI,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, final Cursor cursor) {



        itemCursorAdapter.swapCursor(cursor);
        ItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                openProduct((int) id,cursor);



            }
        });

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        itemCursorAdapter.swapCursor(null);

    }
}


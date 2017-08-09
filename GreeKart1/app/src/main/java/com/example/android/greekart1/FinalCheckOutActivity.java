package com.example.android.greekart1;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.app.ProgressDialog;

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

import android.os.Handler;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.android.greekart1.data.ItemHelper;
import com.example.android.greekart1.email.MailActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.android.greekart1.data.ItemContract.ItemEntry;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.greekart1.R.id.deliver;


/**
 * Created by SruthiGanesh on 7/30/2017.
 */

public class FinalCheckOutActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,UserAdapter.OnClickInAdapter {

    private static final String TAG = MainActivity.class.getSimpleName();

    private EditText inputName,  inputAddress;
    private Button btnSave;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private int ITEM_LOADER =0;
    final int[] num = {0};
    public int count = 0;

    private String userId;
    ArrayList<String> products;
    ArrayList<String> uris;
    UserAdapter userAdapter;


    ArrayList<Integer> quantities;
    ArrayList<String> prices;
    ItemHelper mDbHelper;
    SQLiteDatabase database;
    Double totalPrice;
    public String names=" ",addresses=" ";
    public  ProgressDialog pd = null;
    int set = 0;


    public FinalCheckOutActivity()
    {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_final);

        // Displaying toolbar icon


        inputName = (EditText) findViewById(R.id.name);
        pd = new ProgressDialog(FinalCheckOutActivity.this);
        userAdapter = new UserAdapter(this,null,getClass().getSimpleName());

        btnSave = (Button) findViewById(R.id.btn_save);
        inputAddress = (EditText) findViewById(R.id.address);
        mDbHelper = new ItemHelper(getApplicationContext());
        ListView ItemListView = (ListView) findViewById(R.id.list_view_item_checkout);
        ItemListView.setAdapter(userAdapter);


        products = getIntent().getStringArrayListExtra("Product");
        quantities = getIntent().getIntegerArrayListExtra("Quantity");
        prices = getIntent().getStringArrayListExtra("Price");
        uris = getIntent().getStringArrayListExtra("Uri");
        totalPrice = getIntent().getDoubleExtra("Total Price", 0.00);
        database = mDbHelper.getWritableDatabase();
        mDbHelper.onCreate(database,ItemEntry.TABLE_NAME_USERSLIST);
        getLoaderManager().initLoader(ITEM_LOADER,null,this);
        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("Realtime Database");






        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity);
        toolbar.setTitle("Check Out");
        toolbar.inflateMenu(R.menu.cart_bar);

        toolbar.setNavigationIcon(R.drawable.icons_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new AlertDialog.Builder(FinalCheckOutActivity.this)
                        .setTitle("Order?")
                        .setMessage("By clicking yes your order is confirmed")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                            public void onClick(DialogInterface arg0, int arg1) {
                                startCheckOut();
                            }
                        }).create().show();
            }
        });

        LayoutInflater inflater;
        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.details_activity , null);

        Button delivers = (Button) layout.findViewById(R.id.deliver);
        delivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Clicked 1",Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(FinalCheckOutActivity.this)
                        .setTitle("Order?")
                        .setMessage("By clicking yes your order is confirmed")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                            public void onClick(DialogInterface arg0, int arg1) {
                                startCheckOut();
                            }
                        }).create().show();
            }
        });


    }


    public void insertIntoDatabase(String name, String address)
    {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ItemEntry.COLUMN_ITEM_NAME,name);
        contentValues.put(ItemEntry.COLUMN_ITEM_ADDRESS,address);
        Uri id = getContentResolver().insert(ItemEntry.CONTENT_USER_URI,contentValues);
        if(id!=null)
        {
            Log.v("FinalCheckout",id.toString());
        }

    }

    public void startCheckOut() {


        set = 1;
        String name = " ";
        String address = " ";


        pd.setMessage("Loading! Please wait");
        pd.show();

        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (pd.isShowing()) {
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        };

        pd.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);

            }
        });

        handler.postDelayed(runnable, 20000);


        // Hide after some seconds


        SharedPreference sharedPreference = new SharedPreference(getApplicationContext());
        String email = sharedPreference.getSharedPreference();


        if (names.equals(" ") && addresses.equals(" ")) {
            name = inputName.getText().toString();
            address = inputAddress.getText().toString();
            insertIntoDatabase(name, address);
            Log.v("Null: ", " Address and name is null");

        } else {
            name = names;
            address = addresses;
            Log.v("Null: ", " Address and name is not null");
        }


        // Check for already existed userId
        if (TextUtils.isEmpty(userId)) {
            createUser(name, email, address, totalPrice);

        }
    }

    // Save / update the user





    /**
     * Creating new user node under 'users'
     */
    private void createUser(String name, String email, String address,Double totalPrice) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }
        Log.v("Key: ",userId);





            final User user = new User(userId , name, email,address,totalPrice);

        mFirebaseDatabase.child(userId).setValue(user);



      int i = 0;
        int quantity;
        String product;

        Double price;
        String text="";
        while (i < quantities.size()) {
            quantity = quantities.get(i);
            product = products.get(i);
            price = Double.parseDouble(prices.get(i));

            DataDB data = new DataDB(userId,email,quantity,product,price);


            mFirebaseDatabase.child(userId).child("Product").child("product:"+ i).setValue(data, new DatabaseReference.CompletionListener() {


               @Override
                public void onComplete(DatabaseError error, DatabaseReference ref) {
                   if(error == null) {
                       pd.cancel();
                       num[0]++;


                       Intent intent = new Intent(getApplicationContext(),OrderSuccessfulActivity.class);
                       intent.putExtra("UserId",userId);

                       startActivity(intent);



                   }
               }


            });
            text += "\n" + product +": "+quantity;
            i++;
        }







        MailActivity mail = new MailActivity(this);
        mail.setTextConfirmation(products,quantities,prices);
        mail.sendMail();




        addUserChangeListener();
    }

    /**
     * User data change listener
     */


    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                // Check for null
                if (user == null) {

                    Log.e(TAG, "User data is null!");
                    return;
                }

                Log.v(TAG, "User data is changed!" + user.Name + ", " + user.Email);




            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });


    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface arg0, int arg1) {
                        FinalCheckOutActivity.super.onBackPressed();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }
                }).create().show();
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        String projection[] = {ItemEntry._ID,ItemEntry.COLUMN_ITEM_NAME,ItemEntry.COLUMN_ITEM_ADDRESS};

        return new CursorLoader(this,ItemEntry.CONTENT_USER_URI,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {


        userAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        userAdapter.swapCursor(null);

    }


    @Override
    public void onClickInAdapter(String name, String address) {
        names = name;
        addresses = address;
       startCheckOut();

        Toast.makeText(getApplicationContext(),name + address,Toast.LENGTH_SHORT).show();
    }
}







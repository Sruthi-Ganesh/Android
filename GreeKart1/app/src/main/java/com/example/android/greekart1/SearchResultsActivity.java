package com.example.android.greekart1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SruthiGanesh on 8/1/2017.
 */

public class SearchResultsActivity extends AppCompatActivity {
    SearchAdapter arrayAdapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity);
        toolbar.setTitle(getIntent().getStringExtra("Text"));
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
            return SearchResultsActivity.super.onOptionsItemSelected(item);
        }






    });

        List<Search> searches = getIntent().getParcelableArrayListExtra("Search");
        final ArrayList<Uri> uri = getIntent().getParcelableArrayListExtra("Uri");
        Log.v("Search Results Activity", uri + "");
        arrayAdapter = new SearchAdapter(getApplicationContext(), searches);
        ListView listView = (ListView)findViewById(R.id.list_view_item);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(uri!=null)
                {
                    Intent intent = new Intent(getApplicationContext(), ProductDescription.class);



                    intent.setData(uri.get((int) id));
                    startActivity(intent);
                }
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



}

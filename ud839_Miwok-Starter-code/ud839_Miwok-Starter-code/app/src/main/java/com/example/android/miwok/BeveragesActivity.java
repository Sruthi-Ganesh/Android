package com.example.android.miwok;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class BeveragesActivity extends AppCompatActivity {
    WordAdapter adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        ArrayList<Word> words = new ArrayList<Word>();
        // words.add("one");
        words.add(new Word("World Atlas of Whiskey ", "350", R.drawable.color_red));

        //words.add("two");
        words.add(new Word("Cocktails gift set", "590", R.drawable.color_green));
        //words.add("three");
        words.add(new Word("New Classic CockTail", "240", R.drawable.color_brown));

        //words.add("four");
        words.add(new Word("Whiskey Cocktails", "330", R.drawable.color_gray));
        //words.add("five");
        words.add(new Word("Strong Waters", "570", R.drawable.color_black));
        //words.add("six");
        words.add(new Word("Wines of America", "660", R.drawable.color_white));
        //words.add("seven");
        words.add(new Word("Boozy Baker", "930", R.drawable.color_dusty_yellow));
        //words.add("Eight");

        //words.add("Nine");
        words.add(new Word("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow));


        adapter = new WordAdapter(this, words, R.color.category_colors);
         listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                Word countries = (Word)adapter.getItem(position);

                Toast.makeText(getApplicationContext(), "You have clicked " +countries.getDefaultTranslation(),
                        Toast.LENGTH_SHORT).show();
                Intent i = new Intent(BeveragesActivity.this, productDescription.class);
                i.putExtra("Name", countries.getDefaultTranslation());
                i.putExtra("Price",countries.getMiwokTranslation());
                i.putExtra("Image",countries.getResourceId());
                startActivity(i);
            }
        });








    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }



}

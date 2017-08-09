package com.example.android.miwok;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class FruitsActivity extends AppCompatActivity {
    WordAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        ArrayList<Word> words = new ArrayList<Word>();
       // words.add("one");
        words.add(new Word("Apple","70",R.drawable.number_one));

        //words.add("two");
        words.add(new Word("Orange","39",R.drawable.number_two));
        //words.add("three");
        words.add(new Word("Potato","22",R.drawable.number_three));
        //words.add("four");
        words.add(new Word("Tomato","45",R.drawable.number_four));
        //words.add("five");
        words.add(new Word("Brinjal","62",R.drawable.number_five));
        //words.add("six");
        words.add(new Word("Carrot","19",R.drawable.number_six));
        //words.add("seven");
        words.add(new Word("Beans","47",R.drawable.number_seven));
        //words.add("Eight");
        words.add(new Word("Banana","26",R.drawable.number_eight));
        //words.add("Nine");
        words.add(new Word("Sapota","33",R.drawable.number_nine));
        //words.add("Ten");
        words.add(new Word("ten","na'aacha",R.drawable.number_ten));



    adapter = new WordAdapter(this, words,R.color.category_numbers);
        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word countries = (Word)adapter.getItem(position);

                Toast.makeText(getApplicationContext(), "You have clicked " +countries.getDefaultTranslation(),
                        Toast.LENGTH_SHORT).show();
                Intent i = new Intent(FruitsActivity.this, productDescription.class);
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
        SearchView searchView = (SearchView)item.getActionView();

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
package com.example.android.miwok;

import android.content.Intent;
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

public class HouseHoldActivity extends AppCompatActivity {
    WordAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        final ArrayList<Word> words = new ArrayList<Word>();
        // words.add("one");
        words.add(new Word("Stainless Food Storage" , "2673"));

        //words.add("two");
        words.add(new Word("Plastic Food Storage" , "250"));
        //words.add("three");
        words.add(new Word("Hot and Cold Flask" , "873"));

        //words.add("four");
        words.add(new Word("Glass Food Storage" , "376"));
        //words.add("five");
        words.add(new Word("Cooker Ware set" , "299"));
        //words.add("six");
        words.add(new Word("Vegetable Chopper" , "307"));
        //words.add("seven");
        words.add(new Word("Lunch Box" , "266"));
        //words.add("Eight");
        words.add(new Word("LED bulb" , "315"));
        //words.add("Nine");


         adapter = new WordAdapter(this, words,R.color.category_phrases);

        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word countries = (Word)adapter.getItem(position);

                Toast.makeText(getApplicationContext(), "You have clicked " +countries.getDefaultTranslation(),
                        Toast.LENGTH_SHORT).show();
                Intent i = new Intent(HouseHoldActivity.this, productDescription.class);
                i.putExtra("Name", countries.getDefaultTranslation());
                i.putExtra("Price",countries.getMiwokTranslation());
i.putExtra("obj",countries);
                if(countries.HasImage())
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

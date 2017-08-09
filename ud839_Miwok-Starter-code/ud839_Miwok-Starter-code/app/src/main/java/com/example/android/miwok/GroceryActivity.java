package com.example.android.miwok;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class GroceryActivity extends AppCompatActivity {
    WordAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        ArrayList<Word> words = new ArrayList<Word>();
        // words.add("one");
        words.add(new Word("Wheat Bread", "18.00",R.drawable.family_father));

        //words.add("two");
        words.add(new Word("Olive oil" , "320.50",R.drawable.family_mother));
        //words.add("three");
        words.add(new Word("Liquid Wash", "22.99",R.drawable.family_son));

        //words.add("four");
        words.add(new Word("Salt" , "33.00",R.drawable.family_daughter));
        //words.add("five");
        words.add(new Word("Sugar" , "22.00",R.drawable.family_older_brother));
        //words.add("six");
        words.add(new Word("Rice","61.50",R.drawable.family_younger_brother));
        //words.add("seven");
        words.add(new Word("Dhal" , "62.50",R.drawable.family_older_sister));
        //words.add("Eight");
words.add(new Word("Cream", "61.99",R.drawable.family_younger_sister));
        //words.add("Nine");
        words.add(new Word("Flour" , "29.00",R.drawable.family_grandmother));

        words.add(new Word("Whole grain pasta" , "33.00",R.drawable.family_grandfather));


         adapter = new WordAdapter(this, words,R.color.category_family);
        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word countries = (Word)adapter.getItem(position);

                Toast.makeText(getApplicationContext(), "You have clicked " +countries.getDefaultTranslation(),
                        Toast.LENGTH_SHORT).show();
                Intent i = new Intent(GroceryActivity.this, productDescription.class);
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

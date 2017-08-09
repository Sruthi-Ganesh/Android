package com.example.android.onlineordering;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by SruthiGanesh on 4/12/2017.
 */

public class FruitsVegetables extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderlayout);

        ArrayList<orderingPage> order = new ArrayList<orderingPage>();
        order.add(new orderingPage("Apple",18.00));
        order.add(new orderingPage("Mango",45.00));
        order.add(new orderingPage("potato",95.00));
        order.add(new orderingPage("tomato",62.50));

        orderingAdapter adapter = new orderingAdapter(this, order ,R.color.category_numbers);
        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
}

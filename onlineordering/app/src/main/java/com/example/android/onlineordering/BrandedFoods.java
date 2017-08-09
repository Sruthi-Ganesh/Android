package com.example.android.onlineordering;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by SruthiGanesh on 4/12/2017.
 */

public class BrandedFoods extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderlist);

        ArrayList<orderingPage> order = new ArrayList<orderingPage>();
        order.add(new orderingPage("TATA salt",18.00));
        order.add(new orderingPage("Gold Winner Refined Oil",45.00));
        order.add(new orderingPage("Horlicks Health Drink",95.00));
        order.add(new orderingPage("Aashirwath Wheat",62.50));

        orderingAdapter adapter = new orderingAdapter(this, order ,R.color.category_numbers);
        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
}

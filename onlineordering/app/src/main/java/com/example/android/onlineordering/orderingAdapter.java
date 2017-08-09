package com.example.android.onlineordering;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by SruthiGanesh on 4/12/2017.
 */

public class orderingAdapter extends ArrayAdapter<orderingPage> {
    private int backgroundColor;
    public orderingAdapter(Activity context, List<orderingPage> order , int color)
    {

        super(context,0,order);
        backgroundColor = color;

    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
        View ListItemView =  convertView;
        if(ListItemView ==null )
        {
            ListItemView = LayoutInflater.from(getContext()).inflate(R.layout.orderlayout,parent,false);
        }
        orderingPage order = getItem(position);
        TextView defaultprice = (TextView) ListItemView.findViewById(R.id.price);
        defaultprice.setText("$" + order.getDefaultPrice());
        TextView defaultname = (TextView) ListItemView.findViewById(R.id.name);
        defaultname.setText(order.getDefaultName());
        // Set the theme color for the list item
        View textContainer = (View) ListItemView.findViewById(R.id.linearLayout1);
        // Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), backgroundColor);
        textContainer.setBackgroundColor(color);
        ImageView imgView = (ImageView) ListItemView.findViewById(R.id.image);
        if(order.HasImage())

        {
            imgView.setImageResource(order.getResourceId());
            imgView.setVisibility((View.VISIBLE));
        }
        else
        {
            imgView.setVisibility(View.GONE);
        }
        return ListItemView;
    }
}

package com.example.android.miwok;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SruthiGanesh on 4/18/2017.
 */

public class CartAdapter extends ArrayAdapter<cartActivity> {

        private int backgroundColor;


        public CartAdapter(Activity context, ArrayList<cartActivity> words , int color)
        {

            super(context,0,words);
            backgroundColor = color;

        }


        @Override

        public View getView(int position, View convertView, ViewGroup parent) {
            View ListItemView =  convertView;
            if(ListItemView ==null )
            {
                ListItemView = LayoutInflater.from(getContext()).inflate(R.layout.list,parent,false);
            }

            cartActivity words = getItem(position);
            TextView defaultView = (TextView) ListItemView.findViewById(R.id.one);
            defaultView.setText(words.defaultName());
            TextView miwokView = (TextView) ListItemView.findViewById(R.id.lutti);
            miwokView.setText(words.defaultPrice());
            // Set the theme color for the list item
            View textContainer = (View) ListItemView.findViewById(R.id.linearLayout1);
            // Find the color that the resource ID maps to
            int color = ContextCompat.getColor(getContext(), backgroundColor);
            textContainer.setBackgroundColor(color);
            ImageView imgView = (ImageView) ListItemView.findViewById(R.id.image);



                imgView.setImageResource(words.resourceId());
                imgView.setVisibility((View.VISIBLE));


            return ListItemView;
        }
    }



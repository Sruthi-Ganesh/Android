package com.example.android.miwok;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by SruthiGanesh on 1/26/2017.
 */
public class WordAdapter extends ArrayAdapter<Word> {
    private int backgroundColor;
    public boolean addcart,ischecked;
   public WordAdapter(Activity context, List<Word> words ,  int color)
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
        Word words = getItem(position);
        TextView defaultView = (TextView) ListItemView.findViewById(R.id.one);
        defaultView.setText(words.getDefaultTranslation());
                TextView miwokView = (TextView) ListItemView.findViewById(R.id.lutti);
        miwokView.setText(words.getMiwokTranslation());
        // Set the theme color for the list item
               View textContainer = (View) ListItemView.findViewById(R.id.linearLayout1);
        // Find the color that the resource ID maps to
             int color = ContextCompat.getColor(getContext(), backgroundColor);
        textContainer.setBackgroundColor(color);
        ImageView imgView = (ImageView) ListItemView.findViewById(R.id.image);
        if(words.HasImage())

        {
            imgView.setImageResource(words.getResourceId());
            imgView.setVisibility((View.VISIBLE));
        }
        else
        {
            imgView.setVisibility(View.GONE);
        }
        return ListItemView;
    }
}

package com.example.android.greekart1;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static android.R.attr.id;

/**
 * Created by SruthiGanesh on 8/1/2017.
 */

public class SearchAdapter extends ArrayAdapter<Search> {

public  Context context;
    public SearchAdapter(Context context, List<Search> search )
    {

        super(context,0,search);
        this.context = context;


    }


    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
        View ListItemView =  convertView;
        if(ListItemView ==null )
        {
            ListItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        Search search = getItem(position);
        TextView defaultView = (TextView) ListItemView.findViewById(R.id.name);
        defaultView.setText(search.getName());
        TextView miwokView = (TextView) ListItemView.findViewById(R.id.summary);
        miwokView.setText(search.getPrice().toString());
        ImageView img= (ImageView) ListItemView.findViewById(R.id.image_item);
        ImageActivity imgActivity = new ImageActivity(search.getImage(), context);
        int resourceId = imgActivity.getImageId();
        final String uri = search.getUri();

        ImageButton imgButton = (ImageButton) ListItemView.findViewById(R.id.delete);
        imgButton.setVisibility(View.INVISIBLE);




        img.setImageResource(resourceId);



        return ListItemView;
    }

}

/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.devplat.lmoroney.maps3_4;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import static com.google.devplat.lmoroney.maps3_4.R.id.map;


public class MainActivity extends ActionBarActivity implements OnMapReadyCallback {

    GoogleMap m_map;
    boolean mapReady=false;

    MarkerOptions rentonmarker;

    MarkerOptions kirklandmarker;

    MarkerOptions everettmarker;

    MarkerOptions lynnwoodmarker;

    MarkerOptions montlakemarker;

    MarkerOptions kentmarker;

    MarkerOptions showaremarker;

    LatLng renton=new LatLng(47.489805, -122.120502);
    LatLng kirkland=new LatLng(47.7301986, -122.1768858);
    LatLng everett=new LatLng(47.978748,-122.202001);
    LatLng lynnwood=new LatLng(47.819533,-122.32288);
    LatLng montlake=new LatLng(47.7973733,-122.3281771);
    LatLng kent=new LatLng(47.385938,-122.258212);
    LatLng showare=new LatLng(47.38702,-122.23986);


    static final CameraPosition SEATTLE = CameraPosition.builder()
            .target(new LatLng(47.6204,-122.2491))
            .zoom(10)
            .bearing(0)
            .tilt(45)
            .build();

    @Override
    public Resources getResources() {
        return super.getResources();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         rentonmarker = new MarkerOptions()
                .position(new LatLng(47.489805, -122.120502))
                .title("Renton")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));

         kirklandmarker = new MarkerOptions()
                .position(new LatLng(47.7301986, -122.1768858))
                .title("Kirkland")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));

         everettmarker = new MarkerOptions()
                .position(new LatLng(47.978748,-122.202001))
                .title("Everett")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));

         lynnwoodmarker = new MarkerOptions()
                .position(new LatLng(47.819533,-122.32288))
                .title("Lynnwood")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));

         montlakemarker = new MarkerOptions()
                .position(new LatLng(47.7973733,-122.3281771))
                .title("Montlake Terrace")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));

         kentmarker = new MarkerOptions()
                .position(new LatLng(47.385938,-122.258212))
                .title("Kent Valley")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));

         showaremarker = new MarkerOptions()
                .position(new LatLng(47.38702,-122.23986))
                .title("Showare Center")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap map){
        //MapsInitializer.initialize(getApplicationContext());
        mapReady=true;
        m_map = map;
        m_map.addMarker(rentonmarker);
        m_map.addMarker(kirklandmarker);
        m_map.addMarker(everettmarker);
        m_map.addMarker(lynnwoodmarker);
        m_map.addMarker(montlakemarker);
        m_map.addMarker(kentmarker);
        m_map.addMarker(showaremarker);
        flyTo(SEATTLE);
    }

    private void flyTo(CameraPosition target)
    {
        m_map.moveCamera(CameraUpdateFactory.newCameraPosition(target));
        m_map.addPolyline(new PolylineOptions().geodesic(true).add(renton).add(kirkland).add(everett).add(lynnwood).add(montlake).add(kent).add(showare).add(renton));
        m_map.addCircle(new CircleOptions()
                .center(renton)
                .radius(5000)
                .strokeColor(Color.GREEN)
                .fillColor(Color.argb(64,0,255,0)));
    }
}

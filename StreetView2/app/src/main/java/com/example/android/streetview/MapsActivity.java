package com.example.android.streetview;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;

public class MapsActivity extends FragmentActivity implements OnStreetViewPanoramaReadyCallback {



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_maps);
            StreetViewPanoramaFragment streetViewPanoramaFragment =
                    (StreetViewPanoramaFragment) getFragmentManager()
                            .findFragmentById(R.id.map);
            streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);
        }

        @Override
        public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
            // Set the panorama location on startup, when no
            // panoramas have been loaded.

            panorama.setPosition(new LatLng(12.9532,80.1416));
            StreetViewPanoramaCamera camera = new StreetViewPanoramaCamera.Builder()
                    .bearing(180)
                    .build();
            panorama.animateTo(camera,10000);

        }

    }
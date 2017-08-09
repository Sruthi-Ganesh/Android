package com.example.android.loc;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;

 import android.location.LocationManager;
        import android.os.Bundle;
        import android.app.Activity;
        import android.app.PendingIntent;
        import android.content.Intent;
        import android.view.Menu;
@SuppressWarnings({"MissingPermission"})
public class MainActivity extends Activity {
    LocationManager lm;
    double lat=123,long1=34;    //Defining Latitude & Longitude
    float radius=3000;                         //Defining Radius
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lm=(LocationManager) getSystemService(LOCATION_SERVICE);
        Intent i= new Intent("com.example.android.loc.proximityalert");           //Custom Action
        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), -1, i, 0);
        lm.addProximityAlert(lat, long1, radius, -1, pi);
    }


}
package com.example.android.locationfinder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.example.android.locationfinder.FetchAddress.LOCATION_DATA_EXTRA;
import static com.example.android.locationfinder.FetchAddress.RECEIVER;
import static com.example.android.locationfinder.FetchAddress.RESULT_DATA_KEY;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,LocationListener {
    private Set<String> LateDate = new HashSet<>();
    private Set<String> Date = new HashSet<>();
    int n[] = new int[3];
    private AddressResultReceiver mResultReceiver;
    protected Location mLastLocation;
    protected String mAddressOutput;
    protected TextView mLocationAddressTextView;
    int i=0,j=0;
    private Button mFetchAddressButton;
    private final double finallattitude=12.9424026,finallongitude=80.1463552;
    Double lattitude,longitude;
    private final String LoG = "Location Finder";
    private TextView lattitudeFinder, isChecked, longitudeFinder;

    private GoogleApiClient mGoogleApiClient;
    int checkedin = 0;
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                // adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();
        mResultReceiver = new AddressResultReceiver(new Handler());
        mLocationAddressTextView = (TextView) findViewById(R.id.address);

        mFetchAddressButton = (Button) findViewById(R.id.fetch_address_button);
        mAddressOutput = "";


        lattitudeFinder = (TextView) findViewById(R.id.location);
        isChecked = (TextView) findViewById(R.id.check);
        longitudeFinder = (TextView) findViewById(R.id.longitude);
        Date = getSharedPrefDate();
        LateDate=getSharedPrefDateLate();


    }
//While the activity starts
    @Override
    protected void onStart() {
        try {
            Date = getSharedPrefDate();
            LateDate=getSharedPrefDateLate();
        }
        catch (Exception e)
        {
            Log.v("Single value","Not added to draw chart");
        }

        super.onStart();
        mGoogleApiClient.connect();
    }
    //the fetch address button listener
    public void fetchAddressButtonHandler(View view) {
        // We only start the service to fetch the address if GoogleApiClient is connected.
        if (mGoogleApiClient.isConnected() && mLastLocation != null) {

            startIntentService();
        }

    }
//to start the intent service to get the address
    protected void startIntentService() {
        Intent intent = new Intent(this, FetchAddress.class);
        intent.putExtra(RECEIVER, mResultReceiver);
        intent.putExtra("Latitude",finallattitude);
        intent.putExtra("Longitude",finallongitude);
        intent.putExtra(LOCATION_DATA_EXTRA, mLastLocation);

        startService(intent);
    }

//while the activity stops
    @Override
    protected void onStop() {

        try {
            markAttendance();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mGoogleApiClient.disconnect();
        super.onStop();
    }

    //to connect with the location services
    @SuppressWarnings({"MissingPermission"})
    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(100); //updating location every second

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            // Determine whether a Geocoder is available.

            startIntentService();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(LoG, "Api connection is suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.e(LoG, "Connection Failed");
    }
//when the location gets changed
    @Override
    public void onLocationChanged(Location location) {

        Log.i(LoG, "Location is changed ");
        lattitudeFinder.setText(Double.toString(location.getLatitude()));
        longitudeFinder.setText(Double.toString(location.getLongitude()));

         lattitude = location.getLatitude();
         longitude = location.getLongitude();
        Double lat = Double.parseDouble(String.format("%.4f", lattitude));
        Double longi = Double.parseDouble(String.format("%.4f", longitude));
        Double checkLatitude =Double.parseDouble(String.format("%.4f", finallattitude));
        Double checkLongitude = Double.parseDouble(String.format("%.4f",finallongitude));
        Log.v("lattitude","" + lattitude + longitude + checkLatitude+checkLongitude);
        if (checkedin != 1) {

            Toast.makeText(MainActivity.this, "Not checked in", Toast.LENGTH_SHORT).show();
            isChecked.setText("Not checked in/out");
            checkedin = -1;
            if (lat == 12.9424 && longi == 80.1464) {

                isChecked.setText("Checked In");
                checkedin = 1;
                Toast.makeText(MainActivity.this, "Checked in " + checkedin, Toast.LENGTH_SHORT).show();
            }
        } else if (checkedin == 1) {
            if (lat != 12.9424 && longi != 80.1464) {
                Toast.makeText(MainActivity.this, "Checked out", Toast.LENGTH_LONG);
                isChecked.setText("Checked out");
            }
        }


    }
    //to display the send the address to the xml
    protected void displayAddressOutput() {

           mLocationAddressTextView.setText(mAddressOutput);

           Log.v("Address",mAddressOutput);

    }

    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        /**
         * Receives data sent from FetchAddressIntentService and updates the UI in MainActivity.
         */
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            // Display the address string or an error message sent from the intent service.
            mAddressOutput = resultData.getString(RESULT_DATA_KEY);
            displayAddressOutput();

        }
    }
//to mark the attendance based on time and generate chart
    public void markAttendance() throws ParseException {


        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        DateFormat bf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calobj = Calendar.getInstance();
        Date d = calobj.getTime();
        String dat = bf.format(d);
        String b = df.format(calobj.getTime());
        Log.v("Time", b);

        if (checkedin == 1 && isTimeBetweenTwoTime("12:00:00", "23:00:00", b)) {
            Log.v("Time", "On Time");
            updateSharedPref(dat);
            //Date.add(dat);



        }
       else if (checkedin == 1 && isTimeBetweenTwoTime("12:00:00", "23:00:00", b)) {

            updateSharedPrefLate(dat);
            Log.v("Time", "Time is up");


        }


    }
//to check whether the time lies between two times.
    public static boolean isTimeBetweenTwoTime(String argStartTime,
                                               String argEndTime, String argCurrentTime) throws ParseException {
        String reg = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
        //
        if (argStartTime.matches(reg) && argEndTime.matches(reg)
                && argCurrentTime.matches(reg)) {
            boolean valid = false;
            // Start Time
            Date startTime = new SimpleDateFormat("HH:mm:ss")
                    .parse(argStartTime);
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startTime);

            // Current Time
            Date currentTime = new SimpleDateFormat("HH:mm:ss")
                    .parse(argCurrentTime);
            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTime(currentTime);

            // End Time
            Date endTime = new SimpleDateFormat("HH:mm:ss")
                    .parse(argEndTime);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endTime);

            //
            if (currentTime.compareTo(endTime) < 0) {

                currentCalendar.add(Calendar.DATE, 1);
                currentTime = currentCalendar.getTime();

            }

            if (startTime.compareTo(endTime) < 0) {

                startCalendar.add(Calendar.DATE, 1);
                startTime = startCalendar.getTime();

            }
            //
            if (currentTime.before(startTime)) {

                System.out.println(" Time is Lesser ");

                valid = false;
            } else {

                if (currentTime.after(endTime)) {
                    endCalendar.add(Calendar.DATE, 1);
                    endTime = endCalendar.getTime();

                }

                System.out.println("Comparing , Start Time /n " + startTime);
                System.out.println("Comparing , End Time /n " + endTime);
                System.out
                        .println("Comparing , Current Time /n " + currentTime);

                if (currentTime.before(endTime)) {
                    System.out.println("RESULT, Time lies b/w");
                    valid = true;
                } else {
                    valid = false;
                    System.out.println("RESULT, Time does not lies b/w");
                }

            }
            return valid;

        } else {
            throw new IllegalArgumentException(
                    "Not a valid time, expecting HH:MM:SS format");
        }

    }


    public void updateSharedPref(String date) {

        if(Date==null)
             Date = new HashSet<>();
            Log.v("Date shared pref", date);
            Date.add(date);
            SharedPreferences sharp = this.getSharedPreferences("your activity", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharp.edit();
            editor.clear();
            editor.putStringSet("Date", Date);

            editor.apply();
            Log.v("put shared pref", Date.toString());
            Date = getSharedPrefDate();
            Log.v("put shared pref 2", Date.toString());
        }



    public void updateSharedPrefLate(String date) {

        if(LateDate==null)
            LateDate = new HashSet<>();
            Log.v("Late Date shared pref", date);
            LateDate.add(date);
            Log.v("Late Date shared pref", LateDate.toString());
            SharedPreferences sharp = this.getSharedPreferences("your activity1", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharp.edit();
            editor.clear();
            editor.putStringSet("LateDate", LateDate);

            editor.apply();
            Log.v("Late shared pref", LateDate.toString());
            LateDate = getSharedPrefDateLate();
            Log.v("Late shared pref 2", LateDate.toString());


    }

    public Set<String> getSharedPrefDate() {

             SharedPreferences shap = this.getSharedPreferences("your activity", Context.MODE_PRIVATE);
             Set<String> Dat = new HashSet<>();
                   Dat =  shap.getStringSet("Date", null);
             if(Dat!=null) {

                 Log.v("get shared pref Date", Dat.toString());

                 return Dat;
             }
        return null;

    }
    public Set<String> getSharedPrefDateLate() {

        SharedPreferences shap = this.getSharedPreferences("your activity1", Context.MODE_PRIVATE);
        Set<String> Dat = new HashSet<>();
        Dat =  shap.getStringSet("LateDate", null);
        if(Dat!=null) {

            Log.v("Late get shared pref", Dat.toString());

            return Dat;
        }
        Log.v("get","No return");
        return null;

    }
    public void viewMap(View v)
    {
        Intent i = new Intent(this,MapsActivity.class);
        i.putExtra("lattitude1",lattitude);
        i.putExtra("longitude1",longitude);
        i.putExtra("lattitude2",finallattitude);
        i.putExtra("longitude2",finallongitude);
        startActivity(i);
    }

    public void viewChart(View view) {
        try {
         if(n==null)
         {
             n= new int[3];
         }
            n[0]=Date.size();
            n[1]=LateDate.size();
            Intent i = new Intent(MainActivity.this, Piechart.class);
            i.putExtra("size", n);
            startActivity(i);
        }
      catch(Exception E)
      {
          Toast.makeText(this,"Chart not generated",Toast.LENGTH_SHORT).show();
          Toast.makeText(this,"Wait until next check in",Toast.LENGTH_SHORT).show();
          Log.v("Error","cannot draw chart");
      }
    }

    public void clearShared(View view)
    {
        SharedPreferences sharp = this.getSharedPreferences("your activity", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharp.edit();
        editor.clear();
        editor.apply();

        SharedPreferences shap = this.getSharedPreferences("your activity1", Context.MODE_PRIVATE);
        editor = shap.edit();
        editor.clear();
        editor.apply();
        Toast.makeText(this,"Click apply to add",Toast.LENGTH_SHORT).show();


    }
    public void applyShared(View view)
    {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        finish();

    }
}

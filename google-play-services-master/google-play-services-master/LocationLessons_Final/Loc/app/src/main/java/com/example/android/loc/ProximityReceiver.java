package com.example.android.loc;

/**
 * Created by SruthiGanesh on 4/24/2017.
 */

import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.content.IntentFilter;
        import android.location.LocationManager;
        import android.widget.Toast;

/*This is the Reciever for the Brodcast sent, here our app will be notified if the User is
* in the region specified by our proximity alert.You will have to register the reciever
* with the same Intent you broadcasted in the previous Java file
*
 * @Author: Adnan A M
 */


public class ProximityReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        // TODO Auto-generated method stub
        // The reciever gets the Context & the Intent that fired the broadcast as arg0 & agr1

        String k=LocationManager.KEY_PROXIMITY_ENTERING;
        // Key for determining whether user is leaving or entering

        boolean state=arg1.getBooleanExtra(k, false);
        //Gives whether the user is entering or leaving in boolean form

        if(state){
            // Call the Notification Service or anything else that you would like to do here
            Toast.makeText(arg0, "Welcome to my Area", 600).show();
        }else{
            //Other custom Notification
            Toast.makeText(arg0, "Thank you for visiting my Area,come back again !!", 600).show();

        }

    }


}
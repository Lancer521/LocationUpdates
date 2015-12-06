package com.google.android.gms.location.sample.locationupdates;

import android.location.Location;
import android.os.AsyncTask;

import java.util.Vector;

/**
 * Created by Ty on 12/5/2015.
 */
public class findLocation extends AsyncTask<Vector<Location>, Integer, Boolean>{


    @Override
    protected Boolean doInBackground(Vector<Location>... params) {
        int count = params.length;
        if(count != 2) return false;
        float distance = 0;
        Location currentLocation = params[0].firstElement();
        Location destLocation = params[0].lastElement();

        distance = currentLocation.distanceTo(destLocation);

        return null;
    }
}

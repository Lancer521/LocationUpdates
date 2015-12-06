package com.google.android.gms.location.sample.locationupdates;

import android.location.Location;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Collections;
import java.util.Vector;

/**
 * Created by Ty on 12/5/2015.
 */
public class findLocation extends AsyncTask<Vector<Location>, Integer, String>{


    @Override
    protected String doInBackground(Vector<Location>... params) {

        Vector<Location> temp = params[0];
        int size = temp.size()-1;
        float distance = 1000;
        Location currentLocation = temp.firstElement();
        temp.removeElementAt(0);

        //Get distance to all destinations; keep smallest distance
        int minIndex = 0;
        for(int i = 0; i < size; i++){
            if(distance > currentLocation.distanceTo(temp.firstElement())) {
                distance = currentLocation.distanceTo(temp.firstElement());
                minIndex = i;
            }
            temp.removeElementAt(0);
        }

        //Create 2D vector from xml arrays?
        Vector<Vector<Integer>> times = new Vector<>();
        Vector<Integer> times_one = new Vector<>();
        times_one.add(0);
        times_one.add(1);
        times_one.add(2);
        times.add(times_one);
        times.add(times_one);

        return howLong(times, minIndex);


    }

    private String howLong(Vector<Vector<Integer>> vector, float min){
        Vector<Integer> possibleTimes = new Vector<>();

        //Find bus times at location found previously
        for(int i = 0; i < vector.size(); i++){
            if(vector.elementAt(i).indexOf(min) != -1)
                possibleTimes.add(vector.elementAt(i).indexOf(min));
        }

        Calendar c = Calendar.getInstance();
        int minutes = c.get(Calendar.MINUTE);

        //Find stop time 1-2 minutes ahead
        for (int i = 0; i < possibleTimes.size(); i++) {
            if (possibleTimes.elementAt(i) >= minutes+1 && possibleTimes.elementAt(i) <= minutes+2) {
                return "Bus will arrive at " + possibleTimes.elementAt(i);
            }
        }

        return null;
    }
}

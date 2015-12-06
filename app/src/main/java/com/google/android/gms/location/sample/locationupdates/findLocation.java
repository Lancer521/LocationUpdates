package com.google.android.gms.location.sample.locationupdates;

import android.location.Location;
import android.os.AsyncTask;

import java.util.Collections;
import java.util.Vector;

/**
 * Created by Ty on 12/5/2015.
 */
public class findLocation extends AsyncTask<Vector<Location>, Integer, Integer>{


    @Override
    protected Integer doInBackground(Vector<Location>... params) {

        Vector<Location> temp = params[0];
        int size = temp.size()-1;
        float[] distance = new float[size];
        Location currentLocation = temp.firstElement();
        temp.removeElementAt(0);
        for(int i = 0; i < size; i++){
            distance[i] = currentLocation.distanceTo(temp.firstElement());
            temp.removeElementAt(0);
        }
        float max = 0;
        int maxIndex = 0;
        for(int i = 0; i < distance.length; i++){
            if(distance[i] > max){
                max = distance[i];
                maxIndex = i;
            }
        }

        return maxIndex;
    }

    private int howLong(Vector<Location> location){


        return 0;
    }
}

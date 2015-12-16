package com.google.android.gms.location.sample.locationupdates;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Vector;

/**
 * Created by Ty on 12/5/2015.
 */
public class findLocation extends AsyncTask<myTaskParams, Integer, String>{

    Context mContext = null;

    public findLocation(Context context){
        mContext = context;
    }

    @Override
    protected String doInBackground(myTaskParams... params) {

        myTaskParams temp = new myTaskParams(params[0].locations, params[0].times, params[0].currentLocation);
        int size = temp.locations.size();
        float distance = 1000;
        Location currentLocation = params[0].currentLocation;

        //Get distance to all destinations; keep smallest distance
        int minIndex = 0;
        for(int i = 0; i < size; i++){
            if(distance > currentLocation.distanceTo(temp.locations.firstElement())) {
                distance = currentLocation.distanceTo(temp.locations.firstElement());
                minIndex = i;
            }
            temp.locations.removeElementAt(0);
        }

        //Create 2D vector from xml arrays?
        Vector<Vector<Integer>> stops = new Vector<>();
        Vector<Integer> times = new Vector<>();
        times.add(0);
        times.add(19);
        times.add(21);
        stops.add(times);
        stops.add(times);

        return howLong(stops, minIndex);

    }

    /**
     * This runs on the UI thread using the context provided
     * in the constructor and the result from doInBackground
     * See http://stackoverflow.com/questions/32485789/how-can-i-show-a-toast-from-asynctask-onpostexecute?rq=1
     */
    @Override
    protected void onPostExecute(String result){
        Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
    }

    private String howLong(Vector<Vector<Integer>> times, float minIndex){
        Vector<Integer> possibleTimes = new Vector<>();

        //Find bus times at location found previously
        for(int i = 0; i < times.size(); i++){
            if(times.elementAt(i).indexOf(minIndex) != -1)
                possibleTimes.add(times.elementAt(i).indexOf(minIndex));
        }

        Calendar c = Calendar.getInstance();
        int minutes = c.get(Calendar.MINUTE);



        //Find stop time 1-2 minutes ahead
        for (int i = 0; i < possibleTimes.size(); i++) {
            if (possibleTimes.elementAt(i) >= minutes+1 && possibleTimes.elementAt(i) <= minutes+2) {
                return "Bus will arrive at " + possibleTimes.elementAt(i);
            }
        }
        return "failed";
    }
}

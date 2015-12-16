package com.google.android.gms.location.sample.locationupdates;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
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
        Vector<Vector<Date>> stops = new Vector<>(params[0].times);

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

    private String howLong(Vector<Vector<Date>> times, int minIndex){

        Calendar c = Calendar.getInstance();
        Date currentTime = c.getTime();
        c.add(Calendar.MINUTE, 3);
        Date maxTime = c.getTime();

        for(int i = 0; i < times.elementAt(minIndex).size(); i++){
            if(currentTime.before(times.elementAt(minIndex).elementAt(i)) && maxTime.after(times.elementAt(minIndex).elementAt(i))){
                return "Bus will arrive at " + times.elementAt(minIndex).elementAt(i).getTime();
            }
        }

        return "failed";

        /*if(currentTime.getHours() == times.elementAt(minIndex).elementAt(i).getHours()){
            if(currentTime.getTime() <= times.elementAt(minIndex).elementAt(i).getTime() && maxTime.getTime() >= times.elementAt(minIndex).elementAt(i).getTime()){
                return "Bus will arrive at " + times.elementAt(minIndex).elementAt(i).getTime();
            }
        }*/
    }
}

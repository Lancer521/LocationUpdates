package com.google.android.gms.location.sample.locationupdates;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
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

        Vector<Location> temp = new Vector<>(params[0].locations);
        int size = temp.size();
        float distance = params[0].meters;
        Location currentLocation = params[0].currentLocation;
        params[0].message = null;

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.US);

        //Get distance to all destinations; keep smallest distance
        int closestStopIndex = 0;
        for(int i = 0; i < size; i++){
            if(distance > currentLocation.distanceTo(temp.firstElement())) {
                distance = currentLocation.distanceTo(temp.firstElement());
                closestStopIndex = i;
            }
            temp.removeElementAt(0);
        }

        Vector<Vector<Date>> stops = new Vector<>(params[0].times);
        Date date = howLong(stops, closestStopIndex, params[0]);

        //Format the date (time) and create a notification.
        if(date != null && !params[0].wasNotified){
            params[0].message = sdf.format(date);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext)
                    .setSmallIcon(R.drawable.bus_front_blue)
                    .setContentTitle("Bus nearby!")
                    .setContentText("The bus arrives at " + params[0].getStopName() + " at " + params[0].message)
                    .setVibrate(new long[]{2000, 2000, 2000})
                    .setLights(Color.RED, 3000, 3000);
            int mNotificationId = 1;
            NotificationManager mNotifyMgr = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

            //IN THE FUTURE: Add actions to notification, including what happens when clicked.

            mNotifyMgr.notify(mNotificationId, mBuilder.build());
            params[0].wasNotified = true;

        }

        return params[0].message;

    }

    /**
     * This runs on the UI thread using the context provided
     * in the constructor and the result from doInBackground
     * See http://stackoverflow.com/questions/32485789/how-can-i-show-a-toast-from-asynctask-onpostexecute?rq=1
     */
    @Override
    protected void onPostExecute(String result){
        //Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
    }

    private Date howLong(Vector<Vector<Date>> times, int closestStopIndex, myTaskParams params){

        Calendar c = Calendar.getInstance();
        Date currentTime = c.getTime();
        c.add(Calendar.MINUTE, params.minutes);
        Date maxTime = c.getTime();

        for(int i = 0; i < times.elementAt(closestStopIndex).size(); i++){
            if(currentTime.before(times.elementAt(closestStopIndex).elementAt(i)) && maxTime.after(times.elementAt(closestStopIndex).elementAt(i))){
                params.whichStop = closestStopIndex;
                return times.elementAt(closestStopIndex).elementAt(i);
            } else if (currentTime.equals(times.elementAt(closestStopIndex).elementAt(i))){
                params.whichStop = closestStopIndex;
                return times.elementAt(closestStopIndex).elementAt(i);
            }
        }
        return null;
    }
}

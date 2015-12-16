package com.google.android.gms.location.sample.locationupdates;

import android.location.Location;

import java.util.Date;
import java.util.Vector;

/**
 * Created by Ty on 12/15/2015.
 * Idea from http://stackoverflow.com/questions/12069669/how-can-you-pass-multiple-primitive-parameters-to-asynctask
 */
public class myTaskParams {

    protected Vector<Location> locations;
    protected Vector<Vector<Date>> times;
    protected Location currentLocation;

    public myTaskParams(){
        locations = new Vector<>();
        times = new Vector<>();
        currentLocation = null;
    }

    public myTaskParams(Vector<Location> l, Vector<Vector<Date>> t, Location cl){
        locations = new Vector<>(l);
        times = new Vector<>(t);
        currentLocation = cl;
    }
}

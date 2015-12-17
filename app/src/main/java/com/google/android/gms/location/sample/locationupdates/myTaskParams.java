package com.google.android.gms.location.sample.locationupdates;

import android.content.Context;
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
    protected int whichBus;
    protected String message;
    protected Boolean wasNotified;

    public myTaskParams(){
        locations = new Vector<>();
        times = new Vector<>();
        currentLocation = null;
        message = null;
        wasNotified = false;
        whichBus = -1;
    }

    public myTaskParams(Vector<Location> l, Vector<Vector<Date>> t, Location cl, String m, Boolean w){
        locations = new Vector<>(l);
        times = new Vector<>(t);
        currentLocation = cl;
        message = m;
        wasNotified = w;
    }

    protected String getStopName(){
        switch(whichBus){
            case 0:
                return "Transit Center";
            case 1:
                return "USU Vet Sci Building";
            case 2:
                return "1450 E 1500 N";
            case 3:
                return "USU Education Building";
            case 4:
                return "North Logan City Hall";
            case 5:
                return "66 E Hyde Park";
            case 6:
                return "Sky View High School";
            case 7:
                return "Richmond 40 S State";
            case 8:
                return "Smithfield Civic Center";
            case 9:
                return "Cache County School District";
            case 10:
                return "Test case";
            default:
                return null;
        }
    }
}

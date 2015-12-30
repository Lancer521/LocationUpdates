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
    protected Location previousLocation;
    protected int whichStop;
    protected String message;
    protected Boolean wasNotified;
    protected double currentSpeed;

    //Preferences
    protected int minutes;
    protected double miles;
    protected int meters;
    protected int temp_below;
    protected int temp_above;
    protected boolean walkingEnabled;
    protected boolean bikingEnabled;

    //Spinner indices
    private int minIndex;
    private int mileIndex;
    private int tempBIndex;
    private int tempAIndex;

    protected final int BIKE_MIN_SPEED = 7;
    protected final int BIKE_MAX_SPEED = 20;
    protected final int UPDATE_INTERVAL = 10000;

    public myTaskParams(){
        locations = new Vector<>();
        times = new Vector<>();
        currentLocation = null;
        previousLocation = null;
        message = null;
        wasNotified = false;
        whichStop = -1;
        minutes = 3;
        miles = .3;
        meters = toMeters(miles);
        temp_below = 50;
        temp_above = 90;
        walkingEnabled = true;
        bikingEnabled = true;
        currentSpeed = 0.0;
    }

    protected int toMeters(double d) {
        return (int)(d * 1609.34);
    }

    protected String getStopName(){
        switch(whichStop){
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
                return "Test Stop";
            default:
                return null;
        }
    }

    protected void setMinIndex(int i){
        minIndex = i;
    }

    protected void setMileIndex(int i){
        mileIndex = i;
    }

    protected void setTempBIndex(int i){
        tempBIndex = i;
    }

    protected void setTempAIndex(int i){
        tempAIndex = i;
    }

    protected int getMinIndex(){
        return minIndex;
    }

    protected int getMileIndex(){
        return mileIndex;
    }

    protected int getTempBIndex(){
        return tempBIndex;
    }

    protected int getTempAIndex(){
        return tempAIndex;
    }

    protected void parseTemp(String t, int whichTemp){
        t = t.substring(0, t.length()-2);
        if(whichTemp == 0){
            temp_above = Integer.parseInt(t);
        } else if(whichTemp == 1){
            temp_below = Integer.parseInt(t);
        }
    }

    protected void getSpeed(){
        if(previousLocation == null) return;
        double distance = previousLocation.distanceTo(currentLocation) * 0.000621371;
        currentSpeed = (distance/UPDATE_INTERVAL)*3600;
    }

}

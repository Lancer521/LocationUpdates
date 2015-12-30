package com.google.android.gms.location.sample.locationupdates;

import android.location.Location;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/**
 * Created by Ty on 12/15/2015.
 */
public class loadSchedule {

    public loadSchedule(){}
    private static Calendar currCal = Calendar.getInstance();
    private static int currYear = currCal.get(Calendar.YEAR);
    private static int currMonth = currCal.get(Calendar.MONTH);
    private static int currDay = currCal.get(Calendar.DAY_OF_MONTH);

    protected static void build(Vector<Location> stops, Vector<Vector<Date>> times){

        //Changed to 11 for testing purposes.  Only need 10
        for(int i = 0; i < 11; i++){
            Vector<Date> temp = new Vector<>();
            times.add(temp);
        }

        createStopsVector(stops);
        createTimesVector(times);

    }

    private static void createStopsVector(Vector<Location> stops) {

        Location temp = new Location("Intermodal Transit Center");
        temp.setLatitude(41.740696);
        temp.setLongitude(-111.830901);
        stops.add(temp);

        temp = new Location("USU Vet Science Bldg");
        temp.setLatitude(41.744367);
        temp.setLongitude(-111.81097);
        stops.add(temp);

        temp = new Location("1450 East 1500 North");
        temp.setLatitude(41.758349);
        temp.setLongitude(-111.79794);
        stops.add(temp);

        temp = new Location("USU Education Bldg");
        temp.setLatitude(41.74456);
        temp.setLongitude(-111.81098);
        stops.add(temp);

        temp = new Location("North Logan City Hall");
        temp.setLatitude(41.768322);
        temp.setLongitude(-111.804454);
        stops.add(temp);

        temp = new Location("66 E Hyde Park Ln");
        temp.setLatitude(41.79913);
        temp.setLongitude(-111.81782);
        stops.add(temp);

        temp = new Location("Sky View High School");
        temp.setLatitude(41.82644);
        temp.setLongitude(-111.82635);
        stops.add(temp);

        temp = new Location("Richmond 40 S State");
        temp.setLatitude(41.92179);
        temp.setLongitude(-111.80885);
        stops.add(temp);

        temp = new Location("Smithfield Civic Center");
        temp.setLatitude(41.838448);
        temp.setLongitude(-111.831246);
        stops.add(temp);

        temp = new Location("Cache County School District");
        temp.setLatitude(41.76801);
        temp.setLongitude(-111.80461);
        stops.add(temp);

        /**THIS LOCATION IS STRICTLY FOR TESTING PURPOSES**/
        temp = new Location("Testing from Institute");
        temp.setLatitude(41.741978);
        temp.setLongitude(-111.809861);
        stops.add(temp);
        /**************************************************/
    }

    private static void createTimesVector(Vector<Vector<Date>> times) {
        createRoute1(times);
        createRoute4(times);
        createRouteC(times);
    }

    private static void createRoute1(Vector<Vector<Date>> times) {
        Calendar vet_sci_time = Calendar.getInstance();
        vet_sci_time.set(currYear, currMonth, currDay, 7, 4, 0);
        Calendar church_time = Calendar.getInstance();
        church_time.set(currYear, currMonth, currDay, 7, 11, 0);
        Calendar edu_time = Calendar.getInstance();
        edu_time.set(currYear, currMonth, currDay, 7, 19, 0);
        Calendar transit_time = Calendar.getInstance();
        transit_time.set(currYear, currMonth, currDay, 7, 25, 0);

        times.elementAt(0).add(transit_time.getTime());
        times.elementAt(1).add(vet_sci_time.getTime());
        times.elementAt(2).add(church_time.getTime());
        times.elementAt(3).add(edu_time.getTime());

        for(int i = 0; i < 14; i++) {
            vet_sci_time.add(Calendar.MINUTE, 15);
            church_time.add(Calendar.MINUTE, 15);
            edu_time.add(Calendar.MINUTE, 15);
            transit_time.add(Calendar.MINUTE, 15);

            times.elementAt(1).add(vet_sci_time.getTime());
            times.elementAt(2).add(church_time.getTime());
            times.elementAt(3).add(edu_time.getTime());
            times.elementAt(0).add(transit_time.getTime());
        }
        for(int i = 0; i < 9; i++){
            vet_sci_time.add(Calendar.MINUTE, 30);
            church_time.add(Calendar.MINUTE, 30);
            edu_time.add(Calendar.MINUTE, 30);
            transit_time.add(Calendar.MINUTE, 30);

            times.elementAt(1).add(vet_sci_time.getTime());
            times.elementAt(2).add(church_time.getTime());
            times.elementAt(3).add(edu_time.getTime());
            times.elementAt(0).add(transit_time.getTime());
        }
        for(int i = 0; i < 12; i++){
            vet_sci_time.add(Calendar.MINUTE, 15);
            church_time.add(Calendar.MINUTE, 15);
            edu_time.add(Calendar.MINUTE, 15);
            transit_time.add(Calendar.MINUTE, 15);

            times.elementAt(1).add(vet_sci_time.getTime());
            times.elementAt(2).add(church_time.getTime());
            times.elementAt(3).add(edu_time.getTime());
            times.elementAt(0).add(transit_time.getTime());
        }
        for(int i = 0; i < 4; i++){
            vet_sci_time.add(Calendar.MINUTE, 30);
            church_time.add(Calendar.MINUTE, 30);
            edu_time.add(Calendar.MINUTE, 30);
            transit_time.add(Calendar.MINUTE, 30);

            times.elementAt(1).add(vet_sci_time.getTime());
            times.elementAt(2).add(church_time.getTime());
            times.elementAt(3).add(edu_time.getTime());
            times.elementAt(0).add(transit_time.getTime());
        }
    }

    private static void createRoute4(Vector<Vector<Date>> times) {
        Calendar edu_time = Calendar.getInstance();
        edu_time.set(currYear, currMonth, currDay, 7, 15, 0);
        Calendar transit_time = Calendar.getInstance();
        transit_time.set(currYear, currMonth, currDay, 7, 22, 0);

        /***TEST_TIME IS STRICTLY FOR TESTING PURPOSES ***/
        Calendar test_time = Calendar.getInstance();
        test_time.add(Calendar.MINUTE, 4);
        times.elementAt(10).add(test_time.getTime());
        /*************************************************/

        times.elementAt(3).add(edu_time.getTime());
        times.elementAt(0).add(transit_time.getTime());

        for(int i = 0; i < 27; i++) {
            edu_time.add(Calendar.MINUTE, 30);
            transit_time.add(Calendar.MINUTE, 30);

            times.elementAt(3).add(edu_time.getTime());
            times.elementAt(0).add(transit_time.getTime());
        }
    }

    private static void createRouteC(Vector<Vector<Date>> times) {
        Calendar vet_sci_time = Calendar.getInstance();
        Calendar city_hall_time = Calendar.getInstance();
        Calendar hyde_park_time_1 = Calendar.getInstance();
        Calendar sky_view_time_1 = Calendar.getInstance();
        Calendar richmond_time = Calendar.getInstance();
        Calendar civic_center_time = Calendar.getInstance();
        Calendar sky_view_time_2 = Calendar.getInstance();
        Calendar hyde_park_time_2 = Calendar.getInstance();
        Calendar school_district_time = Calendar.getInstance();
        Calendar education_time = Calendar.getInstance();
        Calendar transit_time = Calendar.getInstance();

        vet_sci_time.set(currYear,currMonth,currDay,5,49,0);
        city_hall_time.set(currYear,currMonth,currDay,5,55,0);
        hyde_park_time_1.set(currYear,currMonth,currDay,6,0,0);
        sky_view_time_1.set(currYear,currMonth,currDay,6,5,0);
        richmond_time.set(currYear,currMonth,currDay,6,25,0);
        civic_center_time.set(currYear,currMonth,currDay,6,34,0);
        sky_view_time_2.set(currYear,currMonth,currDay,6,40,0);
        hyde_park_time_2.set(currYear,currMonth,currDay,6,47,0);
        school_district_time.set(currYear,currMonth,currDay,6,55,0);
        education_time.set(currYear,currMonth,currDay,7,0,0);
        transit_time.set(currYear, currMonth, currDay, 7, 10, 0);

        times.elementAt(0).add(transit_time.getTime());
        times.elementAt(1).add(vet_sci_time.getTime());
        times.elementAt(3).add(education_time.getTime());
        times.elementAt(4).add(city_hall_time.getTime());
        times.elementAt(5).add(hyde_park_time_1.getTime());
        times.elementAt(5).add(hyde_park_time_2.getTime());
        times.elementAt(6).add(sky_view_time_1.getTime());
        times.elementAt(6).add(sky_view_time_2.getTime());
        times.elementAt(7).add(richmond_time.getTime());
        times.elementAt(8).add(civic_center_time.getTime());
        times.elementAt(9).add(school_district_time.getTime());

        for(int i = 0; i < 5; i++){
            vet_sci_time.add(Calendar.MINUTE, 45);
            city_hall_time.add(Calendar.MINUTE, 45);
            hyde_park_time_1.add(Calendar.MINUTE, 45);
            sky_view_time_1.add(Calendar.MINUTE, 45);
            richmond_time.add(Calendar.MINUTE, 45);
            civic_center_time.add(Calendar.MINUTE, 45);
            sky_view_time_2.add(Calendar.MINUTE, 45);
            hyde_park_time_2.add(Calendar.MINUTE, 45);
            school_district_time.add(Calendar.MINUTE, 45);
            education_time.add(Calendar.MINUTE, 45);
            transit_time.add(Calendar.MINUTE, 45);

            times.elementAt(0).add(transit_time.getTime());
            times.elementAt(1).add(vet_sci_time.getTime());
            times.elementAt(3).add(education_time.getTime());
            times.elementAt(4).add(city_hall_time.getTime());
            times.elementAt(5).add(hyde_park_time_1.getTime());
            times.elementAt(5).add(hyde_park_time_2.getTime());
            times.elementAt(6).add(sky_view_time_1.getTime());
            times.elementAt(6).add(sky_view_time_2.getTime());
            times.elementAt(7).add(richmond_time.getTime());
            times.elementAt(8).add(civic_center_time.getTime());
            times.elementAt(9).add(school_district_time.getTime());
        }
        for(int i = 0; i < 4; i++){
            vet_sci_time.add(Calendar.MINUTE, 90);
            city_hall_time.add(Calendar.MINUTE, 90);
            hyde_park_time_1.add(Calendar.MINUTE, 90);
            sky_view_time_1.add(Calendar.MINUTE, 90);
            richmond_time.add(Calendar.MINUTE, 90);
            civic_center_time.add(Calendar.MINUTE, 90);
            sky_view_time_2.add(Calendar.MINUTE, 90);
            hyde_park_time_2.add(Calendar.MINUTE, 90);
            school_district_time.add(Calendar.MINUTE, 90);
            education_time.add(Calendar.MINUTE, 90);
            transit_time.add(Calendar.MINUTE, 90);

            times.elementAt(0).add(transit_time.getTime());
            times.elementAt(1).add(vet_sci_time.getTime());
            times.elementAt(3).add(education_time.getTime());
            times.elementAt(4).add(city_hall_time.getTime());
            times.elementAt(5).add(hyde_park_time_1.getTime());
            times.elementAt(5).add(hyde_park_time_2.getTime());
            times.elementAt(6).add(sky_view_time_1.getTime());
            times.elementAt(6).add(sky_view_time_2.getTime());
            times.elementAt(7).add(richmond_time.getTime());
            times.elementAt(8).add(civic_center_time.getTime());
            times.elementAt(9).add(school_district_time.getTime());
        }
        for(int i = 0; i < 4; i++){
            vet_sci_time.add(Calendar.MINUTE, 45);
            city_hall_time.add(Calendar.MINUTE, 45);
            hyde_park_time_1.add(Calendar.MINUTE, 45);
            sky_view_time_1.add(Calendar.MINUTE, 45);
            richmond_time.add(Calendar.MINUTE, 45);
            civic_center_time.add(Calendar.MINUTE, 45);
            sky_view_time_2.add(Calendar.MINUTE, 45);
            hyde_park_time_2.add(Calendar.MINUTE, 45);
            school_district_time.add(Calendar.MINUTE, 45);
            education_time.add(Calendar.MINUTE, 45);
            transit_time.add(Calendar.MINUTE, 45);

            times.elementAt(0).add(transit_time.getTime());
            times.elementAt(1).add(vet_sci_time.getTime());
            times.elementAt(3).add(education_time.getTime());
            times.elementAt(4).add(city_hall_time.getTime());
            times.elementAt(5).add(hyde_park_time_1.getTime());
            times.elementAt(5).add(hyde_park_time_2.getTime());
            times.elementAt(6).add(sky_view_time_1.getTime());
            times.elementAt(6).add(sky_view_time_2.getTime());
            times.elementAt(7).add(richmond_time.getTime());
            times.elementAt(8).add(civic_center_time.getTime());
            times.elementAt(9).add(school_district_time.getTime());
        }
    }
}
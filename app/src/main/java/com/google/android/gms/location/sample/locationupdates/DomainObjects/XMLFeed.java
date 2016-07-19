package com.google.android.gms.location.sample.locationupdates.DomainObjects;

import java.util.List;

/**
 * @author Lancer521 on 7/19/2016 at 3:23 PM.
 */
public class XMLFeed {
  public Route CVTD_Bus_Route_v100;

  public class Route {
    public List<Bus> Bus;

    public class Bus {
      public int ID;
      public int BlockID;
      public int RouteID;
      public int BusNumber;
      public int RouteNumber;
      public String RouteDescription;
      public String RouteColor;
      public String TextColor;
      public GPSInfo GPSinfo;

      public class GPSInfo {
        public List<DateTime> datetime;

        public class DateTime {
          public double latitude;
          public double longitude;
          public double direction;
        }
      }
    }
  }
}
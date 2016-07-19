package com.google.android.gms.location.sample.locationupdates.DomainObjects;

/**
 * @author Lancer521 on 7/19/2016 at 4:33 PM.
 * https://developers.google.com/transit/gtfs/reference/stops-file
 */
public class Stops {
  /**
   * Contains an ID that uniquely identifies a stop or station.
   * Multiple routes may use the same stop. The stop_id is dataset unique.
   */
  public int stop_id;

  /**
   * Contains the name of a stop or station. Please use a name that people
   * will understand in the local and tourist vernacular.
   */
  public String stop_name;

  /**
   * Contains the latitude of a stop or station.
   * The field value must be a valid WGS 84 latitude.
   */
  public double stop_lat;

  /**
   * Contains the longitude of a stop or station.
   * The field value must be a valid WGS 84 longitude value from -180 to 180.
   */
  public double stop_lon;

  /**
   * Identifies whether this stop ID represents a stop or station.
   * If no location type is specified, or the location_type is blank, stop IDs
   * are treated as stops. Stations can have different properties from stops
   * when they are represented on a map or used in trip planning.

   The location type field can have the following values:

   0 or blank: Stop. A location where passengers board or disembark from a transit vehicle.
   1: Station. A physical structure or area that contains one or more stop.
   */
  public boolean location_type;

  /**
   * For stops that are physically located inside stations, this field identifies
   * the station associated with the stop. To use this field, stops.txt must also
   * contain a row where this stop ID is assigned location type=1.
   */
  public int parent_station; //stops physically located in stations

}

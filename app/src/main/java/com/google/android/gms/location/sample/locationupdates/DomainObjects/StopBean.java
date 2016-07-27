package com.google.android.gms.location.sample.locationupdates.DomainObjects;

import java.io.Serializable;

/**
 * @author Lancer521 on 7/19/2016 at 4:33 PM.
 * @link https://developers.google.com/transit/gtfs/reference/stops-file
 */
public class StopBean implements Serializable {

  /**
   * Contains an ID that uniquely identifies a stop or station.
   * Multiple routes may use the same stop. The stop_id is dataset unique.
   */
  private int stop_id;

  /**
   * Contains the name of a stop or station. Please use a name that people
   * will understand in the local and tourist vernacular.
   */
  private String stop_name;

  /**
   * Contains the latitude of a stop or station.
   * The field value must be a valid WGS 84 latitude.
   */
  private double stop_lat;

  /**
   * Contains the longitude of a stop or station.
   * The field value must be a valid WGS 84 longitude value from -180 to 180.
   */
  private double stop_lon;

  /**
   * Identifies whether this stop ID represents a stop or station.
   * If no location type is specified, or the location_type is blank, stop IDs
   * are treated as stops. Stations can have different properties from stops
   * when they are represented on a map or used in trip planning.
   * <p/>
   * The location type field can have the following values:
   * <p/>
   * 0 or blank: Stop. A location where passengers board or disembark from a transit vehicle.
   * 1: Station. A physical structure or area that contains one or more stop.
   */
  private boolean location_type;

  /**
   * For stops that are physically located inside stations, this field identifies
   * the station associated with the stop. To use this field, stops.txt must also
   * contain a row where this stop ID is assigned location type=1.
   */
  private int parent_station; //stops physically located in stations

  /**
   * UNUSED FIELDS that must be included for CSVBeanReader
   */
  private String stop_code;
  private String stop_desc;
  private String zone_id;
  private String zone_url;
  private String stop_timezone;
  private String wheelchair_boarding;


  /**
   * Constructors
   */
  public StopBean() {
  }

  public StopBean(int stop_id, String stop_name, double stop_lat, double stop_lon, boolean location_type, int parent_station) {
    this.stop_id = stop_id;
    this.stop_name = stop_name;
    this.stop_lat = stop_lat;
    this.stop_lon = stop_lon;
    this.location_type = location_type;
    this.parent_station = parent_station;
  }


  /**
   * Getters and Setters
   */

  public int getStop_id() {
    return stop_id;
  }

  public String getStop_name() {
    return stop_name;
  }

  public double getStop_lat() {
    return stop_lat;
  }

  public double getStop_lon() {
    return stop_lon;
  }

  public boolean getLocation_type() {
    return location_type;
  }

  public int getParent_station() {
    return parent_station;
  }

  public void setStop_id(int stop_id) {
    this.stop_id = stop_id;
  }

  public void setStop_name(String stop_name) {
    this.stop_name = stop_name;
  }

  public void setStop_lat(double stop_lat) {
    this.stop_lat = stop_lat;
  }

  public void setStop_lon(double stop_lon) {
    this.stop_lon = stop_lon;
  }

  public void setLocation_type(boolean location_type) {
    this.location_type = location_type;
  }

  public void setParent_station(int parent_station) {
    this.parent_station = parent_station;
  }

  public void setStop_code(String stop_code) {
    this.stop_code = stop_code;
  }

  public void setStop_desc(String stop_desc) {
    this.stop_desc = stop_desc;
  }

  public void setZone_id(String zone_id) {
    this.zone_id = zone_id;
  }

  public void setZone_url(String zone_url) {
    this.zone_url = zone_url;
  }

  public void setStop_timezone(String stop_timezone) {
    this.stop_timezone = stop_timezone;
  }

  public void setWheelchair_boarding(String wheelchair_boarding) {
    this.wheelchair_boarding = wheelchair_boarding;
  }

}

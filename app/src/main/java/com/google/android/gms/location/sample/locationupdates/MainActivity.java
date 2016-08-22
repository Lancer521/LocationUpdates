/**
 * Copyright 2014 Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the
 * License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.google.android.gms.location.sample.locationupdates;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.sample.locationupdates.DomainObjects.StopBean;
import com.google.android.gms.location.sample.locationupdates.DomainObjects.XMLFeed;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.StrNotNullOrEmpty;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Copyright 2016 Tylen Smith

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

/**
 * This app is built upon sample code provided by Google at:
 * http://developer.android.com/training/location/receive-location-updates.html
 *
 * The full source code is found at:
 * https://github.com/googlesamples/android-play-location/tree/master/LocationUpdates
 *
 * There have been extensive modification to this code, all of which are the work of
 * Tylen Smith.  Any other sources used have been cited where appropriate.
 *
 * Icon made by Freepik [http://www.freepik.com] from www.flaticon.com
 *
 * @author Lancer521
 */

/**
 * Getting Location Updates.
 *
 * Demonstrates how to use the Fused Location Provider API to get updates about a device's
 * location. The Fused Location Provider is part of the Google Play services location APIs.
 *
 * For a simpler example that shows the use of Google Play services to fetch the last known location
 * of a device, see
 * https://github.com/googlesamples/android-play-location/tree/master/BasicLocation.
 *
 * This sample uses Google Play services, but it does not require authentication. For a sample that
 * uses Google Play services for authentication, see
 * https://github.com/googlesamples/android-google-accounts/tree/master/QuickStart.
 */
public class MainActivity extends AppCompatActivity implements
    ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

  protected static final String LOG_TAG = MainActivity.class.toString();

  /**
   * The Package Name for global use
   */
  public static String PACKAGE_NAME;

  /**
   * The desired interval for location updates. Inexact. Updates may be more or less frequent.
   */
  public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

  /**
   * The fastest rate for active location updates. Exact. Updates will never be more frequent
   * than this value.
   */
  public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
      UPDATE_INTERVAL_IN_MILLISECONDS / 2;

  // Keys for storing activity state in the Bundle.
  protected final static String REQUESTING_LOCATION_UPDATES_KEY = "requesting-location-updates-key";
  protected final static String LOCATION_KEY = "location-key";
  protected final static String LAST_UPDATED_TIME_STRING_KEY = "last-updated-time-string-key";

  /**
   * Provides the entry point to Google Play services.
   */
  protected GoogleApiClient mGoogleApiClient;

  /**
   * Stores parameters for requests to the FusedLocationProviderApi.
   */
  protected LocationRequest mLocationRequest;

  /**
   * Represents a geographical location.
   */
  protected Location mCurrentLocation;

  // UI Widgets.
  protected Button mStartUpdatesButton;
  protected Button mStopUpdatesButton;
  protected TextView mLastUpdateTimeTextView;
  protected TextView mLatitudeTextView;
  protected TextView mLongitudeTextView;

  // Labels.
  protected String mLatitudeLabel;
  protected String mLongitudeLabel;
  protected String mLastUpdateTimeLabel;

  /**
   * Tracks the status of the location updates request. Value changes when the user presses the
   * Start Updates and Stop Updates buttons.
   */
  protected Boolean mRequestingLocationUpdates;

  /**
   * Time when the location was updated represented as a String.
   */
  protected String mLastUpdateTime;

  /**
   * Data members to hold bus route information
   */
  protected MyTaskParams myParameters = new MyTaskParams();
  protected List<StopBean> stops;

  private SharedPreferences settings = null;
  private SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
      restorePreferences();
    }
  };


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);

    PACKAGE_NAME = getApplicationContext().getPackageName();

    // Locate the UI widgets.
    mStartUpdatesButton = (Button) findViewById(R.id.start_updates_button);
    mStopUpdatesButton = (Button) findViewById(R.id.stop_updates_button);
    mLatitudeTextView = (TextView) findViewById(R.id.latitude_text);
    mLongitudeTextView = (TextView) findViewById(R.id.longitude_text);
    mLastUpdateTimeTextView = (TextView) findViewById(R.id.last_update_time_text);

    // Restore preferences
    restorePreferences();
    settings.registerOnSharedPreferenceChangeListener(listener);

    // Set labels.
    mLatitudeLabel = getResources().getString(R.string.latitude_label);
    mLongitudeLabel = getResources().getString(R.string.longitude_label);
    mLastUpdateTimeLabel = getResources().getString(R.string.last_update_time_label);

    mRequestingLocationUpdates = false;
    mLastUpdateTime = "";

    //Load stops
    //TODO: Load the stops directly into the database
    new LoadSchedule().execute(saveAssetsToDevice());

    //Get routes from XML feed
    new GetXMLFeedAsyncTask().execute();

    // Update values using data stored in the Bundle.
    updateValuesFromBundle(savedInstanceState);

    // Kick off the process of building a GoogleApiClient and requesting the LocationServices
    // API.
    buildGoogleApiClient();

  }

  /**
   * Updates fields based on data stored in the bundle.
   *
   * @param savedInstanceState The activity state saved in the Bundle.
   */
  private void updateValuesFromBundle(Bundle savedInstanceState) {
    Log.i(LOG_TAG, "Updating values from bundle");
    if (savedInstanceState != null) {
      // Update the value of mRequestingLocationUpdates from the Bundle, and make sure that
      // the Start Updates and Stop Updates buttons are correctly enabled or disabled.
      if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
        mRequestingLocationUpdates = savedInstanceState.getBoolean(
            REQUESTING_LOCATION_UPDATES_KEY);
      }

      // Update the value of mCurrentLocation from the Bundle and update the UI to show the
      // correct latitude and longitude.
      if (savedInstanceState.keySet().contains(LOCATION_KEY)) {
        // Since LOCATION_KEY was found in the Bundle, we can be sure that mCurrentLocation
        // is not null.
        mCurrentLocation = savedInstanceState.getParcelable(LOCATION_KEY);
      }

      // Update the value of mLastUpdateTime from the Bundle and update the UI.
      if (savedInstanceState.keySet().contains(LAST_UPDATED_TIME_STRING_KEY)) {
        mLastUpdateTime = savedInstanceState.getString(LAST_UPDATED_TIME_STRING_KEY);
      }
      updateUI();
    }
  }

  /**
   * Builds a GoogleApiClient. Uses the {@code #addApi} method to request the
   * LocationServices API.
   */
  protected synchronized void buildGoogleApiClient() {
    Log.i(LOG_TAG, "Building GoogleApiClient");
    mGoogleApiClient = new GoogleApiClient.Builder(this)
                           .addConnectionCallbacks(this)
                           .addOnConnectionFailedListener(this)
                           .addApi(LocationServices.API)
                           .build();
    createLocationRequest();
  }

  /**
   * Sets up the location request. Android has two location request settings:
   * {@code ACCESS_COARSE_LOCATION} and {@code ACCESS_FINE_LOCATION}. These settings control
   * the accuracy of the current location. This sample uses ACCESS_FINE_LOCATION, as defined in
   * the AndroidManifest.xml.
   * <p/>
   * When the ACCESS_FINE_LOCATION setting is specified, combined with a fast update
   * interval (5 seconds), the Fused Location Provider API returns location updates that are
   * accurate to within a few feet.
   * <p/>
   * These settings are appropriate for mapping applications that show real-time location
   * updates.
   */
  protected void createLocationRequest() {
    mLocationRequest = new LocationRequest();

    // Sets the desired interval for active location updates. This interval is
    // inexact. You may not receive updates at all if no location sources are available, or
    // you may receive them slower than requested. You may also receive updates faster than
    // requested if other applications are requesting location at a faster interval.
    mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

    // Sets the fastest rate for active location updates. This interval is exact, and your
    // application will never receive updates faster than this value.
    mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

    mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
  }

  /**
   * Handles the Start Updates button and requests start of location updates. Does nothing if
   * updates have already been requested.
   */
  public void startUpdatesButtonHandler(View view) {
    checkTransportMode();

    if (!mRequestingLocationUpdates) {
      myParameters.wasNotified = false;
      mRequestingLocationUpdates = true;
      startLocationUpdates();
    }
  }

  /**
   * Handles the Stop Updates button, and requests removal of location updates. Does nothing if
   * updates were not previously requested.
   */
  public void stopUpdatesButtonHandler(View view) {
    if (mRequestingLocationUpdates) {
      mRequestingLocationUpdates = false;
      stopLocationUpdates();
    }
  }

  /**
   * Save preferences using Shared Preferences
   */
  public void editPreferencesButtonHandler(View view) {
    Intent i = new Intent(this.getApplicationContext(), PrefsActivity.class);
    startActivity(i);
  }

  public void cvtdButtonHandler(View view) {
    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.cvtdbus.org/MIRouteMapsNSchedules/allroutes.php"));
    startActivity(i);
  }

  /**
   * Requests location updates from the FusedLocationApi.
   */
  protected void startLocationUpdates() {
    // The final argument to {@code requestLocationUpdates()} is a LocationListener
    // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
    LocationServices.FusedLocationApi.requestLocationUpdates(
        mGoogleApiClient, mLocationRequest, this);
  }

  /**
   * Updates the latitude, the longitude, and the last location time in the UI.
   */
  private void updateUI() {
    mLatitudeTextView.setText(String.format("%s: %f", mLatitudeLabel,
                                            mCurrentLocation.getLatitude()));
    mLongitudeTextView.setText(String.format("%s: %f", mLongitudeLabel,
                                             mCurrentLocation.getLongitude()));
    mLastUpdateTimeTextView.setText(String.format("%s: %s", mLastUpdateTimeLabel,
                                                  mLastUpdateTime));
  }

  /**
   * Removes location updates from the FusedLocationApi.
   */
  protected void stopLocationUpdates() {
    // It is a good practice to remove location requests when the activity is in a paused or
    // stopped state. Doing so helps battery performance and is especially
    // recommended in applications that request frequent location updates.

    // The final argument to {@code requestLocationUpdates()} is a LocationListener
    // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
    LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
  }

  @Override
  protected void onStart() {
    super.onStart();
    mGoogleApiClient.connect();
  }

  @Override
  public void onResume() {
    super.onResume();
    // Within {@code onPause()}, we pause location updates, but leave the
    // connection to GoogleApiClient intact.  Here, we resume receiving
    // location updates if the user has requested them.

    if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
      startLocationUpdates();
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
  }

  @Override
  protected void onStop() {
    super.onStop();
  }

  /**
   * Runs when a GoogleApiClient object successfully connects.
   */
  @Override
  public void onConnected(Bundle connectionHint) {
    Log.i(LOG_TAG, "Connected to GoogleApiClient");

    // If the initial location was never previously requested, we use
    // FusedLocationApi.getLastLocation() to get it. If it was previously requested, we store
    // its value in the Bundle and check for it in onCreate(). We
    // do not request it again unless the user specifically requests location updates by pressing
    // the Start Updates button.
    //
    // Because we cache the value of the initial location in the Bundle, it means that if the
    // user launches the activity,
    // moves to a new location, and then changes the device orientation, the original location
    // is displayed as the activity is re-created.
    if (mCurrentLocation == null) {
      mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
      mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
      updateUI();
    }

    // If the user presses the Start Updates button before GoogleApiClient connects, we set
    // mRequestingLocationUpdates to true (see startUpdatesButtonHandler()). Here, we check
    // the value of mRequestingLocationUpdates and if it is true, we start location updates.
    if (mRequestingLocationUpdates) {
      startLocationUpdates();
    }
  }

  /**
   * Callback that fires when the location changes.
   */
  @Override
  public void onLocationChanged(Location location) {
    myParameters.previousLocation = mCurrentLocation;
    myParameters.currentLocation = location;
    mCurrentLocation = location;
    mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
    updateUI();

    //create and execute Asynchronous process:
    new FindLocationAsyncTask(MainActivity.this).execute(myParameters);

    if (myParameters.wasNotified) {
      mRequestingLocationUpdates = false;
      stopLocationUpdates();
    }
  }

  @Override
  public void onConnectionSuspended(int cause) {
    // The connection to Google Play services was lost for some reason. We call connect() to
    // attempt to re-establish the connection.
    Log.i(LOG_TAG, "Connection suspended");
    mGoogleApiClient.connect();
  }

  @Override
  public void onConnectionFailed(ConnectionResult result) {
    // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
    // onConnectionFailed.
    Log.i(LOG_TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
  }

  /**
   * Stores activity data in the Bundle.
   */
  public void onSaveInstanceState(Bundle savedInstanceState) {
    savedInstanceState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY, mRequestingLocationUpdates);
    savedInstanceState.putParcelable(LOCATION_KEY, mCurrentLocation);
    savedInstanceState.putString(LAST_UPDATED_TIME_STRING_KEY, mLastUpdateTime);
    super.onSaveInstanceState(savedInstanceState);
  }

  private void restorePreferences() {
    settings = PreferenceManager.getDefaultSharedPreferences(this);
    //settings = getPreferences(MODE_PRIVATE);
    myParameters.minutes = Integer.parseInt(settings.getString("minutes_values", Integer.toString(myParameters.minutes)));
    myParameters.meters = myParameters.toMeters(Float.parseFloat(settings.getString("miles", Double.toString(myParameters.miles))));
    myParameters.temp_below = Integer.parseInt(settings.getString("temp_below_values", Integer.toString(myParameters.temp_below)));
    myParameters.temp_above = Integer.parseInt(settings.getString("temp_above_values", Integer.toString(myParameters.temp_above)));
    myParameters.walkingEnabled = settings.getBoolean("walking", myParameters.walkingEnabled);
    myParameters.bikingEnabled = settings.getBoolean("biking", myParameters.bikingEnabled);
  }

  private boolean checkTransportMode() {
    myParameters.getSpeed();
    return myParameters.currentSpeed < myParameters.BIKE_MAX_SPEED && myParameters.walkingEnabled && (myParameters.bikingEnabled || myParameters.currentSpeed < myParameters.BIKE_MIN_SPEED);
  }

  private File saveAssetsToDevice() {
    AssetManager assetManager = getAssets();
    String[] files = null;
    File stopFile = null;
    try {
      files = assetManager.list("");
    } catch (IOException ioe) {
      Log.e(LOG_TAG, ioe.getMessage(), ioe);
    }
    if (files != null) {
      for (String filename : files) {
        if (filename.equals("stops.txt")) {
          InputStream in = null;
          OutputStream out = null;
          try {
            in = assetManager.open(filename);
            File outFile = new File(getExternalFilesDir(null), filename);
            out = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
              out.write(buffer, 0, read);
            }
            stopFile = outFile;
          } catch (IOException ioe) {
            Log.e(LOG_TAG, ioe.getMessage(), ioe);
          } finally {
            if (in != null) {
              try {
                in.close();
              } catch (IOException ioe) {
                Log.e(LOG_TAG, ioe.getMessage());
              }
            }
            if (out != null) {
              try {
                out.close();
              } catch (IOException ioe) {
                Log.e(LOG_TAG, ioe.getMessage());
              }
            }
          }
        }
      }
    }
    return stopFile;
  }

  private class GetXMLFeedAsyncTask extends AsyncTask<Void, Void, Void> {

    private final OkHttpClient okClient = new OkHttpClient();
    private final Gson gson = new Gson();

    private XMLFeed routes;

    @Override
    protected Void doInBackground(Void... params) {

      try {
        Request request = new Request.Builder().url(getString(R.string.cvtd_feed_url)).build();
        Response response = okClient.newCall(request).execute();

        String xml = response.body().string();

        JSONObject jsonObj = XML.toJSONObject(xml);

        routes = gson.fromJson(jsonObj.toString(), XMLFeed.class);
        Log.d(LOG_TAG, "GETTING ROUTE FROM FEED AND CONVERTING TO JSON WAS SUCCESSFUL");

      } catch (IOException e) {
        Log.e(LOG_TAG, "IOException in getRoutesFromServer");
        e.printStackTrace();
      } catch (JSONException e) {
        Log.e(LOG_TAG, "JSONException in getRoutesFromServer");
        e.printStackTrace();
      }
      return null;
    }
  }

  //TODO: Load each stop directly into Database upon the creation/upgrade of the Database
  public class LoadSchedule extends AsyncTask<File, Void, Void> {

    @Override
    protected Void doInBackground(File... params) {
      List<StopBean> stopList = new ArrayList<>(); //Used more for testing at this point - will be unnecessary when database is implemented
      ICsvBeanReader beanReader;
      try {
        final CellProcessor[] processors = new CellProcessor[]{
            new ParseInt(), // stop_id
            new Optional(), // stop_code
            new StrNotNullOrEmpty(), // stop_name
            new Optional(), // stop_desc
            new ParseDouble(), // stop_lat
            new ParseDouble(), // stop_lon
            new Optional(), // zone_id
            new Optional(), // stop_url
            new Optional(new ParseBool()), // location_type
            new Optional(new ParseInt()), // parent_station
            new Optional(), // stop_timezone
            new Optional() // wheelchair_boarding
        };

        beanReader = new CsvBeanReader(new FileReader(params[0]), CsvPreference.STANDARD_PREFERENCE);

        String[] header = beanReader.getHeader(true);
        StopBean stop;

        while ((stop = beanReader.read(StopBean.class, header, processors)) != null) {
          stopList.add(stop);
        }

        beanReader.close();
      } catch (IOException ioe) {
        Log.e(LOG_TAG, "Error parsing CSV file");
      }
      return null;
    }
  }
}

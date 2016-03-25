package com.melapelapp;
/**
 * Created by Jonathan Gama on 1/4/2016.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
//import android.os.Build;
//import android.support.v4.content.ContextCompat;
import android.widget.TextView;

//import com.google.android.gms.maps.GoogleMap;


public class GeoLocationTracker implements LocationListener  {


    Context context;
//    //The minimum distance to change updates in meters
//    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 10 meters
//
//    //The minimum time beetwen updates in milliseconds
//    private static final long MIN_TIME_BW_UPDATES = 0;//1000 * 60 * 1; // 1 minute

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;//10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 3; // 1 minute

    private final static boolean forceNetwork = false;

    private static GeoLocationTracker instance = null;

    private LocationManager locationManager;
    public Location location;
    public double longitude;
    public double latitude;

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // location service availability depending on GPS or Network availability
    boolean locationServiceAvailable=false;

    /**
     * Singleton implementation
     * @return
     */
//    public static GeoLocationTracker getInstance(Context context)     {
//
//        if (instance == null) {
//            instance = new GeoLocationTracker(context);
//        }
//        return instance;
//    }

    /**
     * Local constructor
     */
    TextView textView;
    public GeoLocationTracker( Context context, TextView textView )     {
        this.context = context;
        this.textView=textView;
        initLocationService(context);
        //bjgl LogService.log("LocationService created");
    }


    public Location getLocation()
    {

        return this.location;
    }

    public void stop()
    {
        locationManager.removeUpdates(this);
        locationManager = null;
    }

    /**
     * Sets up location service after permissions is granted
     */
    @TargetApi(23)
    private void initLocationService(Context context) {


//        if ( Build.VERSION.SDK_INT >= 23 &&
//                ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                ContextCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//           // return  ;
//        }

        try   {
            this.longitude = 0.0;
            this.latitude = 0.0;
            this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            // Get GPS and network status
            this.isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            this.isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (forceNetwork) isGPSEnabled = false;

            if (!isNetworkEnabled && !isGPSEnabled)    {
                // cannot get location
                this.locationServiceAvailable = false;
            }
            //else
            {
                this.locationServiceAvailable = true;

                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if (locationManager != null)   {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        updateCoordinates();
                    }
                }//end if

                if (isGPSEnabled)  {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null)  {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        updateCoordinates();
                    }
                }
            }
        } catch (Exception ex)  {
            String msg;
            msg= "Error creating location service: " + ex.getMessage();
            msg = msg +"no mamaes";

        }
    }

    public void updateCoordinates()
    {
        //aqui va la actualizacion
    }
    @Override
    public void onLocationChanged(Location location)     {
        // do stuff here with location object
        initLocationService(this.context);

        this.textView.setText(location.getLatitude() + "@" + location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

//    public void fitBounds(GoogleMap map)
//    {
//        var markers = //some array;
//                var bounds = new google.maps.LatLngBounds();
//        for(i=0;i<markers.length;i++) {
//            bounds.extend(markers[i].getPosition());
//        }
//
//        map.fitBounds(bounds);
//    }

}
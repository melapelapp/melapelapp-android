package com.melapelapp.geolocation;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import com.melapelapp.commons.lang.Optional;

import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;

/**
 * Created by mcamacho on 4/2/16.
 */
public class LocationListenerStrategy implements LocationListener {
    private static final String TAG = "LocationPolicy";

    private static final int MAX_UPDATES = 4;

    protected TrackingListener listener;
    protected GeolocationTracker tracker;
    private int maxUpdates;
    private int updates = 0;

    LocationListenerStrategy(TrackingListener listener, GeolocationTracker tracker) {
        this.listener = listener;
        this.tracker = tracker;
        maxUpdates = MAX_UPDATES;
    }

    LocationListenerStrategy(TrackingListener listener, GeolocationTracker tracker, Optional<Integer> maxUpdates) {
        this(listener, tracker);
        this.maxUpdates = maxUpdates.orElse(MAX_UPDATES);
    }

    @Override
    public void onLocationChanged(Location location) {
        updates++;
        if (tracker.isBetterLocation(location, tracker.getLocation())) {
            tracker.setLocation(location);
            tracker.doWithLocation(location);
        }

        if (updates >= maxUpdates) {
            tracker.stop();
        }

        Log.d(TAG, "Update: " + updates);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i(TAG, "status changed");
    }

    @Override
    public void onProviderEnabled(String provider) {
        if (provider.equals(GPS_PROVIDER)) {
            Log.i(TAG, "GPS enabled");
            tracker.setGPSEnabled(true);
            tracker.requestLocation();
        } else if (provider.equals(NETWORK_PROVIDER)) {
            Log.i(TAG, "Network enabled");
            tracker.setNetworkEnabled(true);
            tracker.requestLocation();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        if (provider.equals(GPS_PROVIDER)) {
            Log.i(TAG, "GPS disabled");
            tracker.setGPSEnabled(false);
        } else if (provider.equals(NETWORK_PROVIDER)) {
            Log.i(TAG, "Network disabled");
            tracker.setNetworkEnabled(false);
        }
    }

    protected Object getTrackingOjbect(Location location) {
        return location;
    }

}
package com.melapelapp.geolocation;
/**
 * Created by Jonathan Gama on 1/4/2016.
 */

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;

public class GeolocationTracker {
    private static final String TAG = "GeolocationTracker";

    // The minimum distance to change Updates in meters
    private static final float DEFAULT_MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    // The minimum time between updates in milliseconds
    private static final long DEFAULT_MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    private static final int TWO_MINUTES = 1000 * 60 * 2;

    private final static boolean forceNetwork = false;

    protected Context context;
    protected LocationManager locationManager;
    protected LocationListenerStrategy locationPolicy;
    protected TrackingListener listener;

    protected Location location;

    private long minTime;
    private float minDistance;

    private boolean isGPSEnabled = false;
    private boolean isNetworkEnabled = false;

    public GeolocationTracker(Context context, TrackingListener listener) {
        this.listener = listener;
        this.context = context;
        this.minTime = DEFAULT_MIN_TIME_BW_UPDATES;
        this.minDistance = DEFAULT_MIN_DISTANCE_CHANGE_FOR_UPDATES;

        locationPolicy = new LocationListenerStrategy(listener, this);
    }

    public GeolocationTracker(Context context, TrackingListener listener, Settings settings) {
        this(context, listener);
        this.minTime = settings.minTime.orElse(DEFAULT_MIN_TIME_BW_UPDATES);
        this.minDistance = settings.minDistance.orElse(DEFAULT_MIN_DISTANCE_CHANGE_FOR_UPDATES);

        this.locationPolicy = new LocationListenerStrategy(listener, this, settings.maxUpdates);
    }

    public void start() {
        if (!shouldInit(context)) {
            return;
        }

        try {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            if (locationManager == null) {
                Log.i(TAG, "Could not get LocationManager");
                return;
            }

            // Get GPS and network status
            isGPSEnabled = locationManager.isProviderEnabled(GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(NETWORK_PROVIDER);

            if (forceNetwork) isGPSEnabled = false;

            if (!isNetworkEnabled && !isGPSEnabled) {
                return; // cannot get location
            }

            locationManager.requestLocationUpdates(GPS_PROVIDER, minTime, minDistance, locationPolicy);
            locationManager.requestLocationUpdates(NETWORK_PROVIDER, minTime, minDistance, locationPolicy);

            requestLocation();

        } catch (Exception e)  {
            Log.i(TAG, "Error creating location service: " + e.getMessage());
        }
    }

    protected boolean shouldInit(Context context) {
        /*
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           // return  ;
        }
        */
        return true;
    }

    protected void requestLocation() {
        Location firstLocation = null;

        if (isGPSEnabled)  {
            firstLocation = locationManager.getLastKnownLocation(GPS_PROVIDER);
        } else if (firstLocation == null && isNetworkEnabled) {
            firstLocation = locationManager.getLastKnownLocation(NETWORK_PROVIDER);
        }

        if (firstLocation != null) {
            locationPolicy.onLocationChanged(firstLocation);
        }
    }

    protected void doWithLocation() {
        listener.doWithTracking(location);
    }

    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /** Checks whether two providers are the same */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

    public void stop() {
        locationManager.removeUpdates(locationPolicy);
        listener.onStop();
    }

    public boolean isEnabled() {
        return isGPSEnabled || isNetworkEnabled;
    }

    Location getLocation() {
        return location;
    }

    void setLocation(Location location) {
        this.location = location;
    }

    void setGPSEnabled(boolean isGPSEnabled) {
        this.isGPSEnabled = isGPSEnabled;
    }

    void setNetworkEnabled(boolean isNetworkEnabled) {
        this.isNetworkEnabled = isNetworkEnabled;
    }

}
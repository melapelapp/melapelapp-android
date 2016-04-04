package com.melapelapp.geolocation;

/**
 * Created by mcamacho on 3/28/16.
 */
public interface TrackingListener<T> {

    public void doWithTracking(T trackedObject);

    public void onStop();
}

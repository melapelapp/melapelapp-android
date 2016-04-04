package com.melapelapp.geolocation;

/**
 * Created by Jonathan Gama on 1/4/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import com.melapelapp.commons.android.content.ServiceContextHelper;
import com.melapelapp.domain.AddressParcel;

import static com.melapelapp.geolocation.AddressFetcherService.Constants.*;

public class GeolocationAddressTracker extends GeolocationTracker {
    private static final String TAG = "GeolocationAddressTracker";

    public GeolocationAddressTracker(Context context, TrackingListener listener) {
        super(context, listener);
    }

    public GeolocationAddressTracker(Context context, TrackingListener listener, Settings settings) {
        super(context, listener, settings);
    }

    protected boolean shouldInit(Context context) {
        if (!Geocoder.isPresent()) {
            Log.i(TAG, "Tracker will not initiate: geocoder not available.");
            return false;
        }

        if (!ServiceContextHelper.isOnline(context)) {
            Log.i(TAG, "Tracker will not initiate: not online.");
            return false;
        }

        return true;
    }

    @Override
    protected void doWithLocation(Location location) {
        final Intent intent = new Intent(context, AddressFetcherService.class);
        intent.putExtra(RECEIVER, new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode == SUCCESS_RESULT) {
                    listener.doWithTracking( ((AddressParcel) resultData.getParcelable(RESULT_DATA_KEY)).getAddress() );
                }
            }
        });
        intent.putExtra(EXTRA_LOCATION_DATA, location);
        context.startService(intent);
    }

}
package com.melapelapp.geolocation;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import com.melapelapp.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.melapelapp.geolocation.AddressFetcherService.Constants.*;

/**
 * Created by mcamacho on 4/3/16.
 */
public class AddressFetcherService extends IntentService {
    private static final String TAG = "AddressFetcherService";

    public AddressFetcherService() {
        super(TAG);
    }

    @Override
    public void setIntentRedelivery(boolean enabled) {
        super.setIntentRedelivery(enabled);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Location location = intent.getParcelableExtra(EXTRA_LOCATION_DATA);
        ResultReceiver resultReceiver = intent.getParcelableExtra(RECEIVER);

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        String errorMessage = "";

        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            errorMessage = getString(R.string.no_address_found);
            Log.e(TAG, "Could not get address from location ", e);
        } catch (IllegalArgumentException illegalArgumentException) {
            errorMessage = "Invalid values: Lat=" + location.getLatitude() + ", Long=" + location.getLongitude();
            Log.e(TAG, errorMessage, illegalArgumentException);
        }

        Bundle bundle = new Bundle();
        // Handle case where no address was found.
        if (addressNotFound(addresses)) {
            bundle.putString(Constants.RESULT_DATA_KEY, errorMessage);
            resultReceiver.send(FAILURE_RESULT, bundle);
        } else {
            Address address = addresses.get(0);

            Log.i(TAG, "Address found");
            bundle.putParcelable(RESULT_DATA_KEY, address);
            resultReceiver.send(SUCCESS_RESULT, bundle);
        }
    }

    private boolean addressNotFound(List<Address> addresses) {
        return addresses == null || addresses.size() == 0;
    }

    final static public class Constants {
        public static final int SUCCESS_RESULT = 0;
        public static final int FAILURE_RESULT = 1;

        public static final String PACKAGE_NAME = "com.melapelaps.geolocation.address";
        public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
        public static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";
        public static final String EXTRA_LOCATION_DATA = PACKAGE_NAME + ".EXTRA_LOCATION_DATA";
    }
}



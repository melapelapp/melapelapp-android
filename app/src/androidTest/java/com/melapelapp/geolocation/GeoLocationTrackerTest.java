package com.melapelapp.geolocation;

import android.location.Location;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class GeoLocationTrackerTest {

    //private GeolocationTracker locationTracker;
    private SimpleGeolocationTracker locationTracker;

    @Before public void setup() {
        //locationTracker = new GeolocationTracker(InstrumentationRegistry.getTargetContext(), null);
        locationTracker = new SimpleGeolocationTracker(InstrumentationRegistry.getTargetContext(), null);
    }

    @Test
    public void testGetLocation() throws Exception {
        Location location = locationTracker.getLocation();

        assertNotNull(location);
    }
}
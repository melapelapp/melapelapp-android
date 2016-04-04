package com.melapelapp.geolocation;

import com.melapelapp.commons.lang.Optional;

/**
 * Created by mcamacho on 4/4/16.
 */
public class LocationSettingsBuilder {
    Settings settings;

    public static LocationSettingsBuilder newInstance() {
        return new LocationSettingsBuilder(new Settings());
    }

    public LocationSettingsBuilder(Settings settings) {
        this.settings = settings;
    }

    public LocationSettingsBuilder withMinTime(long minTime) {
        settings.minTime = Optional.of(minTime);
        return this;
    }

    public LocationSettingsBuilder withMinDistance(float minDistance) {
        settings.minDistance = Optional.of(minDistance);
        return this;
    }

    public LocationSettingsBuilder withMaxUpdates(int maxUpdates) {
        settings.maxUpdates = Optional.of(maxUpdates);
        return this;
    }

    public Settings build() {
        return settings;
    }
}

class Settings {
    Optional<Long> minTime = Optional.empty();
    Optional<Float> minDistance = Optional.empty();
    Optional<Integer> maxUpdates = Optional.empty();
}


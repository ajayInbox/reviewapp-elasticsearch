package com.aj.hotel.services.impl;

import com.aj.hotel.domain.GeoLocation;
import com.aj.hotel.domain.entities.Address;
import com.aj.hotel.services.GeoLocationService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GeoLocationServiceImpl implements GeoLocationService {

    private static final float MAX_LATITUDE = 76.2f;
    private static final float MIN_LATITUDE = 75.2f;
    private static final float MAX_LANGITUDE = 77.2f;
    private static final float MIN_LANGITUDE = 73.2f;

    @Override
    public GeoLocation geoLocate(Address address) {
        Random random = new Random();

        double latitude = MAX_LATITUDE + random.nextDouble() * (MAX_LATITUDE-MIN_LATITUDE);
        double langitode = MAX_LANGITUDE + random.nextDouble() * (MAX_LATITUDE-MIN_LATITUDE);

        GeoLocation geoLocation = GeoLocation.builder()
                .latitude(latitude)
                .longitude(langitode)
                .build();

        return geoLocation;
    }
}

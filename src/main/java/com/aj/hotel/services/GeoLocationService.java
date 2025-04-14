package com.aj.hotel.services;

import com.aj.hotel.domain.GeoLocation;
import com.aj.hotel.domain.entities.Address;

public interface GeoLocationService {

    GeoLocation geoLocate(Address address);

}

package com.aj.hotel.domain.dtos;

import java.util.ArrayList;
import java.util.List;

public class HotelDto {

    private String id;

    private String hotelName;

    private String cuisineType;

    private String contactInfo;

    private Float averageRatings;

    private GeoPointDto geoLocation;

    private AddressDto address;

    private OperatingHoursDto operatingHours;

    private List<ReviewDto> reviews = new ArrayList<>();

    private List<ImageDto> images = new ArrayList<>();

    private AppUserDto createdBy;
}

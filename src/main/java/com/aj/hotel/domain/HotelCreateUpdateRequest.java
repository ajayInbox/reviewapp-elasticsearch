package com.aj.hotel.domain;

import com.aj.hotel.domain.entities.Address;
import com.aj.hotel.domain.entities.OperatingHours;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelCreateUpdateRequest {

    private String hotelName;
    private String cuisineType;
    private String contactInfo;
    private Address address;
    private OperatingHours operatingHours;
    private List<String> images;

}

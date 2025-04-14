package com.aj.hotel.domain.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class HotelCreateUpdateRequestDto {

    @NotBlank(message = "hotel name should be provided")
    private String hotelName;

    @NotBlank(message = "cuisine type should be provided")
    private String cuisineType;

    @NotBlank(message = "contact info should be provided")
    private String contactInfo;

    @Valid
    private AddressDto addressDto;

    @Valid
    private OperatingHoursDto operatingHoursDto;

    @Min(value = 1)
    private List<String> images;
}

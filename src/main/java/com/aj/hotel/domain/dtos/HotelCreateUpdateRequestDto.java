package com.aj.hotel.domain.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelCreateUpdateRequestDto {

    //@NotBlank(message = "hotel name should be provided")
    private String hotelName;

    //@NotBlank(message = "cuisine type should be provided")
    private String cuisineType;

    //@NotBlank(message = "contact info should be provided")
    private String contactInfo;

    @Valid
    private AddressDto address;

    @Valid
    private OperatingHoursDto operatingHours;

    @Size(min = 1)
    private List<String> images;
}

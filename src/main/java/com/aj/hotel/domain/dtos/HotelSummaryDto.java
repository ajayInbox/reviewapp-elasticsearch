package com.aj.hotel.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelSummaryDto {

    private String id;
    private String hotelName;
    private String cuisineType;
    private Float averageRatings;
    private Integer totalReviews;
    private AddressDto addressDto;
    private List<ImageDto> images;

}

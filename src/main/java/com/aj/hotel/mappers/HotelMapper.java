package com.aj.hotel.mappers;

import com.aj.hotel.domain.HotelCreateUpdateRequest;
import com.aj.hotel.domain.dtos.GeoPointDto;
import com.aj.hotel.domain.dtos.HotelCreateUpdateRequestDto;
import com.aj.hotel.domain.dtos.HotelDto;
import com.aj.hotel.domain.dtos.HotelSummaryDto;
import com.aj.hotel.domain.entities.Hotel;
import com.aj.hotel.domain.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelMapper {

    HotelCreateUpdateRequest toHotelCreateUpdateRequest(HotelCreateUpdateRequestDto dto);

    HotelDto toHotelDto(Hotel hotel);

    @Mapping(target = "latitude", expression = "java(geoPoint.getLat())")
    @Mapping(target = "longitude", expression = "java(geoPoint.getLon())")
    GeoPointDto toGeoPointDto(GeoPoint geoPoint);

    @Mapping(source = "reviews", target = "totalReviews", qualifiedByName = "populateTotalReviews")
    HotelSummaryDto toHotelSummeryDto(Hotel hotel);

    @Named("populateTotalReviews")
    default Integer populateTotalReviews(List<Review> reviews){
        return reviews.size();
    }
}

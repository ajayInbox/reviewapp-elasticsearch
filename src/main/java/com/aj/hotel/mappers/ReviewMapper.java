package com.aj.hotel.mappers;

import com.aj.hotel.domain.ReviewCreateUpdateRequest;
import com.aj.hotel.domain.dtos.ReviewCreateUpdateRequestDto;
import com.aj.hotel.domain.dtos.ReviewDto;
import com.aj.hotel.domain.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {

    ReviewCreateUpdateRequest toReviewCreateUpdateRequest(ReviewCreateUpdateRequestDto dto);

    ReviewDto toDto(Review review);

}

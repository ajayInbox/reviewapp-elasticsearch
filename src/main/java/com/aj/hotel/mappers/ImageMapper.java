package com.aj.hotel.mappers;

import com.aj.hotel.domain.dtos.ImageDto;
import com.aj.hotel.domain.entities.Image;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ImageMapper {

    ImageDto toDto(Image image);

}

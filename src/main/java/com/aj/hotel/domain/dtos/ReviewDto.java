package com.aj.hotel.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDto {

    private String id;

    private String content;

    private Integer rating;

    private LocalDateTime postedDate;

    private LocalDateTime lastEdited;

    private List<ImageDto> images = new ArrayList<>();

    private AppUserDto postedBy;

}

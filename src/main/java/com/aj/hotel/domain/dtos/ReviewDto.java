package com.aj.hotel.domain.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReviewDto {

    private String id;

    private String content;

    private Integer rating;

    private LocalDateTime postedDate;

    private LocalDateTime lastEdited;

    private List<ImageDto> images = new ArrayList<>();

    private AppUserDto postedBy;

}

package com.aj.hotel.domain.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewCreateUpdateRequestDto {

    @NotBlank(message = "content should be provided")
    private String content;

    @NotNull(message = "rating should be provided")
    @Min(value = 1, message = "rating should be between 1 to 5")
    @Max(value = 5, message = "rating should be between 1 to 5")
    private Integer rating;

    private List<String> images = new ArrayList<>();

}

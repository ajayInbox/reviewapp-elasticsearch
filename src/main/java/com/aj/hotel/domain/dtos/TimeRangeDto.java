package com.aj.hotel.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeRangeDto {

    @NotBlank(message = "open time must be provided")
    private String openTime;

    @NotBlank(message = "open time must be provided")
    private String closeTime;
}

package com.aj.hotel.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {

    @NotBlank(message = "plot number is required")
    private String plotNumber;

    @NotBlank(message = "street name is required")
    private String streetName;

    @NotBlank(message = "city name is required")
    private String city;

    @NotBlank(message = "pincode is required")
    private String pinCode;

    @NotBlank(message = "state name is required")
    private String state;

    @NotBlank(message = "country name is required")
    private String country;

}

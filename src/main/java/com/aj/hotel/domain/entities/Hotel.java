package com.aj.hotel.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.ArrayList;
import java.util.List;

@Document(indexName = "hotels")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hotel {

    @Id
    @Field(type = FieldType.Keyword)
    private String id;

    @Field(type = FieldType.Text)
    private String hotelName;

    @Field(type = FieldType.Text)
    private String cuisineType;

    @Field(type = FieldType.Keyword)
    private String contactInfo;

    @Field(type = FieldType.Float)
    private Float averageRatings;

    @GeoPointField
    private GeoPoint geoLocation;

    @Field(type = FieldType.Nested)
    private Address address;

    @Field(type = FieldType.Nested)
    private OperatingHours operatingHours;

    @Field(type = FieldType.Nested)
    private List<Review> reviews = new ArrayList<>();

    @Field(type = FieldType.Nested)
    private List<Image> images = new ArrayList<>();

    @Field(type = FieldType.Nested)
    private AppUser createdBy;
}

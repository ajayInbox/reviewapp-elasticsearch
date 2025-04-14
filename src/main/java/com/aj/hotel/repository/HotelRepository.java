package com.aj.hotel.repository;

import com.aj.hotel.domain.entities.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends ElasticsearchRepository<Hotel, String> {

    Page<Hotel> findByAverageRatingGreaterThanEqual(Float averageRatings, Pageable pageable);

    @Query("{"+
            " \"bool\": {" +
            " \"must\": ["+
            " { \"range\" :{ \"AverageRating\" : { \"gte\" : ?1}}}" +
            " ]," +
            " \"should\" : [" +
            " {\"fuzzy\" : {\"hotelName\" : {\"value\" : \"?0\", \"fuzziness\": \"AUTO\"}}}," +
            " {\"fuzzy\" : {\"cuisineType\" : {\"value\" : \"?0\", \"fuzziness\": \"AUTO\"}}}," +
            " ]," +
            " \"minimum_should_match\": 1" +
            " }" +
            "}" +
            "}"
    )
    Page<Hotel> findByQueryAndMinRating(String query, Float minRating, Pageable pageable);

    @Query("{"+
            " \"bool\": {" +
            " \"must\": [" +
            " {\"geo_distance\" :{" +
            " \"distance\": \"?2km\"," +
            " \"geoLocation\" :{" +
            " \"lat\": ?0," +
            " \"long\": ?1" +
            " }" +
            " }}" +
            " ]" +
            " }" +
            " }"
    )
    Page<Hotel> findByLocationNear(
            Float latitude,
            Float longitude,
            Float radiusKm,
            Pageable pageable
    );
}

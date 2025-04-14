package com.aj.hotel.services;

import com.aj.hotel.domain.HotelCreateUpdateRequest;
import com.aj.hotel.domain.entities.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface HotelService {

    Hotel createHotel(HotelCreateUpdateRequest request);

    Page<Hotel> searchHotels(
            String query,
            Float minRating,
            Float latitude,
            Float longitude,
            Float radiusKm,
            Pageable pageable
    );

    Optional<Hotel> getHotelById(String id);

    Hotel updateHotel(String id, HotelCreateUpdateRequest request);

    void deleteHotel(String id);

}

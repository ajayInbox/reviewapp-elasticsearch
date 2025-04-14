package com.aj.hotel.services.impl;

import com.aj.hotel.domain.GeoLocation;
import com.aj.hotel.domain.HotelCreateUpdateRequest;
import com.aj.hotel.domain.entities.Address;
import com.aj.hotel.domain.entities.Hotel;
import com.aj.hotel.domain.entities.Image;
import com.aj.hotel.exceptions.HotelNotFoundException;
import com.aj.hotel.repository.HotelRepository;
import com.aj.hotel.services.GeoLocationService;
import com.aj.hotel.services.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final GeoLocationService geoLocationService;

    @Override
    public Hotel createHotel(HotelCreateUpdateRequest request) {
        Address address = request.getAddress();
        GeoLocation geoLocation = geoLocationService.geoLocate(address);
        GeoPoint geoPoint = new GeoPoint(geoLocation.getLatitude(), geoLocation.getLongitude());

        List<Image> images = request.getImages().stream().map(
                imageUrl -> Image.builder()
                        .url(imageUrl)
                        .uploadTime(LocalDateTime.now())
                        .build()
                ).toList();

        Hotel newHotel = Hotel.builder()
                .hotelName(request.getHotelName())
                .cuisineType(request.getCuisineType())
                .contactInfo(request.getContactInfo())
                .address(request.getAddress())
                .geoLocation(geoPoint)
                .operatingHours(request.getOperatingHours())
                .images(images)
                .averageRatings(0f)
                .build();

        return hotelRepository.save(newHotel);
    }

    @Override
    public Page<Hotel> searchHotels(String query,
                                    Float minRating,
                                    Float latitude,
                                    Float longitude,
                                    Float radiusKm,
                                    Pageable pageable) {
        if(minRating != null && (query == null || query.isEmpty())){
            return hotelRepository.findByAverageRatingGreaterThanEqual(minRating, pageable);
        }

        Float minRatingValue = minRating == null ? 0f : minRating;

        if(query != null && !query.trim().isEmpty()){
            return hotelRepository.findByQueryAndMinRating(query, minRatingValue, pageable);
        }

        if(latitude != null && longitude != null && radiusKm != null){
            return hotelRepository.findByLocationNear(latitude, longitude, radiusKm, pageable);
        }

        return hotelRepository.findAll(pageable);
    }

    @Override
    public Optional<Hotel> getHotelById(String id) {
        return hotelRepository.findById(id);
    }

    @Override
    public Hotel updateHotel(String id, HotelCreateUpdateRequest request) {
        Hotel hotel = getHotelById(id).orElseThrow(
                () -> new HotelNotFoundException("Hotel not found")
        );

        GeoLocation geoLocation = geoLocationService.geoLocate(request.getAddress());
        GeoPoint geoPoint = new GeoPoint(geoLocation.getLatitude(), geoLocation.getLongitude());
        List<Image> images = request.getImages().stream().map(
                imageUrl -> Image.builder()
                        .url(imageUrl)
                        .uploadTime(LocalDateTime.now())
                        .build()
        ).toList();

        hotel.setHotelName(request.getHotelName());
        hotel.setAddress(request.getAddress());
        hotel.setCuisineType(request.getCuisineType());
        hotel.setContactInfo(request.getContactInfo());
        hotel.setGeoLocation(geoPoint);
        hotel.setOperatingHours(request.getOperatingHours());
        hotel.setImages(images);

        return hotelRepository.save(hotel);
    }

    @Override
    public void deleteHotel(String id) {
        hotelRepository.deleteById(id);
    }
}

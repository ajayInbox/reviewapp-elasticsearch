package com.aj.hotel.controllers;

import com.aj.hotel.domain.HotelCreateUpdateRequest;
import com.aj.hotel.domain.dtos.HotelCreateUpdateRequestDto;
import com.aj.hotel.domain.dtos.HotelDto;
import com.aj.hotel.domain.dtos.HotelSummaryDto;
import com.aj.hotel.domain.entities.Hotel;
import com.aj.hotel.mappers.HotelMapper;
import com.aj.hotel.services.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelService hotelService;
    private final HotelMapper hotelMapper;


    @PostMapping
    public ResponseEntity<HotelDto> createHotel(@Valid @RequestBody HotelCreateUpdateRequestDto dto){
        HotelCreateUpdateRequest request = hotelMapper.toHotelCreateUpdateRequest(dto);
        Hotel newHotel = hotelService.createHotel(request);
        HotelDto response = hotelMapper.toHotelDto(newHotel);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<HotelSummaryDto>> searchHotel(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) float minRating,
            @RequestParam(required = false) float latitude,
            @RequestParam(required = false) float longitude,
            @RequestParam(required = false) float radiusKm,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        Page<Hotel> results = hotelService.searchHotels(q, minRating, latitude,
                longitude, radiusKm, PageRequest.of(page-1, size));

        return new ResponseEntity<>(results.map(hotelMapper::toHotelSummeryDto), HttpStatus.OK);
    }

    @GetMapping(path = "/{hotel_id}")
    public ResponseEntity<HotelDto> getHotel(@PathVariable("hotel_id") String hotelId){
        return hotelService.getHotelById(hotelId)
                .map(hotel -> ResponseEntity.ok(hotelMapper.toHotelDto(hotel)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{hotel_id}")
    public ResponseEntity<HotelDto> updateHotel(@PathVariable("hotel_id") String id,
                                                @Valid @RequestBody HotelCreateUpdateRequestDto requestDto){
        HotelCreateUpdateRequest request = hotelMapper.toHotelCreateUpdateRequest(requestDto);
        Hotel updatedHotel = hotelService.updateHotel(id, request);
        return ResponseEntity.ok(hotelMapper.toHotelDto(updatedHotel));
    }

    @DeleteMapping(path = "/{hotel_id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable("hotel_id") String id){
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }
}

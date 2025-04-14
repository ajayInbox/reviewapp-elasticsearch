package com.aj.hotel.controllers;

import com.aj.hotel.domain.ReviewCreateUpdateRequest;
import com.aj.hotel.domain.dtos.ReviewCreateUpdateRequestDto;
import com.aj.hotel.domain.dtos.ReviewDto;
import com.aj.hotel.domain.entities.AppUser;
import com.aj.hotel.domain.entities.Review;
import com.aj.hotel.mappers.ReviewMapper;
import com.aj.hotel.services.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotels/{hotelId}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    @PostMapping
    public ResponseEntity<ReviewDto> createReview(
            @PathVariable String hotelId,
            @Valid @RequestBody ReviewCreateUpdateRequestDto requestDto,
            @AuthenticationPrincipal Jwt jwt
            ){
        ReviewCreateUpdateRequest request = reviewMapper.toReviewCreateUpdateRequest(requestDto);
        AppUser user = jwtToUser(jwt);
        Review review = reviewService.createReview(user, hotelId, request);
        return ResponseEntity.ok(reviewMapper.toDto(review));
    }

    @GetMapping
    public ResponseEntity<Page<ReviewDto>> getAllReviews(@PathVariable String hotelId,
                                         @PageableDefault(
                                                 page = 0,
                                                 size = 20,
                                                 sort = "postedDate",
                                                 direction = Sort.Direction.DESC
                                         )Pageable pageable){
        return ResponseEntity.ok(
                reviewService.getAllReviews(hotelId, pageable)
                        .map(reviewMapper::toDto)
        );
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> getReview(@PathVariable String hotelId,
                                               @PathVariable String reviewId){
        return reviewService.getReview(hotelId, reviewId)
                .map( review -> ResponseEntity.ok(reviewMapper.toDto(review)))
                .orElse(ResponseEntity.notFound().build());

    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable String hotelId,
                                                  @PathVariable String reviewId,
                                                  @Valid @RequestBody ReviewCreateUpdateRequestDto requestDto,
                                                  @AuthenticationPrincipal Jwt jwt){
        ReviewCreateUpdateRequest request = reviewMapper.toReviewCreateUpdateRequest(requestDto);
        AppUser user = jwtToUser(jwt);
        Review review = reviewService.updateReview(user, hotelId, reviewId, request);
        return ResponseEntity.ok(reviewMapper.toDto(review));
    }

    private AppUser jwtToUser(Jwt jwt){
        return AppUser.builder()
                .id(jwt.getSubject())
                .userName(jwt.getClaimAsString("preferred_username"))
                .givenName(jwt.getClaimAsString("given_name"))
                .build();
    }
}

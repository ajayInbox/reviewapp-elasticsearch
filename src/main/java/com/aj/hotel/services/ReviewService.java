package com.aj.hotel.services;

import com.aj.hotel.domain.ReviewCreateUpdateRequest;
import com.aj.hotel.domain.entities.AppUser;
import com.aj.hotel.domain.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReviewService {

    Review createReview(AppUser author, String hotelId, ReviewCreateUpdateRequest request);

    Page<Review> getAllReviews(String hotelId, Pageable pageable);

    Optional<Review> getReview(String hotelId, String reviewId);

    Review updateReview(AppUser user, String hotelId, String reviewId, ReviewCreateUpdateRequest request);

}

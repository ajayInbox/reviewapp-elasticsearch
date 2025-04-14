package com.aj.hotel.services.impl;

import com.aj.hotel.domain.ReviewCreateUpdateRequest;
import com.aj.hotel.domain.entities.AppUser;
import com.aj.hotel.domain.entities.Hotel;
import com.aj.hotel.domain.entities.Image;
import com.aj.hotel.domain.entities.Review;
import com.aj.hotel.exceptions.HotelNotFoundException;
import com.aj.hotel.exceptions.ReviewNotAllowedException;
import com.aj.hotel.exceptions.ReviewNotFoundException;
import com.aj.hotel.repository.HotelRepository;
import com.aj.hotel.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final HotelRepository hotelRepository;

    @Override
    public Review createReview(AppUser author, String hotelId, ReviewCreateUpdateRequest request) {
        Hotel hotel = getHotel(hotelId);

        boolean reviewExist = hotel.getReviews().stream().anyMatch(
                review -> review.getPostedBy().getId().equals(author.getId())
        );
        if(reviewExist){
            throw new ReviewNotAllowedException("Review already exist with given user "+author.getUserName());
        }

        LocalDateTime now = LocalDateTime.now();
        List<Image> images = request.getImages().stream().map(
                image -> Image.builder()
                        .url(image)
                        .uploadTime(now)
                        .build()
        ).toList();

        String newReviewId = UUID.randomUUID().toString();
        Review review = Review.builder()
                .id(newReviewId)
                .content(request.getContent())
                .images(images)
                .postedDate(now)
                .lastEdited(now)
                .postedBy(author)
                .build();

        hotel.getReviews().add(review);
        updateAverageRating(hotel);
        Hotel savedHotel = hotelRepository.save(hotel);
        return savedHotel.getReviews().stream().filter(
                rev -> rev.getId().equals(newReviewId))
                .findFirst()
                .orElseThrow(()-> new RuntimeException("error occurred during retrieval of review created"));
    }

    @Override
    public Page<Review> getAllReviews(String hotelId, Pageable pageable) {
        Hotel hotel = getHotel(hotelId);
        List<Review> reviews = hotel.getReviews();

        Sort sort = pageable.getSort();
        if(sort.isSorted()){
            Sort.Order order = sort.iterator().next();
            String properties = order.getProperty();
            boolean isAscending = order.getDirection().isAscending();

            Comparator<Review> comparator = switch(properties){
                case "datePosted" -> Comparator.comparing(Review::getPostedDate);
                case "rating" -> Comparator.comparing(Review::getRating);
                default -> Comparator.comparing(Review::getLastEdited);
            };
            reviews.sort(isAscending ? comparator : comparator.reversed());
        }else {
            reviews.sort(Comparator.comparing(Review::getPostedDate).reversed());
        }

        int start = (int) pageable.getOffset();
        if(start >= reviews.size()){
            return new PageImpl<>(Collections.emptyList(), pageable, reviews.size());
        }

        int end = Math.max(start + pageable.getPageSize(), reviews.size());

        return new PageImpl<>(reviews.subList(start, end), pageable, reviews.size());
    }

    @Override
    public Optional<Review> getReview(String hotelId, String reviewId) {
        Hotel hotel = getHotel(hotelId);
        return hotel.getReviews().stream().filter(
                review -> review.getId().equals(reviewId)
        ).findFirst();
    }

    @Override
    public Review updateReview(AppUser user, String hotelId, String reviewId, ReviewCreateUpdateRequest request) {
        Hotel hotel = getHotel(hotelId);
        Review getReviewFromHotel = getReview(hotelId, reviewId)
                .orElseThrow(()-> new ReviewNotFoundException("Review does not exist"));

        if(!user.getId().equals(getReviewFromHotel.getPostedBy().getId())){
            throw new ReviewNotAllowedException("can not update other user's review");
        }

        getReviewFromHotel.setContent(request.getContent());
        getReviewFromHotel.setRating(request.getRating());
        getReviewFromHotel.setLastEdited(LocalDateTime.now());

        getReviewFromHotel.setImages(request.getImages().stream().map(
                image -> Image.builder()
                        .url(image)
                        .uploadTime(LocalDateTime.now())
                        .build()).toList());

        List<Review> remainingReviews = hotel.getReviews().stream().filter(
                review -> !review.getId().equals(reviewId)
        ).collect(Collectors.toList());
        remainingReviews.add(getReviewFromHotel);
        hotel.setReviews(remainingReviews);
        updateAverageRating(hotel);

        hotelRepository.save(hotel);

        return getReviewFromHotel;
    }

    private void updateAverageRating(Hotel hotel){
        List<Review> reviews = hotel.getReviews();
        if(reviews.isEmpty()){
            hotel.setAverageRatings(0.0f);
        }else{
            double averageRatings = reviews.stream().mapToDouble(Review::getRating)
                    .average().orElse(0.0);
            hotel.setAverageRatings((float) averageRatings);
        }

    }

    private Hotel getHotel(String hotelId) {
        return hotelRepository.findById(hotelId).orElseThrow(
                ()-> new HotelNotFoundException("hotel not present with given id "+ hotelId)
        );
    }
}

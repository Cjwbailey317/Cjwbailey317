package com.techelevator.dao;

import com.techelevator.model.Review;
import java.util.List;

public interface ReviewDao {

    Review addReview(Review review);

    Review updateReview(Review review);

    boolean deleteReview(int reviewId);

    Review getReviewById(int reviewId);

    List<Review> getReviewsByCoffeeShopId(int coffeeShopId);
}

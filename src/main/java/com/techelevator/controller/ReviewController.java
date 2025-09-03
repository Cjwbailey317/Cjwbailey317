package com.techelevator.controller;

import com.techelevator.dao.ReviewDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Review;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/coffee-shops")
public class ReviewController {

    private final ReviewDao reviewDao;
    private final UserDao userDao;

    @Autowired
    public ReviewController(ReviewDao reviewDao, UserDao userDao) {
        this.reviewDao = reviewDao;
        this.userDao = userDao;
    }

    @RequestMapping(path = "/{coffeeShopId}/reviews", method = RequestMethod.GET)
    public List<Review> getReviewsByCoffeeShop(@PathVariable int coffeeShopId) {
        return reviewDao.getReviewsByCoffeeShopId(coffeeShopId);
    }

    @RequestMapping(path = "/reviews", method = RequestMethod.POST)
    public Review addReview(@RequestBody Review review, Principal principal) {
        String username = principal.getName();

        User user = userDao.getUserByUsername(username);

        if (user != null) {
            int userId = user.getId();
            review.setUserId(userId);

            return reviewDao.addReview(review);
        } else {
            throw new RuntimeException("User not found with username: " + username);
        }
    }

    @RequestMapping(path = "/reviews/{reviewId}", method = RequestMethod.PUT)
    public Review updateReview(@PathVariable int reviewId, @RequestBody Review review) {
        review.setId(reviewId);
        return reviewDao.updateReview(review);
    }


    @RequestMapping(path = "/reviews/{reviewId}", method = RequestMethod.DELETE)
    public void deleteReview(@PathVariable int reviewId) {
        reviewDao.deleteReview(reviewId);
    }
}
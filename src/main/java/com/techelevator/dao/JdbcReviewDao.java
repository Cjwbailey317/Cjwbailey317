package com.techelevator.dao;


import com.techelevator.model.Review;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcReviewDao implements ReviewDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcReviewDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Review addReview(Review review) {
        String sql = "INSERT INTO reviews (coffee_shop_id, user_id, comment, rating) " +
                "VALUES (?, ?, ?, ?) RETURNING id";

        Integer reviewId = jdbcTemplate.queryForObject(sql, Integer.class,
                review.getCoffeeShopId(), review.getUserId(), review.getComment(), review.getRating());

        review.setId(reviewId);
        return review;
    }

    @Override
    public Review updateReview(Review review) {
        String sql = "UPDATE reviews SET comment = ?, rating = ? WHERE id = ? AND user_id = ? RETURNING *";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, review.getComment(), review.getRating(),
                review.getId(), review.getUserId());
        if (result.next()) {
            return mapRowToReview(result);
        }
        return null;
    }

    @Override
    public boolean deleteReview(int reviewId) {
        String sql = "DELETE FROM reviews WHERE id = ? RETURNING id";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, reviewId);
        return result.next();
    }

    @Override
    public Review getReviewById(int reviewId) {
        String sql = "SELECT * FROM reviews WHERE id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, reviewId);
        if (result.next()) {
            return mapRowToReview(result);
        }
        return null;
    }

    @Override
    public List<Review> getReviewsByCoffeeShopId(int coffeeShopId) {
        String sql = "SELECT * FROM reviews WHERE coffee_shop_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, coffeeShopId);
        List<Review> reviews = new ArrayList<>();
        while (results.next()) {
            reviews.add(mapRowToReview(results));
        }
        return reviews;
    }

    private Review mapRowToReview(SqlRowSet rs) {
        Review review = new Review();
        review.setId(rs.getInt("id"));
        review.setUserId(rs.getInt("user_id"));
        review.setCoffeeShopId(rs.getInt("coffee_shop_id"));
        review.setComment(rs.getString("comment"));
        review.setRating(rs.getInt("rating"));
        return review;
    }
}
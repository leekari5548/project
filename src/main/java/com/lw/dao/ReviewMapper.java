package com.lw.dao;

import com.lw.entity.Review;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {

    int insertReview(Review review);

    List<Review> getReviewByMessageId(String messageId);

    int deleteReview(String id);

    List<Review> getReviewByUsername(String username, String toUsername);

    int deleteReviewByMessageId(String messageId);
}

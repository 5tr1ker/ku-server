package com.team.saver.market.review.repository;

import com.team.saver.market.review.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long>, CustomReviewImageRepository {

}

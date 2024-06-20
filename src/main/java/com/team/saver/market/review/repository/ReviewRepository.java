package com.team.saver.market.review.repository;

import com.team.saver.market.order.entity.Order;
import com.team.saver.market.review.entity.Review;
import com.team.saver.market.store.entity.Market;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> , CustomReviewRepository{

    Optional<Review> findByOrderAndMarket(Order order, Market market);

}

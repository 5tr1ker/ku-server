package com.team.saver.attraction.promotion.repository;

import com.team.saver.attraction.promotion.entity.Promotion;
import com.team.saver.attraction.promotion.entity.PromotionLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Long>, CustomPromotionRepository {

    long deleteByIdAndLocation(long promotionId, PromotionLocation promotionLocation);
}

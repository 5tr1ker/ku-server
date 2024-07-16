package com.team.saver.promotion.repository;

import com.team.saver.promotion.entity.Promotion;
import com.team.saver.promotion.entity.PromotionLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Long>, CustomPromotionRepository {

    long deleteByPromotionIdAndPromotionLocation(long promotionId, PromotionLocation promotionLocation);
}

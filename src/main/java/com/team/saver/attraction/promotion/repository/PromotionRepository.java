package com.team.saver.attraction.promotion.repository;

import com.team.saver.attraction.promotion.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Long>, CustomPromotionRepository {

}

package com.team.saver.attraction.promotion.repository;

import com.team.saver.attraction.promotion.dto.PromotionResponse;
import com.team.saver.attraction.promotion.entity.PromotionLocation;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomPromotionRepository {

    List<PromotionResponse> findByRecommend(Pageable pageable, PromotionLocation location);

}

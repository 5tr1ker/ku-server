package com.team.saver.promotion.repository;

import com.team.saver.promotion.entity.PromotionTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PromotionTagRepository extends JpaRepository<PromotionTag, Long> {

    Optional<PromotionTag> findByTagContent(String tagContent);

}

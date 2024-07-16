package com.team.saver.promotion.service;

import com.team.saver.promotion.dto.PromotionResponse;
import com.team.saver.promotion.dto.PromotionCreateRequest;
import com.team.saver.promotion.entity.Promotion;
import com.team.saver.promotion.entity.PromotionLocation;
import com.team.saver.promotion.entity.PromotionTag;
import com.team.saver.promotion.entity.PromotionTagRelationShip;
import com.team.saver.promotion.repository.PromotionRepository;
import com.team.saver.promotion.repository.PromotionTagRepository;
import com.team.saver.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final PromotionRepository promotionRepository;
    private final PromotionTagRepository promotionTagRepository;
    private final S3Service s3Service;

    @Transactional
    public void addPromotion(PromotionCreateRequest request, PromotionLocation location, MultipartFile imageFile) {
        Promotion promotion = Promotion.createEntity(request, location);
        String imageUrl = s3Service.uploadImage(imageFile);

        promotion.setImageUrl(imageUrl);

        for(String tagContent : request.getTags()) {
            PromotionTag promotionTag = findOrCreateTag(tagContent);

            promotion.addTag(PromotionTagRelationShip.createEntity(promotion, promotionTag));
        }

        promotionRepository.save(promotion);
    }

    @Transactional
    protected PromotionTag findOrCreateTag(String tagContent) {
        return promotionTagRepository.findByTagContent(tagContent)
                .orElseGet(() -> promotionTagRepository.save(PromotionTag.createEntity(tagContent)));
    }

    @Transactional
    public void deleteByIdAndLocation(long promotionId, PromotionLocation location) {
        promotionRepository.deleteByPromotionIdAndPromotionLocation(promotionId, location);
    }

    public List<PromotionResponse> getPromotion(Pageable pageable, PromotionLocation location) {
        return promotionRepository.findByRecommend(pageable, location);
    }

}

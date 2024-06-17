package com.team.saver.attraction.service;

import com.team.saver.attraction.dto.AttractionResponse;
import com.team.saver.attraction.dto.NewAttractionRequest;
import com.team.saver.attraction.entity.Attraction;
import com.team.saver.attraction.entity.AttractionTag;
import com.team.saver.attraction.entity.AttractionTagRelationShip;
import com.team.saver.attraction.repository.AttractionRepository;
import com.team.saver.attraction.repository.AttractionTagRepository;
import com.team.saver.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttractionService {

    private final AttractionRepository attractionRepository;
    private final AttractionTagRepository attractionTagRepository;
    private final S3Service s3Service;

    @Transactional
    public void addAttraction(NewAttractionRequest request, MultipartFile imageFile) {
        Attraction attraction = Attraction.createEntity(request);
        String imageUrl = s3Service.uploadImage(imageFile);

        attraction.setImageUrl(imageUrl);

        for(String tagContent : request.getTags()) {
            AttractionTag attractionTag = findOrCreateTag(tagContent);

            attraction.addTag(AttractionTagRelationShip.createEntity(attraction, attractionTag));
        }

        attractionRepository.save(attraction);
    }

    @Transactional
    protected AttractionTag findOrCreateTag(String tagContent) {
        return attractionTagRepository.findByTagContent(tagContent)
                .orElseGet(() -> attractionTagRepository.save(AttractionTag.createEntity(tagContent)));
    }

    @Transactional
    public void deleteAttraction(long attractionId) {
        attractionRepository.deleteById(attractionId);
    }

    public List<AttractionResponse> getAttraction(Pageable pageable) {
        return attractionRepository.findByRecommend(pageable);
    }

}

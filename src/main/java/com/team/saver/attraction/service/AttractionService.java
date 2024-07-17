package com.team.saver.attraction.service;

import com.team.saver.attraction.dto.AttractionCreateRequest;
import com.team.saver.attraction.dto.AttractionResponse;
import com.team.saver.attraction.entity.Attraction;
import com.team.saver.attraction.repository.AttractionRepository;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.common.util.DistanceCalculator;
import com.team.saver.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.team.saver.common.dto.ErrorMessage.NOT_FOUND_ATTRACTION;

@Service
@RequiredArgsConstructor
public class AttractionService {

    private final AttractionRepository attractionRepository;
    private final S3Service s3Service;

    @Transactional
    public void addAttraction(AttractionCreateRequest request, MultipartFile multipartFile) {
        Attraction attraction = Attraction.createEntity(request);

        String imageUrl = s3Service.uploadImage(multipartFile);
        attraction.setImageUrl(imageUrl);

        attractionRepository.save(attraction);
    }

    public List<AttractionResponse> getAttraction(Pageable pageable, double locationX, double locationY) {
        List<AttractionResponse> result = attractionRepository.getAttraction(pageable);

        return DistanceCalculator.calculateAttractionDistance(result, locationX, locationY);
    }

    public List<AttractionResponse> searchAttraction(Pageable pageable, String keyWord, double locationX, double locationY) {
        List<AttractionResponse> result =  attractionRepository.searchAttraction(pageable, keyWord);

        return DistanceCalculator.calculateAttractionDistance(result, locationX, locationY);
    }

    @Transactional
    public void deleteAttraction(long attractionId) {
        long result = attractionRepository.deleteByAttractionId(attractionId);

        if(result == 0) {
            throw new CustomRuntimeException(NOT_FOUND_ATTRACTION);
        }
    }



}

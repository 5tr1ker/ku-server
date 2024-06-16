package com.team.saver.attraction.service;

import com.team.saver.attraction.dto.NewAttractionRequest;
import com.team.saver.attraction.repository.AttractionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AttractionService {

    private final AttractionRepository attractionRepository;

    public void addAttraction(NewAttractionRequest request, MultipartFile file) {
        
    }

    public void deleteAttraction(long attractionId) {
        attractionRepository.deleteById(attractionId);
    }

    public void getAttraction() {

    }

}

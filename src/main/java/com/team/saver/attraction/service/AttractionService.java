package com.team.saver.attraction.service;

import com.team.saver.attraction.repository.AttractionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttractionService {

    private final AttractionRepository attractionRepository;

}

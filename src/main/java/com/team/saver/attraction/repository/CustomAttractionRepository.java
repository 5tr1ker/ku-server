package com.team.saver.attraction.repository;

import com.team.saver.attraction.dto.AttractionResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomAttractionRepository {

    List<AttractionResponse> findByRecommend(Pageable pageable);

}

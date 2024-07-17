package com.team.saver.attraction.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.team.saver.attraction.dto.AttractionResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomAttractionRepository {

    List<AttractionResponse> getAttraction(Pageable pageable, OrderSpecifier orderSpecifier);

    List<AttractionResponse> searchAttraction(Pageable pageable, OrderSpecifier orderSpecifier, String keyWord);

}

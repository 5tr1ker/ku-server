package com.team.saver.attraction.dto;

import com.querydsl.core.types.OrderSpecifier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.team.saver.attraction.entity.QAttraction.attraction;

@Getter
@RequiredArgsConstructor
public enum SortType {

    RECENTLY_UPLOAD("recentlyUpload" , attraction.attractionId.desc());

    private final String type;

    private final OrderSpecifier orderSpecifier;

}

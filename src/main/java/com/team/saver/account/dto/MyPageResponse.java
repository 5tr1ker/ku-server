package com.team.saver.account.dto;

import com.team.saver.account.entity.School;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MyPageResponse {

    private long accountId;

    private String email;

    private String imageUrl;

    private School school;

    private int level;

    private int totalGainPoint;

    private int useAblePoint;

    private long favoriteCount;

    private long couponCount;

}

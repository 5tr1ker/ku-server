package com.team.saver.account.repository;

import com.team.saver.account.dto.MyPageResponse;

import java.util.Optional;

public interface CustomAccountRepository {

    Optional<MyPageResponse> getMyPageInfo(String email, long userExp, long userLevel);

}

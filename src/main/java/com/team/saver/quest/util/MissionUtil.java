package com.team.saver.quest.util;

import com.team.saver.account.repository.AccountRepository;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.market.coupon.repository.CouponRepository;
import com.team.saver.market.review.repository.ReviewRepository;
import com.team.saver.quest.dto.MissionLevelResponse;
import com.team.saver.quest.dto.MissionResponse;
import com.team.saver.quest.entity.Mission;
import com.team.saver.quest.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.team.saver.common.dto.ErrorMessage.NOT_FOUND_USER;

@Component
@RequiredArgsConstructor
public class MissionUtil {

    private final ReviewRepository reviewRepository;
    private final CouponRepository couponRepository;
    private final AccountRepository accountRepository;
    private final MissionRepository missionRepository;

    @Value("${exp.init}")
    private long initExp;

    @Value("${exp.increase}")
    private long increaseExp;

    public MissionResponse createMissionResponse(Mission mission, CurrentUser currentUser) {
        if (mission.getMissionType() == MissionType.ATTENDANCE) {
            return getNumberOfAttendance(mission, currentUser);
        }

        if (mission.getMissionType() == MissionType.USE_DISCOUNT_COUPON) {
            return getUsedDiscountCoupon(mission, currentUser);
        }

        if (mission.getMissionType() == MissionType.WRITE_REVIEW) {
            return getWriteReview(mission, currentUser);
        }

        return null;
    }

    private MissionResponse getUsedDiscountCoupon(Mission mission, CurrentUser currentUser) {
        long couponCount = couponRepository.countUsedCouponByEmail(currentUser.getEmail());

        return MissionResponse.createEntity(mission, couponCount);
    }

    private MissionResponse getWriteReview(Mission mission, CurrentUser currentUser) {
        long reviewCount = reviewRepository.countReviewByEmail(currentUser.getEmail());

        return MissionResponse.createEntity(mission, reviewCount);
    }

    private MissionResponse getNumberOfAttendance(Mission mission, CurrentUser currentUser) {
        long numberOfAttendance = accountRepository.findByEmail(currentUser.getEmail())
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_USER))
                .getLoginCount();

        return MissionResponse.createEntity(mission, numberOfAttendance);
    }

    public MissionLevelResponse getMissionLevelByEmail(CurrentUser currentUser) {
        List<Mission> missionList = missionRepository.findAll();
        long totalExp = 0;

        for(Mission mission : missionList) {
            MissionResponse missionResponse = createMissionResponse(mission, currentUser);

            totalExp += calculateTotalExp(missionResponse.getInitExp(), missionResponse.getIncreaseExp(), missionResponse.getLevel() - 1);
        }

        return MissionLevelResponse.createEntity(initExp, increaseExp, totalExp);
    }

    private long calculateTotalExp(long initExp, long increaseExp, long level) {
        return level * initExp + increaseExp * (level - 1) * level / 2;
    }

}

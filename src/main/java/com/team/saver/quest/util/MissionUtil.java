package com.team.saver.quest.util;

import com.team.saver.account.repository.AccountRepository;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.ErrorMessage;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.market.coupon.repository.CouponRepository;
import com.team.saver.market.review.repository.ReviewRepository;
import com.team.saver.quest.dto.MissionResponse;
import com.team.saver.quest.entity.Mission;
import com.team.saver.quest.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.team.saver.common.dto.ErrorMessage.NOT_FOUND_USER;

@Component
@RequiredArgsConstructor
public class MissionUtil {

    private final ReviewRepository reviewRepository;
    private final CouponRepository couponRepository;
    private final AccountRepository accountRepository;

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

}

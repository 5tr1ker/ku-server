package com.team.saver.quest.util;

import com.team.saver.quest.entity.Mission;
import com.team.saver.quest.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MissionData implements CommandLineRunner {

    private final MissionRepository missionRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        missionRepository.deleteAll();

        Mission usedDiscountCoupon = Mission.builder()
                .initExp(20)
                .increaseWeight(15)
                .initWeight(5)
                .increaseWeight(5)
                .build();
        missionRepository.save(usedDiscountCoupon);

        Mission numberOfAttendance = Mission.builder()
                .initExp(15)
                .increaseWeight(10)
                .initWeight(7)
                .increaseWeight(7)
                .build();
        missionRepository.save(numberOfAttendance);

        Mission writeReview = Mission.builder()
                .initExp(10)
                .increaseExp(10)
                .initWeight(5)
                .increaseWeight(5)
                .build();
        missionRepository.save(writeReview);
    }
}

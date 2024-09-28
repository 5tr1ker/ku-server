package com.team.saver.market.store.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulerRunner {

    private final BestMenuScheduler bestMenuScheduler;

    @PostConstruct
    public void init() {
        bestMenuScheduler.updateBestMenu();
    }

}

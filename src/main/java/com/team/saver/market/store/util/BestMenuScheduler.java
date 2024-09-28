package com.team.saver.market.store.util;

import com.team.saver.market.store.entity.Menu;
import com.team.saver.market.store.repository.MarketRepository;
import com.team.saver.market.store.repository.MenuRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BestMenuScheduler {

    private final MarketRepository marketRepository;
    private final MenuRepository menuRepository;

    @Value("${best.menu-count}")
    private long bestMenuCount;

    @Scheduled(cron = "0 0 0 ? * MON")
    @Transactional
    public void updateBestMenu() {
        long marketNumber = marketRepository.findMarketCount();

        menuRepository.resetIsBestMenu();
        for(int i = 1; i <= 1; i++) {
            List<Long> menuList = menuRepository.findIdByMarketIdAndOrderByManyOrderCount(i, bestMenuCount);

            menuRepository.setIsBestMenuByMenuId(menuList);
        }
    }
}

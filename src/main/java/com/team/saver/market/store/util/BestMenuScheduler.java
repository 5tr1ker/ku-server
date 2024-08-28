package com.team.saver.market.store.util;

import com.team.saver.market.store.entity.Menu;
import com.team.saver.market.store.repository.MarketRepository;
import com.team.saver.market.store.repository.MenuRepository;
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

    @Scheduled(cron = "0 0 0 1/1 * ?")
    @Transactional
    public void updateBestMenu() {
        long marketNumber = marketRepository.findMarketCount();

        for(int i = 1; i <= marketNumber; i++) {
            menuRepository.resetMenuOrderCountByMarketId(i);
            menuRepository.resetBestMenuByMarketId(i);

            List<Menu> menuList = marketRepository.findManyMenuOrderCountByMarketId(i, bestMenuCount);
            int j = 0;
            for(Menu menu : menuList) {
                if(j <= bestMenuCount) {
                    break;
                }
                menu.isBestMenu();

                j += 1;
            }
        }
    }

}

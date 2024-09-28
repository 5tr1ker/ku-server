package com.team.saver.market.store.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.market.store.entity.Menu;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.team.saver.market.store.entity.QMarket.market;
import static com.team.saver.market.store.entity.QMenu.menu;
import static com.team.saver.market.store.entity.QMenuContainer.menuContainer;

@RequiredArgsConstructor
public class MenuRepositoryImpl implements CustomMenuRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Menu> findManyMenuOrderCountByMarketId(long marketId, long size) {
        return jpaQueryFactory.select(menu)
                .from(market)
                .innerJoin(market.menuContainers, menuContainer)
                .innerJoin(menuContainer.menus, menu)
                .where(market.marketId.eq(marketId))
                .orderBy(menu.orderCount.desc())
                .limit(size)
                .fetch();
    }

    @Override
    public long resetIsBestMenu() {
        return jpaQueryFactory.update(menu).set(menu.isBestMenu, false)
                .execute();
    }


    @Override
    public long setIsBestMenuByMenu(List<Menu> menuList) {
        return jpaQueryFactory.update(menu)
                .set(menu.isBestMenu, true)
                .where(menu.in(menuList))
                .execute();
    }

}

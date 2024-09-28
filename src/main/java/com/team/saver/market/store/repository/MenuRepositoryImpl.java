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
    public long resetIsBestMenuByMarketId(long marketId) {
        List<Menu> result = jpaQueryFactory.select(menu)
                .from(menuContainer)
                .innerJoin(menuContainer.market, market).on(market.marketId.eq(marketId))
                .innerJoin(menuContainer.menus, menu)
                .fetch();

        return jpaQueryFactory.update(menu).set(menu.isBestMenu, false)
                .where(menu.in(result))
                .execute();
    }

    @Override
    public List<Long> findIdByMarketIdAndOrderByManyOrderCount(long marketId, long size) {
        return jpaQueryFactory.select(menu.menuId)
                .from(market)
                .innerJoin(market.menuContainers, menuContainer)
                .innerJoin(menuContainer.menus, menu)
                .where(market.marketId.eq(marketId))
                .orderBy(menu.orderCount.desc())
                .limit(size)
                .fetch();
    }

    @Override
    public long setIsBestMenuByMenuId(List<Long> menuId) {
        return jpaQueryFactory.update(menu)
                .set(menu.isBestMenu, true)
                .where(menu.menuId.in(menuId))
                .execute();
    }

    @Override
    public long resetIsBestMenu() {
        return jpaQueryFactory.update(menu).set(menu.isBestMenu, false)
                .execute();
    }
}

package com.team.saver.market.store.repository;

import com.team.saver.market.store.entity.Menu;

import java.util.List;

public interface CustomMenuRepository {

    List<Menu> findManyMenuOrderCountByMarketId(long marketId, long size);

    long setIsBestMenuByMenu(List<Menu> menuList);

    long resetIsBestMenu();
}

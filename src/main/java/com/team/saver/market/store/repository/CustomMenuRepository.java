package com.team.saver.market.store.repository;

import com.team.saver.market.store.entity.Menu;

import java.util.List;

public interface CustomMenuRepository {

    List<Long> findManyMenuOrderCountByMarketId(long marketId, long size);

    long setIsBestMenuByMenu(List<Long> menuList);

    long resetIsBestMenu();
}

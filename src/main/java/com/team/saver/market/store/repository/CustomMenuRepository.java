package com.team.saver.market.store.repository;

import com.team.saver.market.store.entity.Menu;

import java.util.List;

public interface CustomMenuRepository {

    long resetIsBestMenuByMarketId(long marketId);

    List<Long> findIdByMarketIdAndOrderByManyOrderCount(long marketId, long size);

    long setIsBestMenuByMenuId(List<Long> menuId);

    long resetIsBestMenu();
}

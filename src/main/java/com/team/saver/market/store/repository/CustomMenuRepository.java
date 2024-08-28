package com.team.saver.market.store.repository;

public interface CustomMenuRepository {

    long resetMenuOrderCountByMarketId(long marketId);

    long resetBestMenuByMarketId(long marketId);

}

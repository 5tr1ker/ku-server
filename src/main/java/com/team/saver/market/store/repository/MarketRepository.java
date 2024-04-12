package com.team.saver.market.store.repository;

import com.team.saver.market.store.entity.MainCategory;
import com.team.saver.market.store.entity.Market;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarketRepository extends JpaRepository<Market, Long> , CustomMarketRepository {

}

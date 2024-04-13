package com.team.saver.market.store.repository;

import com.team.saver.market.store.entity.Market;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketRepository extends JpaRepository<Market, Long> , CustomMarketRepository {

}

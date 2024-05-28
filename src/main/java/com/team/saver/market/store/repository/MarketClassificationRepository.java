package com.team.saver.market.store.repository;

import com.team.saver.market.store.entity.Classification;
import com.team.saver.market.store.entity.Market;
import com.team.saver.market.store.entity.MarketClassification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarketClassificationRepository extends JpaRepository<MarketClassification, Long> {
    Optional<MarketClassification> findByMarketAndClassification(Market market, Classification classification);

}

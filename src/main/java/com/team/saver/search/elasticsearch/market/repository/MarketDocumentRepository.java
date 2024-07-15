package com.team.saver.search.elasticsearch.market.repository;

import com.team.saver.search.elasticsearch.market.document.MarketDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MarketDocumentRepository extends ElasticsearchRepository<MarketDocument, Long> {

    Page<MarketDocument> findByMarketNameContainingOrMarketDescriptionContaining(String marketName, String marketDescription, Pageable pageable);

}

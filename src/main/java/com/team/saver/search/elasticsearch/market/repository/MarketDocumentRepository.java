package com.team.saver.search.elasticsearch.market.repository;

import com.team.saver.search.elasticsearch.market.document.MarketDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface MarketDocumentRepository extends ElasticsearchRepository<MarketDocument, Long> {

    List<MarketDocument> findByMarketName(String marketName);

}

package com.team.saver.search.elasticsearch.market.repository;

import com.team.saver.search.elasticsearch.market.document.MarketDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface MarketDocumentRepository extends ElasticsearchRepository<MarketDocument, Long> {

    Page<MarketDocument> findByMarketName(String marketName, Pageable pageable);

}

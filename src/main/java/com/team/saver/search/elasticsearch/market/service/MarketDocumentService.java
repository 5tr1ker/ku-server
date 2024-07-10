package com.team.saver.search.elasticsearch.market.service;

import com.team.saver.search.elasticsearch.market.document.MarketDocument;
import com.team.saver.search.elasticsearch.market.repository.MarketDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketDocumentService {

    private final MarketDocumentRepository marketDocumentRepository;

    public List<MarketDocument> findByMarketName(String title) {
        return marketDocumentRepository.findByMarketName(title);
    }

}

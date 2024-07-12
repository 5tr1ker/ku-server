package com.team.saver.search.elasticsearch.market.controller;

import com.team.saver.search.elasticsearch.market.document.MarketDocument;
import com.team.saver.search.elasticsearch.market.service.MarketDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MarketDocumentController {

    private final MarketDocumentService marketDocumentService;

    @GetMapping("/v1/markets/search")
    public ResponseEntity findByMarketName(@RequestParam String marketName, Pageable pageable) {
        List<MarketDocument> result = marketDocumentService.findByMarketName(marketName, pageable);

        return ResponseEntity.ok(result);
    }

}

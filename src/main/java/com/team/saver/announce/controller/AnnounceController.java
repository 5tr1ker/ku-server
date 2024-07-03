package com.team.saver.announce.controller;

import com.team.saver.announce.dto.AnnounceCreateRequest;
import com.team.saver.announce.dto.AnnounceResponse;
import com.team.saver.announce.dto.AnnounceUpdateRequest;
import com.team.saver.announce.service.AnnounceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AnnounceController {

    private final AnnounceService announceService;

    @GetMapping("/v1/announces")
    @Operation(summary = "모든 공지사항 데이터 가져오기")
    public ResponseEntity findAllAnnounce(Pageable pageable) {
        List<AnnounceResponse> result = announceService.findAllAnnounce(pageable);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/announces/{announceId}")
    @Operation(summary = "특정 공지사항 데이터 가져오기")
    public ResponseEntity findAnnounceDetail(@PathVariable long announceId) {
        AnnounceResponse result = announceService.findAnnounceDetail(announceId);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/v1/announces")
    @Operation(summary = "공지사항 데이터 등록하기")
    public ResponseEntity addAnnounce(@RequestBody AnnounceCreateRequest request) {
        announceService.addAnnounce(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/v1/announces/{announceId}")
    @Operation(summary = "공지사항 데이터 수정하기")
    public ResponseEntity updateAnnounce(@RequestBody AnnounceUpdateRequest request,
                                         @PathVariable long announceId) {
        announceService.updateAnnounce(request, announceId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/v1/announces/{announceId}")
    @Operation(summary = "공지사항 데이터 삭제하기")
    public ResponseEntity deleteAnnounce(@PathVariable long announceId) {
        announceService.deleteAnnounce(announceId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

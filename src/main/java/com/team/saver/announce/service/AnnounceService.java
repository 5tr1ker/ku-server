package com.team.saver.announce.service;

import com.team.saver.announce.dto.AnnounceCreateRequest;
import com.team.saver.announce.dto.AnnounceResponse;
import com.team.saver.announce.dto.AnnounceUpdateRequest;
import com.team.saver.announce.entity.Announce;
import com.team.saver.announce.repository.AnnounceRepository;
import com.team.saver.common.exception.CustomRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.team.saver.common.dto.ErrorMessage.NOT_FOUND_ANNOUNCE;

@Service
@RequiredArgsConstructor
public class AnnounceService {

    private final AnnounceRepository announceRepository;

    @Transactional
    public void addAnnounce(AnnounceCreateRequest request) {
        Announce announce = Announce.createEntity(request);

        announceRepository.save(announce);
    }

    @Transactional
    public void updateAnnounce(AnnounceUpdateRequest request, long announceId) {
        Announce announce = announceRepository.findById(announceId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_ANNOUNCE));

        announce.update(request);
    }

    @Transactional
    public void deleteAnnounce(long announceId) {
        Announce announce = announceRepository.findById(announceId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_ANNOUNCE));

        announce.delete();
    }

    public List<AnnounceResponse> findAllAnnounce(Pageable pageable) {
        return announceRepository.findAllAnnounce(pageable);
    }

    public AnnounceResponse findAnnounceDetail(long announceId) {
        return announceRepository.findAnnounceDetail(announceId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_ANNOUNCE));
    }

}

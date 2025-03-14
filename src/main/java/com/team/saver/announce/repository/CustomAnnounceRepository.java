package com.team.saver.announce.repository;

import com.team.saver.announce.dto.AnnounceDetailResponse;
import com.team.saver.announce.dto.AnnounceResponse;
import com.team.saver.announce.entity.Announce;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomAnnounceRepository {

    List<AnnounceResponse> findAllAnnounce(Pageable pageable, boolean isImportant);

    Optional<AnnounceDetailResponse> findAnnounceDetail(long announceId);

    Optional<Announce> findById(long announceId);
}

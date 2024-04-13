package com.team.saver.partner.request.repository;

import com.team.saver.partner.request.entity.PartnerRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerRequestRepository extends JpaRepository<PartnerRequest, Long>, CustomPartnerRequestRepository {
}

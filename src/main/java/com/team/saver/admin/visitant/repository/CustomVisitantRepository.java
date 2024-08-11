package com.team.saver.admin.visitant.repository;

import com.team.saver.admin.visitant.dto.VisitantResponse;

import java.util.List;

public interface CustomVisitantRepository {

    List<VisitantResponse> findVisitantCountByVisitDate();

}

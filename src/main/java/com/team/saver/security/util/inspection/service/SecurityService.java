package com.team.saver.security.util.inspection.service;

import com.team.saver.common.dto.ResponseCode;
import com.team.saver.common.dto.ResponseMessage;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.security.util.inspection.dto.InspectionTimeRequest;
import com.team.saver.security.util.inspection.dto.InspectionTimeResponse;
import com.team.saver.security.util.inspection.entity.InspectionTime;
import com.team.saver.security.util.inspection.repository.InspectionTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.team.saver.common.dto.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final InspectionTimeRepository inspectionTimeRepository;

    public ResponseMessage isInspectionTime() {
        LocalDateTime now = LocalDateTime.now();

        InspectionTime inspectionTime = inspectionTimeRepository.findByInspectionStartLessThanEqualAndInspectionEndGreaterThanEqual(now, now).orElse(null);

        if (inspectionTime != null) {
            return ResponseMessage.of(ResponseCode.REQUEST_FAIL
                    , new InspectionTimeResponse(inspectionTime.getInspectionStart().toLocalTime()
                            , inspectionTime.getInspectionEnd().toLocalTime()));
        }

        return null;
    }

    public void setInspectionTime(InspectionTimeRequest request) {
        LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), request.getStart());
        LocalDateTime endTime = LocalDateTime.of(LocalDate.now(), request.getEnd());
        isValidInspectionTime(startTime, endTime);

        InspectionTime inspectionTime = InspectionTime.createEntity(startTime, endTime);

        inspectionTimeRepository.save(inspectionTime);
    }

    private void isValidInspectionTime(LocalDateTime startTime, LocalDateTime endTime) {
        if(startTime.compareTo(endTime) >= 0) {
            throw new CustomRuntimeException(NOT_VALID_INSPECTION_TIME);
        }

        if(inspectionTimeRepository.findByInspectionStartLessThanEqualAndInspectionEndGreaterThanEqual(startTime, startTime).isPresent()) {
            throw new CustomRuntimeException(EXIST_INSPECTION_TIME);
        }
    }

    @Transactional
    public void deleteInspection() {
        LocalDateTime now = LocalDateTime.now();

        InspectionTime inspectionTime = inspectionTimeRepository.findByInspectionStartLessThanEqualAndInspectionEndGreaterThanEqual(now, now)
                .orElseThrow(() -> new CustomRuntimeException(NOT_INSPECTION_TIME));

        inspectionTimeRepository.delete(inspectionTime);
    }

    @Transactional
    public void updateInspection(InspectionTimeRequest request) {
        LocalDateTime now = LocalDateTime.now();

        InspectionTime inspectionTime = inspectionTimeRepository.findByInspectionStartLessThanEqualAndInspectionEndGreaterThanEqual(now, now)
                .orElseThrow(() -> new CustomRuntimeException(NOT_INSPECTION_TIME));

        inspectionTime.updateInspectionTime(request);
    }
}

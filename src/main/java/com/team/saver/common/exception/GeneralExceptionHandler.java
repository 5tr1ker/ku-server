package com.team.saver.common.exception;

import com.team.saver.common.dto.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.team.saver.common.dto.ResponseCode.REQUEST_FAIL;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity handleExceptionHandler(CustomRuntimeException e) {
        ResponseMessage message = ResponseMessage.of(REQUEST_FAIL, e.getErrorMessage().getErrorCode(), e.getMessage(), e.getData());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

}

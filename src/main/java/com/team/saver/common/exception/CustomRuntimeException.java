package com.team.saver.common.exception;

import com.team.saver.common.dto.ErrorMessage;

public class CustomRuntimeException extends RuntimeException {

    public CustomRuntimeException(ErrorMessage e) {
        super(e.getMessage());
    }

    public CustomRuntimeException(ErrorMessage e, String data) {
        super(String.format(e.getMessage() , data));
    }
}

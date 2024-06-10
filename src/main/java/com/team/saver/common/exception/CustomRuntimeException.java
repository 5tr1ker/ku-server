package com.team.saver.common.exception;

import com.team.saver.common.dto.ErrorMessage;
import lombok.Getter;

@Getter
public class CustomRuntimeException extends RuntimeException {

    private Object data;

    public CustomRuntimeException(ErrorMessage e) {
        super(e.getMessage());
    }

    public CustomRuntimeException(ErrorMessage e, String messageData) {
        super(String.format(e.getMessage() , messageData));
    }

    public CustomRuntimeException(ErrorMessage e, Object data) {
        super(e.getMessage());
        this.data = data;
    }

    public CustomRuntimeException(ErrorMessage e, String messageData, Object data) {
        super(String.format(e.getMessage(), messageData));
        this.data = data;
    }

}

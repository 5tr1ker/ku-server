package com.team.saver.common.exception;

import com.team.saver.common.dto.ErrorMessage;
import lombok.Getter;

@Getter
public class CustomRuntimeException extends RuntimeException {

    private Object data;
    private ErrorMessage errorMessage;

    public CustomRuntimeException(ErrorMessage e) {
        super(e.getMessage());
        this.errorMessage = e;
    }

    public CustomRuntimeException(ErrorMessage e, String messageData) {
        super(String.format(e.getMessage() , messageData));
        this.errorMessage = e;
    }

    public CustomRuntimeException(ErrorMessage e, Object data) {
        super(e.getMessage());
        this.errorMessage = e;
        this.data = data;
    }

    public CustomRuntimeException(ErrorMessage e, String messageData, Object data) {
        super(String.format(e.getMessage(), messageData));
        this.errorMessage = e;
        this.data = data;
    }

}

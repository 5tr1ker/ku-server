package com.team.saver.common.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseMessage {
    private String code;
    private String message;
    private Object data;

    private ResponseMessage(String code , String message) {
        this.code = code;
        this.message = message;
    }

    private ResponseMessage(ResponseCode responseCode , Object data) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.data = data;
    }

    private ResponseMessage(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResponseMessage of(ResponseCode responseCode) {
        return new ResponseMessage(responseCode.getCode() , responseCode.getMessage());
    }

    public static ResponseMessage of(ResponseCode responseCode , String message) {
        return new ResponseMessage(responseCode.getCode() , message);
    }

    public static ResponseMessage of(ResponseCode responseCode, Object data) {
        return new ResponseMessage(responseCode , data);
    }

    public static ResponseMessage of(ResponseCode responseCode, String message, Object data) {
        return new ResponseMessage(responseCode.getCode(), message, data);
    }
}
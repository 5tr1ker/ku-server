package com.team.saver.common.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseMessage {
    private String responseCode;
    private String responseMessage;
    private Integer errorCode;
    private Object data;

    private ResponseMessage(ResponseCode responseCode , Object data) {
        this.responseCode = responseCode.getCode();
        this.responseMessage = responseCode.getMessage();
        this.data = data;
    }

    private ResponseMessage(String responseCode, int errorCode, String responseMessage, Object data) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.errorCode = errorCode;
        this.data = data;
    }

    public static ResponseMessage of(ResponseCode responseCode, Object data) {
        return new ResponseMessage(responseCode , data);
    }

    public static ResponseMessage of(ResponseCode responseCode,int errorCode , String message, Object data) {
        return new ResponseMessage(responseCode.getCode() ,errorCode ,message ,data);
    }

}
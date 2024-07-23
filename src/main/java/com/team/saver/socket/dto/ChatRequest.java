package com.team.saver.socket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatRequest {

    private MessageType messageType;

    private String message;

    private long accountId;

}

package com.team.saver.socket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatRoomResponse {

    private long chatRoomId;

    private long accountId;

    private String email;

    private String profileImage;

}

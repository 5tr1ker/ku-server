package com.team.saver.socket.dto;

import com.team.saver.socket.entity.Chat;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatResponse {

    private long chatId;

    private String message;

    private LocalDateTime sendTime;

    private boolean isAdmin;

    public static ChatResponse createEntity(Chat chat) {
        return ChatResponse.builder()
                .chatId(chat.getChatId())
                .message(chat.getMessage())
                .sendTime(chat.getSendTime())
                .isAdmin(chat.isAdmin())
                .build();
    }

}

package com.team.saver.socket.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.saver.socket.entity.Chat;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ChatResponse {

    private long chatId;

    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
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

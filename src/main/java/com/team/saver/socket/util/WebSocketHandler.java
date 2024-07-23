package com.team.saver.socket.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.socket.dto.ChatRequest;
import com.team.saver.socket.dto.ChatResponse;
import com.team.saver.socket.dto.MessageType;
import com.team.saver.socket.entity.Chat;
import com.team.saver.socket.entity.ChatRoom;
import com.team.saver.socket.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.team.saver.common.dto.ErrorMessage.NOT_FOUND_CHATROOM;

@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private final Map<Long, WebSocketSession> CLIENTS = new HashMap<>();
    private WebSocketSession ADMINS = null;
    private final ObjectMapper objectMapper;
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        CLIENTS.remove(session.getId());
    }

    @Override
    @Transactional
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ChatRequest chatMessage = objectMapper.readValue(message.getPayload(), ChatRequest.class);

        if(chatMessage.getMessageType() == MessageType.ENTER) {
            joinChatBySession(chatMessage.getAccountId(), session);
        }

        if(chatMessage.getMessageType() == MessageType.ENTER_ADMIN) {
            ADMINS = session;
        }

        if(chatMessage.getMessageType() == MessageType.SEND) {
            sendChat(chatMessage, false);
        }

        if(chatMessage.getMessageType() == MessageType.SEND_ADMIN) {
            sendChat(chatMessage, true);
        }

    }

    private void joinChatBySession(long accountId, WebSocketSession session) {
        if(!CLIENTS.containsKey(accountId)) {
            CLIENTS.put(accountId , session);
        }
    }

    private Chat sendChat(ChatRequest chatRequest, boolean isAdmin) throws IOException {
        Chat chat = saveChatData(chatRequest, isAdmin);

        WebSocketSession session = CLIENTS.get(chatRequest.getAccountId());

        sendChatMessage(chat, session);
        sendChatToAdmin(chat);

        return chat;
    }

    private void sendChatToAdmin(Chat chat) throws IOException {
        if(ADMINS != null) {
            sendChatMessage(chat, ADMINS);
        }
    }

    private void sendChatMessage(Chat chat, WebSocketSession session) throws IOException {
        String body = objectMapper.writeValueAsString(ChatResponse.createEntity(chat));
        session.sendMessage(new TextMessage(body));
    }

    private Chat saveChatData(ChatRequest chatRequest, boolean isAdmin) {
        ChatRoom chatRoom = chatRoomRepository.findByAccountId(chatRequest.getAccountId())
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_CHATROOM));

        Chat chat = Chat.createEntity(chatRequest.getMessage(), isAdmin);

        chatRoom.addChat(chat);
        return chat;
    }

}

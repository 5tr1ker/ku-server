package com.team.saver.socket.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.saver.account.entity.Account;
import com.team.saver.account.repository.AccountRepository;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.socket.dto.ChatRequest;
import com.team.saver.socket.dto.ChatResponse;
import com.team.saver.socket.dto.MessageType;
import com.team.saver.socket.entity.Chat;
import com.team.saver.socket.entity.ChatRoom;
import com.team.saver.socket.repository.ChatRepository;
import com.team.saver.socket.repository.ChatRoomRepository;
import com.team.saver.socket.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.team.saver.common.dto.ErrorMessage.NOT_FOUND_CHATROOM;
import static com.team.saver.common.dto.ErrorMessage.NOT_FOUND_USER;

@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private final Map<Long, WebSocketSession> CLIENTS = new HashMap<>();
    private WebSocketSession ADMINS = null;
    private final ObjectMapper objectMapper;
    private final ChatRoomRepository chatRoomRepository;
    private final AccountRepository accountRepository;
    private final ChatService chatService;

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
        addChatRoomByAccountIdIfNotExists(accountId);

        if(!CLIENTS.containsKey(accountId)) {
            CLIENTS.put(accountId , session);
        }
    }

    private ChatRoom addChatRoomByAccountIdIfNotExists(long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_USER));

        return chatRoomRepository.findByAccount(account)
                .orElseGet(() -> chatRoomRepository.save(createChatRoom(account)));
    }

    private ChatRoom createChatRoom(Account account) {
        return ChatRoom.builder()
                .account(account)
                .build();
    }

    public Chat sendChat(ChatRequest chatRequest, boolean isAdmin) throws IOException {
        Chat chat = chatService.saveChatData(chatRequest, isAdmin);

        WebSocketSession session = CLIENTS.get(chatRequest.getAccountId());

        sendChatMessage(chat, session);
        sendChatToAdmin(chat);

        return chat;
    }

    public void sendChatToAdmin(Chat chat) throws IOException {
        if(ADMINS != null) {
            sendChatMessage(chat, ADMINS);
        }
    }

    public void sendChatMessage(Chat chat, WebSocketSession session) throws IOException {
        String body = objectMapper.writeValueAsString(ChatResponse.createEntity(chat));
        session.sendMessage(new TextMessage(body));
    }

}

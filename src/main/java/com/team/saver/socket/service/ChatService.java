package com.team.saver.socket.service;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.ErrorMessage;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.socket.dto.ChatRequest;
import com.team.saver.socket.dto.ChatResponse;
import com.team.saver.socket.dto.ChatRoomResponse;
import com.team.saver.socket.entity.Chat;
import com.team.saver.socket.entity.ChatRoom;
import com.team.saver.socket.repository.ChatRepository;
import com.team.saver.socket.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

import static com.team.saver.common.dto.ErrorMessage.NOT_FOUND_CHATROOM;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;

    public List<ChatResponse> findByAccountEmail(CurrentUser currentUser) {
        return chatRoomRepository.findByAccountEmail(currentUser.getEmail());
    }

    public List<ChatRoomResponse> findAllChatRoom(Pageable pageable) {
        return chatRoomRepository.findAllChatRoom(pageable);
    }

    @Transactional
    public void readChatRoom(long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_CHATROOM));

        chatRoom.readChat();
    }

    @Transactional
    public Chat saveChatData(ChatRequest chatRequest, boolean isAdmin) {
        ChatRoom chatRoom = chatRoomRepository.findByAccountId(chatRequest.getAccountId())
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_CHATROOM));

        if(!isAdmin) {
            chatRoom.newChat();
        }

        Chat chat = Chat.createEntity(chatRequest.getMessage(), isAdmin);
        chatRoom.addChat(chat);

        return chat;
    }

}

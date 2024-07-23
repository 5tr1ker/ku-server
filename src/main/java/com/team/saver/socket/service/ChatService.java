package com.team.saver.socket.service;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.socket.dto.ChatResponse;
import com.team.saver.socket.dto.ChatRoomResponse;
import com.team.saver.socket.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;

    public List<ChatResponse> findByAccountEmail(CurrentUser currentUser) {
        return chatRoomRepository.findByAccountEmail(currentUser.getEmail());
    }

    public List<ChatRoomResponse> findAllChatRoom(Pageable pageable) {
        return chatRoomRepository.findAllChatRoom(pageable);
    }

}

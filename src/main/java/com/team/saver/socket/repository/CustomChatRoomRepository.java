package com.team.saver.socket.repository;

import com.team.saver.socket.dto.ChatResponse;
import com.team.saver.socket.dto.ChatRoomResponse;
import com.team.saver.socket.entity.ChatRoom;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomChatRoomRepository {

    Optional<ChatRoom> findByAccountId(long accountId);

    List<ChatResponse> findByAccountEmail(String email);

    List<ChatRoomResponse> findAllChatRoom(Pageable pageable);


}

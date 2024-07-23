package com.team.saver.socket.repository;

import com.team.saver.socket.entity.ChatRoom;

import java.util.Optional;

public interface CustomChatRoomRepository {

    Optional<ChatRoom> findByAccountId(long accountId);

}

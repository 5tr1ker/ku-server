package com.team.saver.socket.repository;

import com.team.saver.account.entity.Account;
import com.team.saver.socket.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, CustomChatRoomRepository {

    Optional<ChatRoom> findByAccount(Account account);

}

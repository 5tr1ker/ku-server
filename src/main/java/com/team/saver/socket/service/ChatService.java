package com.team.saver.socket.service;

import com.team.saver.account.entity.Account;
import com.team.saver.account.service.AccountService;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.socket.entity.ChatRoom;
import com.team.saver.socket.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.team.saver.common.dto.ErrorMessage.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final AccountService accountService;

    public ChatRoom findOrCreateChatRoomByAccountId(CurrentUser currentUser) {
        Account account = accountService.getProfile(currentUser);

        return chatRoomRepository.findByAccount(account)
                .orElseGet(() -> chatRoomRepository.save(createChatRoom(account)));
    }

    private ChatRoom createChatRoom(Account account) {
        return ChatRoom.builder()
                .account(account)
                .build();
    }

}

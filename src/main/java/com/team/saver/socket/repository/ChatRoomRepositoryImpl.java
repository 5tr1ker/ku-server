package com.team.saver.socket.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.socket.dto.ChatResponse;
import com.team.saver.socket.dto.ChatRoomResponse;
import com.team.saver.socket.entity.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import static com.team.saver.account.entity.QAccount.account;
import static com.team.saver.socket.entity.QChatRoom.chatRoom;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements CustomChatRoomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<ChatRoom> findByAccountId(long accountId) {
        ChatRoom result = jpaQueryFactory.select(chatRoom)
                .from(chatRoom)
                .innerJoin(chatRoom.account, account).on(account.accountId.eq(accountId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<ChatResponse> findByAccountEmail(String email) {
        return null;
    }

    @Override
    public List<ChatRoomResponse> findAllChatRoom(Pageable pageable) {
        return null;
    }
}

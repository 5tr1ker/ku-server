package com.team.saver.socket.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.socket.entity.ChatRoom;
import lombok.RequiredArgsConstructor;

import static com.team.saver.account.entity.QAccount.account;
import static com.team.saver.socket.entity.QChatRoom.chatRoom;
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
}

package com.team.saver.socket.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.socket.dto.ChatResponse;
import com.team.saver.socket.dto.ChatRoomResponse;
import com.team.saver.socket.entity.ChatRoom;
import com.team.saver.socket.entity.QChat;
import com.team.saver.socket.entity.QChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import static com.querydsl.jpa.JPAExpressions.select;
import static com.team.saver.socket.entity.QChat.chat;
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
                .leftJoin(chatRoom.chat).fetchJoin()
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<ChatResponse> findByAccountEmail(String email) {
        return jpaQueryFactory.select(
                        Projections.constructor(ChatResponse.class,
                                chat.chatId,
                                chat.message,
                                chat.sendTime,
                                chat.isAdmin
                        )
                ).from(chatRoom)
                .orderBy(chat.chatId.desc())
                .innerJoin(chatRoom.account, account).on(account.email.eq(email))
                .leftJoin(chatRoom.chat, chat)
                .fetch();
    }

    @Override
    public List<ChatRoomResponse> findAllChatRoom(Pageable pageable) {
        QChat qChat = new QChat("qChat2");

        return jpaQueryFactory.select(
                        Projections.constructor(ChatRoomResponse.class,
                                chatRoom.chatRoomId,
                                account.accountId,
                                account.email,
                                account.profileImage,
                                chat.sendTime,
                                chat.message
                        )
                ).from(chatRoom)
                .groupBy(chatRoom.chatRoomId)
                .innerJoin(chatRoom.account, account)
                .leftJoin(chatRoom.chat, chat)
                .where(chat.chatId.eq(
                        select(qChat.chatId.max())
                                .from(qChat)
                                .where(qChat.chatRoom.chatRoomId.eq(chatRoom.chatRoomId))
                ))
                .orderBy(chat.sendTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

}

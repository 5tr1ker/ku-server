package com.team.saver.socket.entity;

import com.team.saver.account.entity.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long chatRoomId;

    @JoinColumn(nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private Account account;

    @Builder.Default
    @OneToMany(mappedBy = "chatRoom", cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, orphanRemoval = true)
    private List<Chat> chat = new ArrayList<>();

    public void addChat(Chat chat) {
        this.chat.add(chat);

        chat.setChatRoom(this);
    }

}

package com.team.saver.socket.entity;

import com.team.saver.account.entity.Account;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Chat {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long chatId;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime sendTime;

    @Column(nullable = false)
    private boolean isAdmin;

    @Setter
    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom chatRoom;

    public static Chat createEntity(String message, boolean isAdmin) {
        return Chat.builder()
                .message(message)
                .isAdmin(isAdmin)
                .build();
    }

}

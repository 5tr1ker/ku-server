package com.team.saver.socket.controller;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.socket.dto.ChatResponse;
import com.team.saver.socket.dto.ChatRoomResponse;
import com.team.saver.socket.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/v1/services/chat-rooms/chats")
    @Operation(summary = "[ 로그인 ] 내 1:1 고객센터 문의 가져오기 ( 105 )")
    public ResponseEntity findByAccountEmail(@Parameter(hidden = true) @LogIn CurrentUser currentUser) {
        List<ChatResponse> result = chatService.findByAccountEmail(currentUser);

        return ResponseEntity.ok(result);
    }

    @PatchMapping("/v1/admin/services/chat-room/{chatRoomId}/read")
    @Operation(summary = "[ 어드민-전용 ] 해당 채팅방 읽음 처리 ( 110 )")
    public ResponseEntity readChatRoom(@PathVariable long chatRoomId) {
        chatService.readChatRoom(chatRoomId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/v1/admin/services/chat-rooms/all")
    @Operation(summary = "[ 어드민-전용 ] 모든 고객센터 문의 데이터방 가져오기 ( 106 )")
    public ResponseEntity findAllChatRoom(Pageable pageable) {
        List<ChatRoomResponse> result = chatService.findAllChatRoom(pageable);

        return ResponseEntity.ok(result);
    }

}

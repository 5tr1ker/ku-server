package com.team.saver.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
public class SecurityController {

    @GetMapping("/is-connect")
    @Operation(summary = "서버와 연결 여부 확인 API")
    public ResponseEntity checkConnection() {

        return ResponseEntity.ok().build();
    }

}

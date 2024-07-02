package com.team.saver.announce.controller;

import com.team.saver.announce.service.AnnounceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AnnounceController {

    private final AnnounceService announceService;

}

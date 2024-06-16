package com.team.saver.attraction.controller;

import com.team.saver.attraction.service.AttractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/attraction")
public class AttractionController {

    private final AttractionService attractionService;

}

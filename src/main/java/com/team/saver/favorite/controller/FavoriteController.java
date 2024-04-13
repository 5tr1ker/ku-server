package com.team.saver.favorite.controller;

import com.team.saver.favorite.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorite")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping
    public ResponseEntity addFavorite(@AuthenticationPrincipal UserDetails userDetails,
                            @RequestParam long marketId) {

        favoriteService.addFavorite(userDetails, marketId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}

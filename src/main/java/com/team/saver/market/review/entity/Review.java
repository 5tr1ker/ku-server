package com.team.saver.market.review.entity;

import com.team.saver.account.entity.Account;
import com.team.saver.market.review.dto.ReviewRequest;
import com.team.saver.market.store.entity.Market;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account reviewer;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    private Market market;

    private int score;

    public static Review createEntity(Account account, ReviewRequest request) {
        return Review.builder()
                .reviewer(account)
                .content(request.getContent())
                .score(request.getScore())
                .build();
    }

}

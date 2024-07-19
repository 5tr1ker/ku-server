package com.team.saver.market.review.entity;

import com.team.saver.account.entity.Account;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRecommender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewRecommenderId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Review review;

    public static ReviewRecommender createEntity(Account account, Review review) {
        return ReviewRecommender.builder()
                .account(account)
                .review(review)
                .build();
    }
    
}

package com.team.saver.market.review.entity;

import com.team.saver.account.entity.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;

    public static ReviewRecommender createEntity(Account account, Review review) {
        return ReviewRecommender.builder()
                .account(account)
                .review(review)
                .build();
    }

    public void setReview(Review review) {
        this.review = review;
    }
}

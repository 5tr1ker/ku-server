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
public class Review {

    @Id @GeneratedValue
    private long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account reviewer;

    private String reviewMessage;

    private int score;

}

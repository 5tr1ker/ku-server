package com.team.saver.market.review.entity;

import com.team.saver.account.entity.Account;
import com.team.saver.market.review.dto.ReviewRequest;
import com.team.saver.market.review.dto.ReviewUpdateRequest;
import com.team.saver.market.store.entity.Market;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @JoinColumn(nullable = false)
    private Account reviewer;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime writeTime;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    @JoinColumn(nullable = false)
    private Market market;

    @Column(nullable = false)
    private int score;

    @OneToMany(cascade = { CascadeType.PERSIST , CascadeType.REMOVE }, orphanRemoval = true, mappedBy = "review")
    @Builder.Default
    private List<ReviewRecommender> recommender = new ArrayList<>();

    public static Review createEntity(Account account, ReviewRequest request) {
        return Review.builder()
                .reviewer(account)
                .title(request.getTitle())
                .content(request.getContent())
                .score(request.getScore())
                .build();
    }

    public void addRecommender(ReviewRecommender reviewRecommender) {
        recommender.add(reviewRecommender);
        reviewRecommender.setReview(this);
    }

    public void update(ReviewUpdateRequest request) {
        this.content = request.getContent();
        this.score = request.getScore();
    }
}

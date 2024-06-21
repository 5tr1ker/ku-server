package com.team.saver.market.review.entity;

import com.team.saver.account.entity.Account;
import com.team.saver.market.order.entity.Order;
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
    private String content;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "review")
    @JoinColumn
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    @JoinColumn(nullable = false)
    private Market market;

    @Column(nullable = false)
    private int score;

    @Builder.Default
    private boolean isDelete = false;

    @Builder.Default
    @OneToMany(cascade = { CascadeType.PERSIST , CascadeType.REMOVE }, orphanRemoval = true, mappedBy = "review")
    private List<ReviewRecommender> recommender = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE} , orphanRemoval = true)
    private List<ReviewImage> reviewImage = new ArrayList<>();

    public static Review createEntity(Account account, ReviewRequest request, Order order) {
        Review review = Review.builder()
                .reviewer(account)
                .order(order)
                .content(request.getContent())
                .score(request.getScore())
                .build();

        order.setReview(review);

        return review;
    }

    public void addReviewImage(String imageUrl) {
        reviewImage.add(ReviewImage.createEntity(imageUrl));
    }

    public void addRecommender(ReviewRecommender reviewRecommender) {
        recommender.add(reviewRecommender);
        reviewRecommender.setReview(this);
    }

    public void update(ReviewUpdateRequest request) {
        this.content = request.getContent();
        this.score = request.getScore();
    }

    public void delete() {
        this.isDelete = true;
    }
}

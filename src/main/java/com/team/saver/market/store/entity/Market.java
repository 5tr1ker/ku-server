package com.team.saver.market.store.entity;

import com.team.saver.market.coupon.entity.Coupon;
import com.team.saver.market.review.entity.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Market {

    @Id @GeneratedValue
    private long marketId;

    @Enumerated(EnumType.STRING)
    private MainCategory mainCategory;

    @OneToMany(mappedBy = "market" , cascade = {CascadeType.PERSIST, CascadeType.REMOVE} , orphanRemoval = true)
    @Builder.Default
    private List<Coupon> coupons = new ArrayList<>();

    @OneToMany(mappedBy = "market" , cascade = {CascadeType.PERSIST, CascadeType.REMOVE} , orphanRemoval = true)
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    private double locationX;

    private double locationY;

    private String marketName;

    private String marketDescription;

    private String detailAddress;

    public void addCoupon(Coupon coupon) {
        coupon.setMarket(this);
        coupons.add(coupon);
    }

    public void addReview(Review review) {
        review.setMarket(this);
        reviews.add(review);
    }

}

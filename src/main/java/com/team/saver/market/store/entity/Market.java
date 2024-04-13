package com.team.saver.market.store.entity;

import com.team.saver.account.entity.Account;
import com.team.saver.market.coupon.entity.Coupon;
import com.team.saver.market.review.entity.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long marketId;

    @Enumerated(EnumType.STRING)
    private MainCategory mainCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account partner;

    @OneToMany(mappedBy = "market", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @Builder.Default
    private List<Coupon> coupons = new ArrayList<>();

    @OneToMany(mappedBy = "market", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @Builder.Default
    private List<Menu> menus = new ArrayList<>();

    private double locationX;

    private double locationY;

    private String marketName;

    private String marketDescription;

    private String detailAddress;

    private LocalTime openTime;

    private LocalTime closeTime;

    private String marketPhone;

    public void addCoupon(Coupon coupon) {
        coupon.setMarket(this);
        coupons.add(coupon);
    }

    public void addReview(Review review) {
        review.setMarket(this);
        reviews.add(review);
    }

    public void addMenu(Menu menu) {
        menus.add(menu);
    }

}

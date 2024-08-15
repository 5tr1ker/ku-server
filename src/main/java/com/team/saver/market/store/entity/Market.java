package com.team.saver.market.store.entity;

import com.team.saver.account.entity.Account;
import com.team.saver.market.coupon.entity.Coupon;
import com.team.saver.market.review.entity.Review;
import com.team.saver.market.store.dto.MarketCreateRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
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
    @Column(nullable = false)
    private MainCategory mainCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Account partner;

    @OneToMany(mappedBy = "market", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @Builder.Default
    private List<Coupon> coupons = new ArrayList<>();

    @OneToMany(mappedBy = "market", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "market", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @Builder.Default
    private List<MenuContainer> menuContainers = new ArrayList<>();

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDate createDate;

    @Column(nullable = false)
    private double locationX;

    @Column(nullable = false)
    private int cookingTime;

    @Column(nullable = false)
    private String marketImage;

    private int minimumOrderPrice;

    @Column(nullable = false)
    private double locationY;

    @Column(nullable = false)
    private String marketName;

    @Column(nullable = false)
    private String marketDescription;

    @Column(nullable = false)
    private String detailAddress;

    @Setter
    private String eventMessage;

    private LocalTime openTime;

    private LocalTime closeTime;

    private String closedDays;

    private String marketPhone;

    @Builder.Default
    @Column(nullable = false)
    private boolean isDelete = false;

    @Builder.Default
    @Column(nullable = false)
    private boolean eventCouponMarket = false;

    public void addCoupon(Coupon coupon) {
        coupon.setMarket(this);
        coupons.add(coupon);
    }

    public void addReview(Review review) {
        review.setMarket(this);
        reviews.add(review);
    }

    public void addMenuContainer(MenuContainer menuContainer) {
        menuContainers.add(menuContainer);

        menuContainer.setMarket(this);
    }

    public static Market createEntity(Account account, MarketCreateRequest request, String marketImage) {
        return Market.builder()
                .mainCategory(request.getMainCategory())
                .partner(account)
                .locationX(request.getLocationX())
                .locationY(request.getLocationY())
                .cookingTime(request.getCookingTime())
                .marketName(request.getMarketName())
                .marketImage(marketImage)
                .marketDescription(request.getMarketDescription())
                .detailAddress(request.getDetailAddress())
                .openTime(request.getOpenTime())
                .closeTime(request.getCloseTime())
                .closedDays(request.getClosedDays())
                .marketPhone(request.getMarketPhone())
                .build();
    }

}

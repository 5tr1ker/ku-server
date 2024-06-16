package com.team.saver.market.coupon.entity;

import com.team.saver.market.coupon.dto.CouponCreateRequest;
import com.team.saver.market.store.entity.Market;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long couponId;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    @JoinColumn(nullable = false)
    private Market market;

    @Column(nullable = false)
    private String couponName;

    @Column(nullable = false)
    private String couponDescription;

    @Builder.Default
    @Column(nullable = false)
    private int saleRate = 0;

    public static Coupon createEntity(CouponCreateRequest request) {
        return Coupon.builder()
                .couponName(request.getCouponName())
                .couponDescription(request.getCouponDescription())
                .saleRate(request.getSaleRate())
                .build();
    }

}

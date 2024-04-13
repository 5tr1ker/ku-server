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
    @GeneratedValue
    private long couponId;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    private Market market;

    private String couponName;

    private String couponDescription;

    private double saleRate;

    public static Coupon createCoupon(CouponCreateRequest request, Market market) {
        return Coupon.builder()
                .market(market)
                .couponName(request.getCouponName())
                .couponDescription(request.getCouponDescription())
                .saleRate(request.getSaleRate())
                .build();
    }

}

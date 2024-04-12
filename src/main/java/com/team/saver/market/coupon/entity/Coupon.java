package com.team.saver.market.coupon.entity;

import com.team.saver.market.store.entity.Market;
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
public class Coupon {

    @Id @GeneratedValue
    private long couponId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Market market;

    private String couponName;

    private String couponDescription;

    private double saleRate;

}

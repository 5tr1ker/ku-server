package com.team.saver.market.coupon.entity;

import com.team.saver.market.store.entity.Market;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {

    @Id @GeneratedValue
    private long couponId;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    private Market market;

    private String couponName;

    private String couponDescription;

    private double saleRate;

}

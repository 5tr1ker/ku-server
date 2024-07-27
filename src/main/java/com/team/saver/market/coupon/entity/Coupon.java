package com.team.saver.market.coupon.entity;

import com.team.saver.market.coupon.dto.CouponCreateRequest;
import com.team.saver.market.store.entity.Market;
import jakarta.persistence.*;
import lombok.*;

import java.text.NumberFormat;
import java.util.Locale;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long couponId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Market market;

    @Column(nullable = false)
    private String couponName;

    @Column(nullable = false)
    private ConditionToUse conditionToUse;

    @Column(nullable = false)
    private int conditionToUseAmount;

    @Column(nullable = false)
    private String couponDescription;

    @Builder.Default
    @Column(nullable = false)
    private int saleRate = 0;

    public static Coupon createEntity(CouponCreateRequest request) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        String formattedNumber = numberFormat.format(request.getConditionToUseAmount());

        return Coupon.builder()
                .couponName(request.getCouponName())
                .couponDescription(String.format(ConditionToUse.MINIMUM_AMOUNT.getDescription(), formattedNumber))
                .conditionToUse(request.getConditionToUse())
                .conditionToUseAmount(request.getConditionToUseAmount())
                .saleRate(request.getSaleRate())
                .build();
    }

}

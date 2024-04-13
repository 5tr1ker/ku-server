package com.team.saver.market.coupon.entity;

import com.team.saver.account.entity.Account;
import com.team.saver.market.store.entity.Market;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class DownloadCoupon {

    @Id
    @GeneratedValue
    private long downloadCouponId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    private Market market;

    @Builder.Default
    private boolean isUsage = false;

    @Builder.Default
    private LocalDateTime useDate = null;

    public void updateIsUsage() {
        isUsage = true;
        useDate = LocalDateTime.now();
    }

    public static DownloadCoupon createEntity(Account account, Coupon coupon, Market market) {
        return DownloadCoupon.builder()
                .account(account)
                .coupon(coupon)
                .market(market)
                .build();
    }

}

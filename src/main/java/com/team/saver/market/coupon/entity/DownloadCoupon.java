package com.team.saver.market.coupon.entity;

import com.team.saver.account.entity.Account;
import com.team.saver.market.store.entity.Market;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class DownloadCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long downloadCouponId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Account account;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Market market;

    @Builder.Default
    @Column(nullable = false)
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

package com.team.saver.market.coupon.entity;

import com.team.saver.market.coupon.dto.CouponCreateRequest;
import com.team.saver.market.store.entity.Market;
import jakarta.persistence.*;
import lombok.*;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "coupon")
    private List<DownloadCoupon> downloadCoupons = new ArrayList<>();

    @Builder.Default
    @Column(nullable = false)
    private long priority = 0;

    @Builder.Default
    @Column(nullable = false)
    private int saleRate = 0;

    @Column(nullable = false)
    private LocalDateTime expireDate;

    @Builder.Default
    @Column(nullable = false)
    private boolean isDelete = false;

    public static Coupon createEntity(CouponCreateRequest request) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        String formattedNumber = numberFormat.format(request.getConditionToUseAmount());

        return Coupon.builder()
                .couponName(request.getCouponName())
                .couponDescription(String.format(ConditionToUse.MINIMUM_AMOUNT.getDescription(), formattedNumber))
                .conditionToUse(request.getConditionToUse())
                .conditionToUseAmount(request.getConditionToUseAmount())
                .expireDate(request.getExpireDate())
                .saleRate(request.getSaleRate())
                .build();
    }

    public void addDownloadCoupon(DownloadCoupon downloadCoupon) {
        this.downloadCoupons.add(downloadCoupon);

        downloadCoupon.setCoupon(this);
    }

    public void delete() {
        this.isDelete = true;
    }

}

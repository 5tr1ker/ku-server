package com.team.saver.market.coupon.repository;

import com.team.saver.market.coupon.entity.DownloadCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponDownloadRepository extends JpaRepository<DownloadCoupon, Long> {
}

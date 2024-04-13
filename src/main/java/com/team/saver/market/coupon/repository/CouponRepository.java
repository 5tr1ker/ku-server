package com.team.saver.market.coupon.repository;

import com.team.saver.market.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long>, CustomCouponRepository {
}

package com.team.saver.market.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderCreateRequest {

    private long couponId;

    private long marketId;

    private List<Long> basketMenuId;

    private PaymentType paymentType;

}

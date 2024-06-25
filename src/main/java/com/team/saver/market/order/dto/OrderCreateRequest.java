package com.team.saver.market.order.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderCreateRequest {

    private long couponId;

    private List<Long> menuIdList;

    private String deliveryAddress;

    private String deliveryAddressDetail;

    private String phoneNumber;

}

package com.team.saver.market.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.saver.market.order.entity.OrderMenu;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderDetailResponse {

    private long orderId;

    private String marketName;

    private long marketId;

    private List<OrderMenu> orderMenu;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime orderDateTime;

    private String orderNumber;

    private String deliveryAddress;

    private String deliveryAddressDetail;

    private String phoneNumber;

    private int orderPrice;

    private int discountAmount;

    private int finalPrice;

}

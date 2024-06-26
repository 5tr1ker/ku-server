package com.team.saver.market.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.saver.market.order.entity.OrderMenu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrderDetailResponse {

    private long orderId;

    private String marketName;

    private long marketId;

    @Setter
    private List<OrderMenu> orderMenus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime orderDateTime;

    private String orderNumber;

    private String deliveryAddress;

    private String deliveryAddressDetail;

    private String phoneNumber;

    private int orderPrice;

    private int discountAmount;

    private int finalPrice;

    public OrderDetailResponse(long orderId, String marketName, long marketId, LocalDateTime orderDateTime, String orderNumber, String deliveryAddress, String deliveryAddressDetail, String phoneNumber, int orderPrice, int discountAmount, int finalPrice) {
        this.orderId = orderId;
        this.marketName = marketName;
        this.marketId = marketId;
        this.orderDateTime = orderDateTime;
        this.orderNumber = orderNumber;
        this.deliveryAddress = deliveryAddress;
        this.deliveryAddressDetail = deliveryAddressDetail;
        this.phoneNumber = phoneNumber;
        this.orderPrice = orderPrice;
        this.discountAmount = discountAmount;
        this.finalPrice = finalPrice;
    }

}

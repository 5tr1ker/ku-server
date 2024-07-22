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

    private long marketId;

    private String marketName;

    private String marketPhoneNumber;

    private String marketAddress;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime orderDateTime;

    private String orderNumber;

    private long orderPrice;

    private int discountAmount;

    private PaymentType paymentType;

    private long finalPrice;

    private List<OrderMenuResponse> orderMenus;

}

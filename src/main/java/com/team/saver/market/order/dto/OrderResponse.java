package com.team.saver.market.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.saver.market.order.entity.Order;
import com.team.saver.market.order.entity.OrderMenu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {

    private long orderId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime orderDate;

    private String marketName;

    private String marketImage;

    private long amountOfPayment;

    private long menuCount;

    private String show_menu;

}

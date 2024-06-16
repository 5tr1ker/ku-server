package com.team.saver.market.order.entity;

import com.team.saver.market.order.dto.NewOrderRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderDetailId;

    @Setter
    @OneToOne(mappedBy = "order")
    private Order order;

    @CreationTimestamp
    private LocalDateTime orderDateTime;

    private String orderNumber;

    private String deliveryAddress;

    private String deliveryAddressDetail;

    private String phoneNumber;

    private int orderPrice;

    private int discountAmount;

    private int finalPrice;

    public static OrderDetail createEntity(NewOrderRequest request, String orderNumber, int orderPrice, int discountAmount) {
        return OrderDetail.builder()
                .deliveryAddress(request.getDeliveryAddress())
                .deliveryAddressDetail(request.getDeliveryAddressDetail())
                .phoneNumber(request.getPhoneNumber())
                .orderNumber(orderNumber)
                .orderPrice(orderPrice)
                .discountAmount(discountAmount)
                .finalPrice(orderPrice - discountAmount)
                .build();
    }

}

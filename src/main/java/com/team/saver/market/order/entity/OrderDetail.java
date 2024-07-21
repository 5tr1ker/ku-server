package com.team.saver.market.order.entity;

import com.team.saver.market.order.dto.OrderCreateRequest;
import com.team.saver.market.order.dto.PaymentType;
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
    @OneToOne(mappedBy = "orderDetail")
    private Order order;

    @CreationTimestamp
    private LocalDateTime orderDateTime;

    private String orderNumber;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private long orderPrice;

    private int discountAmount;

    private long finalPrice;

    public static OrderDetail createEntity(PaymentType paymentType, String orderNumber, long orderPrice, int discountAmount) {
        return OrderDetail.builder()
                .orderNumber(orderNumber)
                .orderPrice(orderPrice)
                .discountAmount(discountAmount)
                .paymentType(paymentType)
                .finalPrice(orderPrice - discountAmount)
                .build();
    }

}

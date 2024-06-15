package com.team.saver.market.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

}

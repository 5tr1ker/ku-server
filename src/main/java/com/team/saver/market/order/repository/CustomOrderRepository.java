package com.team.saver.market.order.repository;

import com.team.saver.market.order.dto.OrderDetailResponse;
import com.team.saver.market.order.dto.OrderResponse;
import com.team.saver.market.order.entity.Order;

import java.util.List;
import java.util.Optional;

public interface CustomOrderRepository {

    Optional<Order> findByIdAndUserEmail(String email, long orderId);

    List<OrderResponse> findOrderByUserEmail(String email);

    Optional<OrderDetailResponse> getOrderDetailByOrderIdAndEmail(long orderId, String email);
}

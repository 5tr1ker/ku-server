package com.team.saver.market.order.repository;

import com.team.saver.market.order.dto.OrderDetailResponse;
import com.team.saver.market.order.entity.Order;
import com.team.saver.market.order.entity.OrderMenu;

import java.util.List;
import java.util.Optional;

public interface CustomOrderRepository {

    Optional<Order> findByIdAndUserEmail(String email, long orderId);

    List<Order> findOrderDataByUserEmail(String email);

    Optional<OrderDetailResponse> findOrderDetailByOrderIdAndEmail(long orderId, String email);

    List<OrderMenu> findOrderMenuByOrderId(long orderId);

}

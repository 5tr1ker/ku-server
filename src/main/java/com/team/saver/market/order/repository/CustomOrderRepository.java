package com.team.saver.market.order.repository;

import com.team.saver.market.order.dto.OrderDetailResponse;
import com.team.saver.market.order.dto.OrderResponse;
import com.team.saver.market.order.entity.Order;
import com.team.saver.market.order.entity.OrderMenu;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomOrderRepository {

    Optional<Order> findByIdAndUserEmail(String email, long orderId);

    List<OrderResponse> findOrderDataByUserEmail(String email, boolean existReview, Pageable pageable);

    Optional<OrderDetailResponse> findOrderDetailByOrderIdAndEmail(long orderId, String email);

}

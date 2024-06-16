package com.team.saver.market.order.repository;

import com.team.saver.market.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, CustomOrderRepository {

}

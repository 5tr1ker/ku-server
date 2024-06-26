package com.team.saver.market.order.service;

import com.team.saver.account.entity.Account;
import com.team.saver.account.repository.AccountRepository;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.market.coupon.entity.Coupon;
import com.team.saver.market.coupon.repository.CouponRepository;
import com.team.saver.market.order.dto.OrderCreateRequest;
import com.team.saver.market.order.dto.OrderDetailResponse;
import com.team.saver.market.order.dto.OrderResponse;
import com.team.saver.market.order.entity.Order;
import com.team.saver.market.order.entity.OrderDetail;
import com.team.saver.market.order.entity.OrderMenu;
import com.team.saver.market.order.repository.OrderRepository;
import com.team.saver.market.store.entity.Market;
import com.team.saver.market.store.entity.Menu;
import com.team.saver.market.store.repository.MarketRepository;
import com.team.saver.market.store.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.team.saver.common.dto.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MarketRepository marketRepository;
    private final AccountRepository accountRepository;
    private final CouponRepository couponRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public void addOrder(CurrentUser currentUser, long marketId, OrderCreateRequest request) {
        Order order = createOrder(currentUser.getEmail(), marketId);
        int totalPrice = addOrderMenuAndReturnTotalPrice(order, findMenuListByMenuIdList(request.getMenuIds()));

        OrderDetail orderDetail = createOrderDetail(request, marketId, totalPrice);
        order.setOrderDetail(orderDetail);

        orderRepository.save(order);
    }

    private List<Menu> findMenuListByMenuIdList(List<Long> menuIdList) {
        return menuRepository.findAllById(menuIdList);
    }

    private Order createOrder(String userEmail, long marketId) {
        Market market = marketRepository.findMarketAndMenuByMarketId(marketId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_MARKET));
        Account account = accountRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_USER));

        return Order.createEntity(market, account);
    }

    private OrderDetail createOrderDetail(OrderCreateRequest request, long marketId , int totalPrice) {
        int saleRate = getSaleRateByCouponIdAndMarketId(request.getCouponId(), marketId);

        String orderNumber = UUID.randomUUID().toString().replace('-' , 'Z').toUpperCase().substring(0 , 13);

        return OrderDetail.createEntity(request, orderNumber, totalPrice, saleRate);
    }

    private int getSaleRateByCouponIdAndMarketId(long couponId, long marketId) {
        if(couponId != 0) {
            Coupon coupon = couponRepository.findByIdAndMarketId(couponId, marketId)
                    .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_COUPON));

            return coupon.getSaleRate();
        }

        return 0;
    }

    private int addOrderMenuAndReturnTotalPrice(Order order, List<Menu> menuList) {
        int totalPrice = 0;

        for(Menu menu : menuList) {
            OrderMenu orderMenu = OrderMenu.createEntity(menu);
            totalPrice += orderMenu.getPrice();

            order.addOrderMenu(orderMenu);
        }

        return totalPrice;
    }

    @Transactional
    public void deleteOrder(CurrentUser currentUser, long orderId) {
        Order order = orderRepository.findByIdAndUserEmail(currentUser.getEmail(), orderId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_ORDER));

        orderRepository.delete(order);
    }

    public List<OrderResponse> findOrderByUserEmail(CurrentUser currentUser, boolean existReview) {
        List<Order> result = orderRepository.findOrderDataByUserEmail(currentUser.getEmail(), existReview);

        return result.stream().map(OrderResponse::createEntity)
                .collect(Collectors.toList());
    }

    public OrderDetailResponse getOrderDetailByOrderIdAndEmail(CurrentUser currentUser, long orderId) {
        OrderDetailResponse result = orderRepository.findOrderDetailByOrderIdAndEmail(orderId, currentUser.getEmail())
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_ORDER_DETAIL));

        result.setOrderMenus(orderRepository.findOrderMenuByOrderId(orderId));
        return result;
    }

}

package com.team.saver.market.order.service;

import com.team.saver.account.entity.Account;
import com.team.saver.account.repository.AccountRepository;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.ErrorMessage;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.market.coupon.entity.Coupon;
import com.team.saver.market.coupon.repository.CouponRepository;
import com.team.saver.market.order.dto.NewOrderRequest;
import com.team.saver.market.order.entity.Order;
import com.team.saver.market.order.entity.OrderDetail;
import com.team.saver.market.order.entity.OrderMenu;
import com.team.saver.market.order.repository.OrderRepository;
import com.team.saver.market.store.entity.Market;
import com.team.saver.market.store.entity.Menu;
import com.team.saver.market.store.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.team.saver.common.dto.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MarketRepository marketRepository;
    private final AccountRepository accountRepository;
    private final CouponRepository couponRepository;

    @Transactional
    public void addOrder(CurrentUser currentUser, NewOrderRequest request) {
        Order order = createOrder(currentUser.getEmail(), request.getMarketId());
        int totalPrice = addOrderMenuAndReturnTotalPrice(order, order.getMarket().getMenus());

        OrderDetail orderDetail = createOrderDetail(request, totalPrice);
        order.setOrderDetail(orderDetail);

        orderRepository.save(order);
    }

    private Order createOrder(String userEmail, long marketId) {
        Market market = marketRepository.findMarketAndMenuByMarketId(marketId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_MARKET));
        Account account = accountRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_USER));

        return Order.createEntity(market, account);
    }

    private OrderDetail createOrderDetail(NewOrderRequest request, int totalPrice) {
        Coupon coupon = couponRepository.findByIdAndMarketId(request.getCouponId(), request.getMarketId())
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_COUPON));
        String orderNumber = UUID.randomUUID().toString().replace('-' , 'Z').toUpperCase();

        return OrderDetail.createEntity(request, orderNumber, totalPrice, coupon.getSaleRate());
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

    public void deleteOrder() {

    }

    public void getOrderListAndEmail(CurrentUser currentUser) {

    }

    public void getOrderDetailByOrderIdAndEmail(CurrentUser currentUser, long orderId) {

    }

}

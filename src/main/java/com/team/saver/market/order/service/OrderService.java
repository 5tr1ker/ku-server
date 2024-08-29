package com.team.saver.market.order.service;

import com.team.saver.account.entity.Account;
import com.team.saver.account.repository.AccountRepository;
import com.team.saver.account.service.AccountService;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.market.basket.entity.Basket;
import com.team.saver.market.basket.entity.BasketMenu;
import com.team.saver.market.basket.entity.BasketMenuOption;
import com.team.saver.market.basket.repository.BasketMenuRepository;
import com.team.saver.market.basket.repository.BasketRepository;
import com.team.saver.market.coupon.entity.Coupon;
import com.team.saver.market.coupon.repository.CouponRepository;
import com.team.saver.market.order.dto.OrderCreateRequest;
import com.team.saver.market.order.dto.OrderDetailResponse;
import com.team.saver.market.order.dto.OrderResponse;
import com.team.saver.market.order.entity.Order;
import com.team.saver.market.order.entity.OrderDetail;
import com.team.saver.market.order.entity.OrderMenu;
import com.team.saver.market.order.entity.OrderOption;
import com.team.saver.market.order.repository.OrderRepository;
import com.team.saver.market.store.entity.Market;
import com.team.saver.market.store.entity.Menu;
import com.team.saver.market.store.entity.MenuOption;
import com.team.saver.market.store.repository.MarketRepository;
import com.team.saver.market.store.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.team.saver.common.dto.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CouponRepository couponRepository;
    private final BasketMenuRepository basketMenuRepository;
    private final MarketRepository marketRepository;
    private final AccountService accountService;

    @Transactional
    public Order addOrder(CurrentUser currentUser, OrderCreateRequest request) {
        List<BasketMenu> basketMenus = basketMenuRepository.findAllByAccountEmailAndId(currentUser.getEmail(), request.getBasketMenuId());
        Market market = marketRepository.findById(request.getMarketId())
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_MARKET));
        Account account = accountService.getProfile(currentUser);

        Order order = Order.createEntity(market, account);

        long amountOfPayment = 0;
        long optionPrice = 0;
        for(BasketMenu basketMenu : basketMenus) {
            List<MenuOption> menuOptions = basketMenuRepository.findMenuOptionById(basketMenu.getBasketMenuId());
            OrderMenu orderMenu = OrderMenu.createEntity(basketMenu);
            basketMenu.getMenu().addOrderCount();
            optionPrice = 0;

            for(MenuOption menuOption : menuOptions) {
                OrderOption orderOption = OrderOption.createEntity(menuOption);

                optionPrice += orderOption.getOptionPrice();
                orderMenu.addOption(orderOption);
            }
            amountOfPayment += (optionPrice + basketMenu.getMenu().getPrice()) * basketMenu.getAmount();

            order.addOrderMenu(orderMenu);
        }

        OrderDetail orderDetail = createOrderDetail(request, market, amountOfPayment);
        order.setOrderDetail(orderDetail);
        return orderRepository.save(order);
    }

    private OrderDetail createOrderDetail(OrderCreateRequest request, Market market , long amountOfPayment) {
        int saleRate = getSaleRateByCouponIdAndMarketId(request.getCouponId(), market.getMarketId());

        String orderNumber = UUID.randomUUID().toString().replace('-' , 'Z').toUpperCase().substring(0 , 13);

        return OrderDetail.createEntity(request.getPaymentType(), orderNumber, amountOfPayment, saleRate);
    }

    private int getSaleRateByCouponIdAndMarketId(long couponId, long marketId) {
        if(couponId != 0) {
            Coupon coupon = couponRepository.findByIdAndMarketId(couponId, marketId)
                    .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_COUPON));

            return coupon.getSaleRate();
        }

        return 0;
    }

    @Transactional
    public void deleteOrder(CurrentUser currentUser, long orderId) {
        Order order = orderRepository.findByIdAndUserEmail(currentUser.getEmail(), orderId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_ORDER));

        orderRepository.delete(order);
    }

    public List<OrderResponse> findOrderByUserEmail(CurrentUser currentUser, boolean existReview) {
        return orderRepository.findOrderDataByUserEmail(currentUser.getEmail(), existReview);
    }

    public OrderDetailResponse getOrderDetailByOrderIdAndEmail(CurrentUser currentUser, long orderId) {
        return orderRepository.findOrderDetailByOrderIdAndEmail(orderId, currentUser.getEmail())
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_ORDER_DETAIL));
    }

}

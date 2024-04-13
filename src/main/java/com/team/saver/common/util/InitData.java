package com.team.saver.common.init;

import com.team.saver.account.entity.Account;
import com.team.saver.account.entity.UserRole;
import com.team.saver.account.repository.AccountRepository;
import com.team.saver.market.coupon.entity.Coupon;
import com.team.saver.market.review.entity.Review;
import com.team.saver.market.store.dto.MarketResponse;
import com.team.saver.market.store.entity.MainCategory;
import com.team.saver.market.store.entity.Market;
import com.team.saver.market.store.entity.Menu;
import com.team.saver.market.store.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class InitData implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final MarketRepository marketRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Account account = Account.builder()
                .email("email@naver.com")
                .age("20")
                .phone("01012341234")
                .role(UserRole.NORMAL)
                .build();

        accountRepository.save(account);

        Account account2 = Account.builder()
                .email("email2@naver.com")
                .age("24")
                .phone("01046544654")
                .role(UserRole.NORMAL)
                .build();

        accountRepository.save(account2);

        Market market = Market.builder()
                .marketName("marketName")
                .marketDescription("description")
                .detailAddress("address")
                .mainCategory(MainCategory.RESTAURANT)
                .locationY(123.451)
                .locationX(431.176)
                .partner(account)
                .closeTime(LocalTime.now())
                .openTime(LocalTime.now())
                .marketPhone("01012341234")
                .build();

        Menu menu1 = Menu.builder().menuName("메뉴1").price(10000).build();
        market.addMenu(menu1);
        Menu menu2 = Menu.builder().menuName("메뉴2").price(8000).build();
        market.addMenu(menu2);
        Menu menu3 = Menu.builder().menuName("메뉴3").price(6000).build();
        market.addMenu(menu3);
        Menu menu4 = Menu.builder().menuName("메뉴4").price(18000).build();
        market.addMenu(menu4);
        Menu menu5 = Menu.builder().menuName("메뉴5").price(21000).build();
        market.addMenu(menu5);

        marketRepository.save(market);
    }
}
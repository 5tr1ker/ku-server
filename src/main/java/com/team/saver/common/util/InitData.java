package com.team.saver.common.init;

import com.team.saver.account.entity.Account;
import com.team.saver.account.entity.UserRole;
import com.team.saver.account.repository.AccountRepository;
import com.team.saver.market.coupon.entity.Coupon;
import com.team.saver.market.review.entity.Review;
import com.team.saver.market.store.entity.MainCategory;
import com.team.saver.market.store.entity.Market;
import com.team.saver.market.store.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitData implements CommandLineRunner {

    private final AccountRepository accountRepository;

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
    }
}
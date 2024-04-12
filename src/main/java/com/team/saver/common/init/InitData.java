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

        Market market = Market.builder()
                .marketDescription("description")
                .locationX(51.651)
                .locationY(123.124)
                .marketName("defgh")
                .mainCategory(MainCategory.RESTAURANT)
                .detailAddress("detailAddress")
                .build();
        market.addCoupon(Coupon.builder().saleRate(18.0).build());
        market.addCoupon(Coupon.builder().saleRate(16.0).build());
        market.addCoupon(Coupon.builder().saleRate(13.0).build());
        market.addCoupon(Coupon.builder().saleRate(17.0).build());
        market.addReview(Review.builder().score(3).build());
        market.addReview(Review.builder().score(1).build());
        market.addReview(Review.builder().score(5).build());
        market.addReview(Review.builder().score(4).build());
        marketRepository.save(market);

        Market market2 = Market.builder()
                .marketDescription("description")
                .locationX(57.651)
                .locationY(161.14)
                .marketName("abcde")
                .mainCategory(MainCategory.RESTAURANT)
                .detailAddress("detailAddress")
                .build();
        market2.addCoupon(Coupon.builder().saleRate(12.0).build());
        market2.addCoupon(Coupon.builder().saleRate(10.0).build());
        market2.addCoupon(Coupon.builder().saleRate(4.0).build());
        market2.addReview(Review.builder().score(1).build());
        market2.addReview(Review.builder().score(1).build());
        market2.addReview(Review.builder().score(3).build());
        market2.addReview(Review.builder().score(2).build());
        market2.addReview(Review.builder().score(2).build());
        market2.addReview(Review.builder().score(5).build());

        marketRepository.save(market2);

        Market market3 = Market.builder()
                .marketDescription("description")
                .locationX(13.651)
                .locationY(56.14)
                .marketName("abcvbzc")
                .mainCategory(MainCategory.CULTURE)
                .detailAddress("detailAddress")
                .build();
        market3.addCoupon(Coupon.builder().saleRate(8.0).build());
        market3.addCoupon(Coupon.builder().saleRate(3.0).build());
        market3.addCoupon(Coupon.builder().saleRate(5.0).build());
        market3.addReview(Review.builder().score(1).build());
        market3.addReview(Review.builder().score(4).build());
        market3.addReview(Review.builder().score(5).build());
        market3.addReview(Review.builder().score(4).build());

        marketRepository.save(market3);

        Market market3_1 = Market.builder()
                .marketDescription("description")
                .locationX(13.651)
                .locationY(56.14)
                .marketName("abcvbzc")
                .mainCategory(MainCategory.CULTURE)
                .detailAddress("detailAddress")
                .build();
        market3_1.addCoupon(Coupon.builder().saleRate(8.0).build());
        market3_1.addCoupon(Coupon.builder().saleRate(3.0).build());
        market3_1.addCoupon(Coupon.builder().saleRate(5.0).build());
        market3_1.addCoupon(Coupon.builder().saleRate(8.0).build());
        market3_1.addCoupon(Coupon.builder().saleRate(3.0).build());
        market3_1.addCoupon(Coupon.builder().saleRate(5.0).build());
        market3_1.addReview(Review.builder().score(1).build());
        market3_1.addReview(Review.builder().score(4).build());
        market3_1.addReview(Review.builder().score(5).build());
        market3_1.addReview(Review.builder().score(4).build());
        market3_1.addReview(Review.builder().score(1).build());
        market3_1.addReview(Review.builder().score(4).build());
        market3_1.addReview(Review.builder().score(5).build());
        market3_1.addReview(Review.builder().score(4).build());

        marketRepository.save(market3_1);

        Market market4 = Market.builder()
                .marketDescription("description")
                .locationX(131.651)
                .locationY(56.14)
                .marketName("abcabc")
                .mainCategory(MainCategory.CULTURE)
                .detailAddress("detailAddress")
                .build();
        market4.addCoupon(Coupon.builder().saleRate(15.0).build());
        market4.addCoupon(Coupon.builder().saleRate(14.0).build());
        market4.addCoupon(Coupon.builder().saleRate(17.0).build());
        market4.addReview(Review.builder().score(5).build());
        market4.addReview(Review.builder().score(4).build());
        market4.addReview(Review.builder().score(5).build());
        market4.addReview(Review.builder().score(4).build());

        marketRepository.save(market4);

        Market market4_1 = Market.builder()
                .marketDescription("description")
                .locationX(131.651)
                .locationY(56.14)
                .marketName("abcabc")
                .mainCategory(MainCategory.CULTURE)
                .detailAddress("detailAddress")
                .build();
        market4_1.addCoupon(Coupon.builder().saleRate(15.0).build());
        market4_1.addCoupon(Coupon.builder().saleRate(14.0).build());
        market4_1.addCoupon(Coupon.builder().saleRate(17.0).build());
        market4_1.addReview(Review.builder().score(5).build());
        market4_1.addReview(Review.builder().score(4).build());
        market4_1.addReview(Review.builder().score(5).build());
        market4_1.addReview(Review.builder().score(4).build());
        market4_1.addCoupon(Coupon.builder().saleRate(15.0).build());
        market4_1.addCoupon(Coupon.builder().saleRate(14.0).build());
        market4_1.addCoupon(Coupon.builder().saleRate(17.0).build());
        market4_1.addReview(Review.builder().score(5).build());
        market4_1.addReview(Review.builder().score(4).build());
        market4_1.addReview(Review.builder().score(5).build());
        market4_1.addReview(Review.builder().score(4).build());

        marketRepository.save(market4_1);

        Market market5 = Market.builder()
                .marketDescription("description")
                .locationX(1341.651)
                .locationY(5516.14)
                .marketName("aaaaa")
                .mainCategory(MainCategory.CULTURE)
                .detailAddress("detailAddress")
                .build();
        market5.addReview(Review.builder().score(3).build());

        marketRepository.save(market5);

    }
}

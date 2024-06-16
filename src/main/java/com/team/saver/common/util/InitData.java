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
import com.team.saver.market.store.util.RecommendAlgorithm;
import com.team.saver.oauth.util.OAuthType;
import com.team.saver.search.popular.util.SearchWordScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class InitData implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final MarketRepository marketRepository;
    private final RecommendAlgorithm recommendAlgorithm;
    private final SearchWordScheduler searchWordScheduler;

    public void settingInitData() {
        // init Data-Start
        recommendAlgorithm.updateMarketRecommend();
        searchWordScheduler.updateSearchWordScore();
        searchWordScheduler.calculateRankingChangeValue();
        // init Data-End
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Random random = new Random();
        accountRepository.deleteAll();
        marketRepository.deleteAll();

        Account account = Account.builder()
                .email("email@naver.com")
                .age("20")
                .phone("01012341234")
                .lastedLoginDate(LocalDate.now().minusDays(5))
                .loginCount(1)
                .role(UserRole.STUDENT)
                .oAuthType(OAuthType.KAKAO)
                .build();

        accountRepository.save(account);

        Account account2 = Account.builder()
                .email("email2@naver.com")
                .age("24")
                .phone("01046544654")
                .lastedLoginDate(LocalDate.now().minusDays(5))
                .loginCount(1)
                .role(UserRole.STUDENT)
                .oAuthType(OAuthType.NAVER)
                .build();

        accountRepository.save(account2);

        Market market = Market.builder()
                .marketName("marketName")
                .marketDescription("description")
                .detailAddress("address")
                .mainCategory(MainCategory.RESTAURANT)
                .locationY(123.451)
                .locationX(431.176)
                .marketImage("https://ibb.co/0DSt7KN")
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
        Review review1 = Review.builder().reviewer(account).title("title1").content("content1").score(1).build();
        for(int i = 0; i < 5; i++) {
            int randomData = random.nextInt(10) * 1000;

            Coupon coupon = Coupon.builder()
                    .couponName("couponName" + i)
                    .couponDescription("couponDescription")
                    .market(market)
                    .saleRate(randomData)
                    .build();

            market.addCoupon(coupon);
        }
        marketRepository.save(market);
        market.addReview(review1);

        for (int i = 0; i < 30; i++) {
            double randomX = random.nextDouble(99999);
            double randomY = random.nextDouble(99999);
            int reviewCount = random.nextInt(30);

            Market market_20 = Market.builder()
                    .marketName("MarketName " + (i + 1))
                    .marketDescription("Market Description" + (i + 1))
                    .detailAddress("세부주소 101-1003 45로")
                    .mainCategory(MainCategory.RESTAURANT)
                    .locationY(randomX)
                    .locationX(randomY)
                    .marketImage("https://ibb.co/0DSt7KN")
                    .partner(account)
                    .closeTime(LocalTime.now())
                    .openTime(LocalTime.now())
                    .marketPhone("01012341234")
                    .build();

            for (int j = 0; j < reviewCount; j++) {
                int randomScore = random.nextInt(5) + 1;

                market_20.addReview(Review
                        .builder()
                        .reviewer(account)
                        .title("title " + randomScore)
                        .content("content " + randomScore)
                        .score(randomScore)
                        .build());
            }

            marketRepository.save(market_20);

            settingInitData();
        }
    }
}
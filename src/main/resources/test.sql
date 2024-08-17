DELIMITER $$

-- market
CREATE PROCEDURE createMarketTestData(var1 int)
BEGIN
    DECLARE i INT DEFAULT var1 + 1;
    WHILE (i <= 100000) DO
    INSERT INTO `market` VALUES (i,'16:18:22',NULL,40,'2024-08-16','충청북도 충주시 단월동',_binary '\0','선착순 100명 10% 할인 쿠폰 증정 이벤트 진행 중! 대충 메세지 임을 알려줍니다. 이건 말도 안된다고 아잇 정말 맛업ㅈㅅ어 죽겠네 ',_binary '\0',37.78493395412131,128.85370267630134,'RESTAURANT','족발.보쌈','https://kku-saver.s3.ap-northeast-2.amazonaws.com/21a68141-fdcb-4d57-a900-fd1a5a0f2048','장충동 왕족발보쌈','010-1234-1234',0,'16:18:22',1);
        SET i = i + 1;
END WHILE;
END$$

-- basket, basket_menu, basket_menu_option
CREATE PROCEDURE createBasketTestData(basket_index int, account_count int, basket_menu_index int, basket_option_index int)
BEGIN
    DECLARE i INT DEFAULT 1; -- ⓑ i변수 선언, defalt값으로 1설정
    DECLARE y INT DEFAULT 1;
    DECLARE update_index INT DEFAULT 1;
    set @menu_count = (select count(*) from menu) - 1;
    set @market_count = (select count(*) from market) - 1;
    set @menu_option_count = (select count(*) from menu_option) - 1;

    WHILE (i <= 40000) DO
		INSERT INTO `basket`(update_time, account_account_id, market_market_id) VALUES ('2024-08-16 16:18:46.922513', ( i % account_count ) + 1, i % @market_count );
        set y = 0;

        while (y <= 10) do
			INSERT INTO `basket_menu`(amount, basket_basket_id, menu_menu_id) VALUES (FLOOR(RAND()*10), i , i % @menu_count);
INSERT INTO `basket_menu_option`(basket_menu_basket_menu_id, menu_option_menu_option_id) VALUES (i + basket_menu_index , @menu_option_count);

set y = y + 1;
            set update_index = update_index + 1;
end while;

			SET i = i + 1;
END WHILE;
END$$

# Coupon, download_coupon
CREATE PROCEDURE createCouponTestData()
BEGIN
    DECLARE i INT DEFAULT 1;
    set @download_coupon_index = (select max(download_coupon_id) from download_coupon);
    set @coupon_index = (select max(coupon_id) from coupon);
    set @market_index = (select max(market.market_id) from market);

    WHILE (i <= 100000) DO
		INSERT INTO `coupon` VALUES (i + @coupon_index ,0,10000,"description","name",0,0,2000, (i % @market_index));
INSERT INTO `download_coupon`(is_usage, use_date, account_account_id, coupon_coupon_id, market_market_id) VALUES (0,'2000-03-02',1,i + @coupon_index, (i % @market_index));
INSERT INTO `download_coupon`(is_usage, use_date, account_account_id, coupon_coupon_id, market_market_id) VALUES (0,'2000-03-02',3,i + @coupon_index, (i % @market_index));
INSERT INTO `download_coupon`(is_usage, use_date, account_account_id, coupon_coupon_id, market_market_id) VALUES (0,'2000-03-02',4,i + @coupon_index, (i % @market_index));
INSERT INTO `download_coupon`(is_usage, use_date, account_account_id, coupon_coupon_id, market_market_id) VALUES (0,'2000-03-02',5,i + @coupon_index, (i % @market_index));
SET i = i + 1;
END WHILE;
END$$

DELIMITER ;

drop PROCEDURE createCouponTestData;

# market 테스트 데이터 생성
CALL createMarketTestData((select max(market.market_id) from market));

# basket 테스트 데이터 생성
call createBasketTestData((select max(basket_id) from basket) ,
	(select count(*) from account),
    (select max(basket_menu_id) from basket_menu),
    (select max(basket_menu_option_id) from basket_menu_option));

# Coupon 테스트 데이터 생성
call createCouponTestData();

select count(*) from download_coupon;

# Review , Partner_request , Order , Coupon , Basket


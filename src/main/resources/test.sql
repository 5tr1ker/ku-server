DELIMITER $$


-- market
CREATE PROCEDURE createAccountTestData()
BEGIN
    DECLARE i INT DEFAULT 1;
    WHILE (i <= 120000) DO
    INSERT INTO `saver`.`account` (`age`, `auto_login`, `auto_update`, `email`, `is_delete`, `join_date`, `lasted_login_date`, `login_count`, `o_auth_type`, `phone`, `profile_image`, `push_alert`, `role`, `use_point`) VALUES ('30', 0, 1, concat('email' , i , '@naver.com'), 0, '2000-03-02', '2000-03-02', '1', 'NAVER', '01012341234', 'profile_image', 1, 'STUDENT', '0');
        SET i = i + 1;
END WHILE;
END$$

CREATE PROCEDURE createMarketTestData(var1 int)
BEGIN
    DECLARE i INT DEFAULT var1 + 1;
    WHILE (i <= 100000) DO
    INSERT INTO `market` VALUES (i,'16:18:22',NULL,40,'2024-08-16','충청북도 충주시 단월동',_binary '\0','선착순 100명 10% 할인 쿠폰 증정 이벤트 진행 중! 대충 메세지 임을 알려줍니다. 이건 말도 안된다고 아잇 정말 맛업ㅈㅅ어 죽겠네 ',_binary '\0',37.78493395412131,128.85370267630134,'RESTAURANT','족발.보쌈','https://kku-saver.s3.ap-northeast-2.amazonaws.com/21a68141-fdcb-4d57-a900-fd1a5a0f2048','장충동 왕족발보쌈','010-1234-1234',0,'16:18:22',1);
        SET i = i + 1;
END WHILE;
END$$

-- basket, basket_menu, basket_menu_option
CREATE PROCEDURE createBasketTestData()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE y INT DEFAULT 1;
    DECLARE update_index INT DEFAULT 1;
    set @menu_count = (select count(*) from menu) - 1;
    set @market_count = (select count(*) from market) - 1;
    set @menu_option_count = (select count(*) from menu_option) - 1;
    set @basket_index = (select max(basket_id) from basket);

    WHILE (i <= 100000) DO
		INSERT INTO `basket`(update_time, account_account_id, market_market_id) VALUES ('2024-08-16 16:18:46.922513', ( i % 5 ) + 1, i % 50 + 1 );
        set y = 0;

        while (y <= 10) do
			INSERT INTO `basket_menu`(amount, basket_basket_id, menu_menu_id) VALUES (8 , @basket_index , i % @menu_count + 1);
INSERT INTO `basket_menu_option`(basket_menu_basket_menu_id, menu_option_menu_option_id) VALUES (i , (i + y) % @menu_option_count + 1);

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

# Partner_request
CREATE PROCEDURE createPartnerRequestTestData()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE y INT DEFAULT 1;

    WHILE (i <= 60000) DO
		INSERT INTO `partner_request` (`description`, `detail_address`, `locationx`, `locationy`, `market_address`, `phone_number`, `request_market_name`, `title`, `write_time`, `request_user_account_id`) VALUES ('description', 'addrtess', '10.145351', '36.4131235', 'market_address', 'phone_number', 'market_name', 'title', '2000-03-02 00:00:00', 1);
        set y = 0;
        set @partner_request_index = (select max(partner_request_id) from partner_request) - 1;

        while (y <= 20) DO
			INSERT INTO `partner_comment` (`message`, `write_time`, `partner_request_partner_request_id`, `writer_account_id`) VALUES ('message', '2000-03-02 00:00:00', (i + y) % @partner_request_index + 1 , 2);
INSERT INTO `partner_recommender` (`account_account_id`, `partner_request_partner_request_id`) VALUES (1, (i + y) % @partner_request_index + 1);

set y = y + 1;
END while;

        SET i = i + 1;
END WHILE;
END$$

# review, review_image , review_recommender
CREATE PROCEDURE createReviewTestData()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE y INT DEFAULT 1;

    WHILE (i <= 100000) DO
		INSERT INTO `review` (`content`, `is_delete`, `score`, `write_time`, `market_market_id`, `reviewer_account_id`) VALUES ('content', 0,'5', '2000-03-02 00:00:00', i % 17 + 1, i % 5 + 1);
        set y = 0;

        while (y <= 5) DO
			INSERT INTO `review_image` (`image_url`, `is_delete`, `review_review_id`) VALUES ('image_url', 0, i);
INSERT INTO `review_recommender` (`account_account_id`, `review_review_id`) VALUES (i % 5 + 1, i);

set y = y + 1;
END while;

        SET i = i + 1;
END WHILE;
END$$

# order, order_menu, order_detail, order_option
CREATE PROCEDURE createOrderTestData()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE y INT DEFAULT 1;
    set @order_detail_index = (select max(order_detail_id) from order_detail);
    set @order_index = (select max(order_id) from order_tb);

    WHILE (i <= 100000) DO
		INSERT INTO `order_detail` (`discount_amount`, `final_price`, `order_date_time`, `order_number`, `order_price`, `payment_type`) VALUES ('0', '90000', '2000-03-02 00:00:00', 'order_number', '90000', 'KAKAO_PAY');
INSERT INTO `order_tb` (`account_account_id`, `market_market_id`, `order_detail_order_detail_id`) VALUES (i % 5 + 1, i, @order_detail_index + 1);
set y = 0;

        set @order_menu_index = (select max(order_menu_id) from order_menu);
        while (y <= 5) DO
			INSERT INTO `order_menu` (`amount`, `menu_name`, `price`, `order_order_id`) VALUES ('1', 'menu_name', '990000', @order_index + 1);
INSERT INTO `order_option` (`option_description`, `option_price`, `order_menu_order_menu_id`) VALUES ('option_description', '5000', @order_menu_index + y);

set y = y + 1;
END while;

        SET i = i + 1;
END WHILE;
END$$

# Search_word, auto_complete 테스트 데이터
CREATE PROCEDURE createWordTestData()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE y INT DEFAULT 1;
    DECLARE z INT DEFAULT 1;
    set @first_word_index = (select count(*) from word_table_1);
    set @second_word_index = (select count(*) from word_table_2);
    set @third_word_index = (select count(*) from word_table_3);

    WHILE (i <= @first_word_index) DO
		set y = 1;
		WHILE (y <= @second_word_index) DO
			set z = 1;
			WHILE (z <= @third_word_index) DO
				set @word_1 = (select word from word_table_1 where id = i);
                set @word_2 = (select word from word_table_2 where id = y);
                set @word_3 = (select word from word_table_3 where id = z);

INSERT INTO `search_word` (`day_search`, `previous_ranking`, `recently_search`, `search_word`, `total_search`, `week_search`) VALUES ('1', '999', '1', concat(@word_1, @word_2, @word_3), '1', '1');
INSERT INTO `auto_complete` (`frequency`, `word`) VALUES ('1', concat(@word_1, @word_2, @word_3));
set z = z + 1;
END WHILE;
            set y = y + 1;
END WHILE;
        SET i = i + 1;
END WHILE;
END$$

CREATE PROCEDURE createWord()
BEGIN
CREATE TABLE if not exists word_table_1 (
                                            id INT AUTO_INCREMENT PRIMARY KEY,
                                            word VARCHAR(255)
    );
INSERT INTO word_table_1 (word) VALUES
                                    ('책상'), ('건전지'), ('마스크'), ('온도계'), ('습도계'), ('이어폰'),
                                    ('컴퓨터'), ('모니터'), ('키보드'), ('마우스'), ('부채'), ('가위'),
                                    ('펜'), ('볼펜'), ('휴지'), ('책'), ('리모컨'), ('손톱깎이'),
                                    ('집게'), ('담배'), ('전자담배'), ('연초'), ('액상'),
                                    ('리틀보이'), ('폭탄'), ('A1'), ('A2'), ('A3'), ('선'),
                                    ('거치대');


CREATE TABLE if not exists word_table_2 (
                                            id INT AUTO_INCREMENT PRIMARY KEY,
                                            word VARCHAR(255)
    );
INSERT INTO word_table_2 (word) VALUES
                                    ('바람'), ('호수'), ('도시'), ('별빛'), ('우산'), ('나비'), ('손목시계'), ('모자'), ('도서관'), ('초콜릿'),
                                    ('고양이'), ('나무'), ('바다'), ('책'), ('꽃'), ('구름'), ('산'), ('강아지'), ('별'), ('하늘'),
                                    ('강'), ('나무'), ('꽃'), ('비'), ('시계'), ('안경'), ('바다'), ('하늘'), ('구름'), ('별'),
                                    ('고양이'), ('강아지'), ('책'), ('커피'), ('의자'), ('책상'), ('창문'), ('문'), ('자동차'), ('버스'),
                                    ('기차'), ('자전거'), ('산'), ('바위'), ('물'), ('모래'), ('길'), ('나침반'), ('지도'), ('휴대폰');

CREATE TABLE if not exists word_table_3 (
                                            id INT AUTO_INCREMENT PRIMARY KEY,
                                            word VARCHAR(255)
    );
INSERT INTO word_table_3 (word) VALUES
                                    ('강'), ('나무'), ('꽃'), ('비'), ('시계'), ('안경'), ('바다'), ('하늘'), ('구름'), ('별'),
                                    ('고양이'), ('강아지'), ('책'), ('커피'), ('의자'), ('책상'), ('창문'), ('문'), ('자동차'), ('버스'),
                                    ('기차'), ('자전거'), ('산'), ('바위'), ('물'), ('모래'), ('길'), ('나침반'), ('지도'), ('휴대폰'),
                                    ('노트북'), ('마우스'), ('키보드'), ('그림'), ('사진'), ('영화'), ('음악'), ('피아노'), ('기타'), ('바이올린'),
                                    ('풍경'), ('꽃병'), ('거울'), ('시계탑'), ('공원'), ('정원'), ('집'), ('학교'), ('병원'), ('식당'),
                                    ('가게'), ('은행'), ('우체국');
END$$

DELIMITER ;

drop PROCEDURE createWordTestData;

# account 테스트 데이터 생성
call createAccountTestData();

# market 테스트 데이터 생성
CALL createMarketTestData((select max(market.market_id) from market));

# basket 테스트 데이터 생성
call createBasketTestData();

# Coupon 테스트 데이터 생성
call createCouponTestData();

# Partner_request 테스트 데이터 생성
call createPartnerRequestTestData();

# Review 테스트 데이터 생성
call createReviewTestData();

# Order 테스트 데이터 생성
call createOrderTestData();

# Search_word, auto_complete 테스트 데이터
call createWordTestData();

start transaction;
commit;
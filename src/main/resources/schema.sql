CREATE TABLE IF NOT EXISTS account (
                         account_id BIGINT NOT NULL AUTO_INCREMENT,
                         age VARCHAR(255),
                         auto_login BIT NOT NULL,
                         auto_update BIT NOT NULL,
                         email VARCHAR(255) NOT NULL,
                         is_delete BIT NOT NULL,
                         join_date DATE NOT NULL,
                         lasted_login_date DATE NOT NULL,
                         login_count BIGINT NOT NULL,
                         name VARCHAR(255),
                         o_auth_type VARCHAR(255) NOT NULL,
                         phone VARCHAR(255),
                         profile_image VARCHAR(255),
                         push_alert BIT NOT NULL,
                         role VARCHAR(255) NOT NULL,
                         school_email VARCHAR(255),
                         use_point BIGINT NOT NULL,
                         default_delivery_address_delivery_address_id BIGINT,
                         PRIMARY KEY (account_id)
);

CREATE TABLE IF NOT EXISTS market (
                        market_id BIGINT NOT NULL AUTO_INCREMENT,
                        close_time TIME,
                        closed_days VARCHAR(255),
                        cooking_time INTEGER NOT NULL,
                        create_date DATE NOT NULL,
                        detail_address VARCHAR(255) NOT NULL,
                        event_coupon_market BIT NOT NULL,
                        event_message VARCHAR(255),
                        is_delete BIT NOT NULL,
                        locationx FLOAT(53) NOT NULL,
                        locationy FLOAT(53) NOT NULL,
                        main_category VARCHAR(255) NOT NULL,
                        market_description VARCHAR(255) NOT NULL,
                        market_image VARCHAR(255) NOT NULL,
                        market_name VARCHAR(255) NOT NULL,
                        market_phone VARCHAR(255),
                        minimum_order_price INTEGER NOT NULL,
                        open_time TIME,
                        partner_account_id BIGINT NOT NULL,
                        PRIMARY KEY (market_id),
                        FOREIGN KEY (partner_account_id) references account (account_id)
);

CREATE TABLE IF NOT EXISTS menu_option_container (
                                       menu_option_container_id BIGINT NOT NULL AUTO_INCREMENT,
                                       classification VARCHAR(255) NOT NULL,
                                       is_multiple_selection BIT NOT NULL,
                                       priority BIGINT NOT NULL,
                                       menu_menu_id BIGINT NOT NULL,
                                       PRIMARY KEY (menu_option_container_id)
);

CREATE TABLE IF NOT EXISTS menu_option (
                             menu_option_id BIGINT NOT NULL AUTO_INCREMENT,
                             description VARCHAR(255) NOT NULL,
                             is_default_option BIT NOT NULL,
                             is_adult_menu BIT NOT NULL,
                             option_price INTEGER NOT NULL,
                             menu_option_container_menu_option_container_id BIGINT NOT NULL,
                             PRIMARY KEY (menu_option_id),
                             foreign key (menu_option_container_menu_option_container_id) references menu_option_container (menu_option_container_id)
);

CREATE TABLE IF NOT EXISTS menu_container (
                                menu_container_id BIGINT NOT NULL AUTO_INCREMENT,
                                classification VARCHAR(255) NOT NULL,
                                priority BIGINT NOT NULL,
                                market_market_id BIGINT NOT NULL,
                                PRIMARY KEY (menu_container_id),
                                foreign key (market_market_id) references market (market_id)
);

CREATE TABLE IF NOT EXISTS menu (
                      menu_id BIGINT NOT NULL AUTO_INCREMENT,
                      description VARCHAR(255) NOT NULL,
                      image_url VARCHAR(255),
                      menu_name VARCHAR(255) NOT NULL,
                      price INTEGER NOT NULL,
                      is_adult_menu BIT NOT NULL,
                      menu_container_menu_container_id BIGINT NOT NULL,
                      PRIMARY KEY (menu_id),
                      foreign key (menu_container_menu_container_id) references menu_container (menu_container_id),
                      foreign key (menu_container_menu_container_id) references menu_container (menu_container_id)
);

CREATE TABLE IF NOT EXISTS announce (
                          announce_id BIGINT NOT NULL AUTO_INCREMENT,
                          announce_type SMALLINT NOT NULL,
                          description LONGTEXT NOT NULL,
                          is_delete BIT NOT NULL,
                          is_important BIT NOT NULL,
                          title VARCHAR(255) NOT NULL,
                          write_time DATETIME(6) NOT NULL,
                          PRIMARY KEY (announce_id)
);

CREATE TABLE IF NOT EXISTS attraction (
                            attraction_id BIGINT NOT NULL AUTO_INCREMENT,
                            attraction_description VARCHAR(255),
                            attraction_name VARCHAR(255),
                            close_time TIME,
                            event_message VARCHAR(255),
                            image_url VARCHAR(255),
                            locationx FLOAT(53) NOT NULL,
                            locationy FLOAT(53) NOT NULL,
                            open_time TIME,
                            PRIMARY KEY (attraction_id)
);

CREATE TABLE IF NOT EXISTS auto_complete (
                               auto_complete_id BIGINT NOT NULL AUTO_INCREMENT,
                               frequency INTEGER NOT NULL,
                               word VARCHAR(255),
                               PRIMARY KEY (auto_complete_id)
);

CREATE TABLE IF NOT EXISTS basket (
                        basket_id BIGINT NOT NULL AUTO_INCREMENT,
                        update_time DATETIME(6),
                        account_account_id BIGINT,
                        market_market_id BIGINT,
                        PRIMARY KEY (basket_id),
                        foreign key (account_account_id) references account (account_id),
                        foreign key (market_market_id) references market (market_id)
);

CREATE TABLE IF NOT EXISTS basket_menu (
                             basket_menu_id BIGINT NOT NULL AUTO_INCREMENT,
                             amount BIGINT NOT NULL,
                             basket_basket_id BIGINT,
                             menu_menu_id BIGINT,
                             PRIMARY KEY (basket_menu_id),
                             foreign key (basket_basket_id) references basket (basket_id),
                             foreign key (menu_menu_id) references menu (menu_id)
);

CREATE TABLE IF NOT EXISTS basket_menu_option (
                                    basket_menu_option_id BIGINT NOT NULL AUTO_INCREMENT,
                                    basket_menu_basket_menu_id BIGINT,
                                    menu_option_menu_option_id BIGINT,
                                    PRIMARY KEY (basket_menu_option_id),
                                    foreign key (basket_menu_basket_menu_id) references basket_menu (basket_menu_id),
                                    foreign key (menu_option_menu_option_id) references menu_option (menu_option_id)
);

CREATE TABLE IF NOT EXISTS chat_room (
                           chat_room_id BIGINT NOT NULL AUTO_INCREMENT,
                           is_read BIT NOT NULL,
                           account_account_id BIGINT NOT NULL,
                           PRIMARY KEY (chat_room_id),
                           foreign key (account_account_id) references account (account_id)
);

CREATE TABLE IF NOT EXISTS chat (
                      chat_id BIGINT NOT NULL AUTO_INCREMENT,
                      is_admin BIT NOT NULL,
                      message VARCHAR(255) NOT NULL,
                      send_time DATETIME(6) NOT NULL,
                      chat_room_chat_room_id BIGINT NOT NULL,
                      PRIMARY KEY (chat_id),
                      foreign key (chat_room_chat_room_id) references chat_room (chat_room_id)
);

CREATE TABLE IF NOT EXISTS coupon (
                        coupon_id BIGINT NOT NULL AUTO_INCREMENT,
                        condition_to_use SMALLINT NOT NULL,
                        condition_to_use_amount INTEGER NOT NULL,
                        coupon_description VARCHAR(255) NOT NULL,
                        coupon_name VARCHAR(255) NOT NULL,
                        is_delete BIT NOT NULL,
                        priority BIGINT NOT NULL,
                        sale_rate INTEGER NOT NULL,
                        market_market_id BIGINT NOT NULL,
                        PRIMARY KEY (coupon_id),
                        foreign key (market_market_id) references market (market_id)
);

CREATE TABLE IF NOT EXISTS delivery_address (
                                  delivery_address_id BIGINT NOT NULL AUTO_INCREMENT,
                                  address VARCHAR(255) NOT NULL,
                                  detail_address VARCHAR(255) NOT NULL,
                                  name VARCHAR(255) NOT NULL,
                                  phone VARCHAR(255) NOT NULL,
                                  zip_code VARCHAR(255) NOT NULL,
                                  account_account_id BIGINT,
                                  PRIMARY KEY (delivery_address_id),
                                  FOREIGN KEY (account_account_id) references account (account_id)
);

CREATE TABLE IF NOT EXISTS download_coupon (
                                 download_coupon_id BIGINT NOT NULL AUTO_INCREMENT,
                                 is_usage BIT NOT NULL,
                                 use_date DATETIME(6),
                                 account_account_id BIGINT NOT NULL,
                                 coupon_coupon_id BIGINT NOT NULL,
                                 market_market_id BIGINT NOT NULL,
                                 PRIMARY KEY (download_coupon_id),
                                 foreign key (account_account_id) references account (account_id),
                                 foreign key (coupon_coupon_id) references coupon (coupon_id),
                                 foreign key (market_market_id) references market (market_id)
);

CREATE TABLE IF NOT EXISTS event (
                       event_id BIGINT NOT NULL AUTO_INCREMENT,
                       description VARCHAR(255) NOT NULL,
                       event_end_date DATE NOT NULL,
                       event_start_date DATE NOT NULL,
                       image_url VARCHAR(255) NOT NULL,
                       is_delete BIT NOT NULL,
                       title VARCHAR(255) NOT NULL,
                       PRIMARY KEY (event_id)
);

CREATE TABLE IF NOT EXISTS favorite (
                          favorite_id BIGINT NOT NULL AUTO_INCREMENT,
                          account_account_id BIGINT NOT NULL,
                          market_market_id BIGINT NOT NULL,
                          PRIMARY KEY (favorite_id),
                          foreign key (account_account_id) references account (account_id),
                          foreign key (market_market_id) references market (market_id)
);

CREATE TABLE IF NOT EXISTS history (
                         history_id BIGINT NOT NULL AUTO_INCREMENT,
                         content VARCHAR(255) NOT NULL,
                         local_date_time DATETIME(6) NOT NULL,
                         account_account_id BIGINT,
                         PRIMARY KEY (history_id),
                         foreign key (account_account_id) references account (account_id)
);

CREATE TABLE IF NOT EXISTS inspection_time (
                                 inspection_time_id BIGINT NOT NULL AUTO_INCREMENT,
                                 inspection_end DATETIME(6),
                                 inspection_start DATETIME(6),
                                 PRIMARY KEY (inspection_time_id)
);

CREATE TABLE IF NOT EXISTS mail (
                      mail_cert_id BIGINT NOT NULL AUTO_INCREMENT,
                      id VARCHAR(255) NOT NULL,
                      verification_code VARCHAR(255) NOT NULL,
                      PRIMARY KEY (mail_cert_id)
);

CREATE TABLE IF NOT EXISTS mission (
                         mission_id BIGINT NOT NULL AUTO_INCREMENT,
                         increase_exp BIGINT NOT NULL,
                         increase_weight BIGINT NOT NULL,
                         init_exp BIGINT NOT NULL,
                         init_weight BIGINT NOT NULL,
                         mission_type VARCHAR(255) NOT NULL,
                         PRIMARY KEY (mission_id)
);

CREATE TABLE IF NOT EXISTS review (
                        review_id BIGINT NOT NULL AUTO_INCREMENT,
                        content VARCHAR(255) NOT NULL,
                        is_delete BIT NOT NULL,
                        score INTEGER NOT NULL,
                        write_time DATETIME(6) NOT NULL,
                        market_market_id BIGINT NOT NULL,
                        reviewer_account_id BIGINT NOT NULL,
                        PRIMARY KEY (review_id),
                        foreign key (market_market_id) references market (market_id),
                        foreign key (reviewer_account_id) references account (account_id)
);

CREATE TABLE IF NOT EXISTS review_image (
                              review_image_id BIGINT NOT NULL AUTO_INCREMENT,
                              image_url VARCHAR(255),
                              is_delete BIT NOT NULL,
                              review_review_id BIGINT NOT NULL,
                              PRIMARY KEY (review_image_id),
                              foreign key (review_review_id) references review (review_id)
);

CREATE TABLE IF NOT EXISTS review_recommender (
                                    review_recommender_id BIGINT NOT NULL AUTO_INCREMENT,
                                    account_account_id BIGINT,
                                    review_review_id BIGINT NOT NULL,
                                    PRIMARY KEY (review_recommender_id),
                                    foreign key (account_account_id) references account (account_id),
                                    foreign key (review_review_id) references review (review_id)
);

CREATE TABLE IF NOT EXISTS order_detail (
                              order_detail_id BIGINT NOT NULL AUTO_INCREMENT,
                              discount_amount INTEGER NOT NULL,
                              final_price BIGINT NOT NULL,
                              order_date_time DATETIME(6),
                              order_number VARCHAR(255),
                              order_price BIGINT NOT NULL,
                              payment_type VARCHAR(255),
                              PRIMARY KEY (order_detail_id)
);

CREATE TABLE IF NOT EXISTS order_tb (
                          order_id BIGINT NOT NULL AUTO_INCREMENT,
                          account_account_id BIGINT,
                          market_market_id BIGINT,
                          order_detail_order_detail_id BIGINT,
                          review_review_id BIGINT,
                          PRIMARY KEY (order_id),
                          foreign key (account_account_id) references account (account_id),
                          foreign key (market_market_id) references market (market_id),
                          foreign key (order_detail_order_detail_id) references order_detail (order_detail_id),
                          foreign key (review_review_id) references review (review_id)
);

CREATE TABLE IF NOT EXISTS order_menu (
                            order_menu_id BIGINT NOT NULL AUTO_INCREMENT,
                            amount BIGINT NOT NULL,
                            menu_name VARCHAR(255),
                            price BIGINT NOT NULL,
                            order_order_id BIGINT NOT NULL,
                            PRIMARY KEY (order_menu_id),
                            foreign key (order_order_id) references order_tb (order_id)
);

CREATE TABLE IF NOT EXISTS order_option (
                              order_option_id BIGINT NOT NULL AUTO_INCREMENT,
                              option_description VARCHAR(255),
                              option_price BIGINT NOT NULL,
                              order_menu_order_menu_id BIGINT NOT NULL,
                              PRIMARY KEY (order_option_id),
                              foreign key (order_menu_order_menu_id) references order_menu (order_menu_id)
);

CREATE TABLE IF NOT EXISTS partner_request (
                                 partner_request_id BIGINT NOT NULL AUTO_INCREMENT,
                                 description VARCHAR(255) NOT NULL,
                                 detail_address VARCHAR(255) NOT NULL,
                                 locationx FLOAT(53) NOT NULL,
                                 locationy FLOAT(53) NOT NULL,
                                 market_address VARCHAR(255) NOT NULL,
                                 phone_number VARCHAR(255) NOT NULL,
                                 request_market_name VARCHAR(255) NOT NULL,
                                 title VARCHAR(255) NOT NULL,
                                 write_time DATETIME(6),
                                 request_user_account_id BIGINT NOT NULL,
                                 PRIMARY KEY (partner_request_id),
                                 foreign key (request_user_account_id) references account (account_id)
);

CREATE TABLE IF NOT EXISTS partner_comment (
                                 partner_comment_id BIGINT NOT NULL AUTO_INCREMENT,
                                 message VARCHAR(255) NOT NULL,
                                 write_time DATETIME(6),
                                 partner_request_partner_request_id BIGINT NOT NULL,
                                 writer_account_id BIGINT NOT NULL,
                                 PRIMARY KEY (partner_comment_id),
                                 foreign key (partner_request_partner_request_id) references partner_request (partner_request_id),
                                 foreign key (writer_account_id) references account (account_id)
);

CREATE TABLE IF NOT EXISTS partner_recommender (
                                     partner_recommender_id BIGINT NOT NULL AUTO_INCREMENT,
                                     account_account_id BIGINT,
                                     partner_request_partner_request_id BIGINT,
                                     PRIMARY KEY (partner_recommender_id),
                                     foreign key (account_account_id) references account (account_id),
                                     foreign key (partner_request_partner_request_id) references partner_request (partner_request_id)
);

CREATE TABLE IF NOT EXISTS promotion (
                           promotion_id BIGINT NOT NULL AUTO_INCREMENT,
                           image_url VARCHAR(255),
                           introduce VARCHAR(255),
                           promotion_location VARCHAR(255),
                           PRIMARY KEY (promotion_id)
);

CREATE TABLE IF NOT EXISTS promotion_tag (
                               promotion_tag_id BIGINT NOT NULL AUTO_INCREMENT,
                               tag_content VARCHAR(255) NOT NULL,
                               PRIMARY KEY (promotion_tag_id)
);

CREATE TABLE IF NOT EXISTS promotion_tag_relation_ship (
                                             promotion_tag_relation_ship_id BIGINT NOT NULL AUTO_INCREMENT,
                                             promotion_promotion_id BIGINT NOT NULL,
                                             promotion_tag_promotion_tag_id BIGINT,
                                             PRIMARY KEY (promotion_tag_relation_ship_id),
                                             foreign key (promotion_promotion_id) references promotion (promotion_id),
                                             foreign key (promotion_tag_promotion_tag_id) references promotion_tag (promotion_tag_id)
);

CREATE TABLE IF NOT EXISTS report (
                        report_id BIGINT NOT NULL AUTO_INCREMENT,
                        content VARCHAR(255) NOT NULL,
                        create_time DATETIME(6) NOT NULL,
                        is_read BIT NOT NULL,
                        report_content VARCHAR(255) NOT NULL,
                        title VARCHAR(255) NOT NULL,
                        reporter_account_id BIGINT NOT NULL,
                        PRIMARY KEY (report_id),
                        foreign key (reporter_account_id) references account (account_id)
);

CREATE TABLE IF NOT EXISTS search_word (
                             search_word_id BIGINT NOT NULL AUTO_INCREMENT,
                             day_search INTEGER NOT NULL,
                             previous_ranking INTEGER NOT NULL,
                             recently_search INTEGER NOT NULL,
                             search_word VARCHAR(255) NOT NULL,
                             total_search INTEGER NOT NULL,
                             week_search INTEGER NOT NULL,
                             PRIMARY KEY (search_word_id)
);

CREATE TABLE IF NOT EXISTS visitant (
                          visitant_id BIGINT NOT NULL AUTO_INCREMENT,
                          user_agent VARCHAR(255) NOT NULL,
                          user_ip VARCHAR(255) NOT NULL,
                          visit_date DATE NOT NULL,
                          PRIMARY KEY (visitant_id)
);

CALL AddForeignKeyIfNotExists();
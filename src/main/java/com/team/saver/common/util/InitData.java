package com.team.saver.common.init;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.team.saver.account.entity.Account;
import com.team.saver.account.entity.UserRole;
import com.team.saver.account.repository.AccountRepository;
import com.team.saver.admin.visitant.domain.Visitant;
import com.team.saver.admin.visitant.repository.VisitantRepository;
import com.team.saver.announce.entity.Announce;
import com.team.saver.announce.entity.AnnounceType;
import com.team.saver.announce.repository.AnnounceRepository;
import com.team.saver.attraction.entity.Attraction;
import com.team.saver.attraction.repository.AttractionRepository;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.event.entity.Event;
import com.team.saver.event.repository.EventRepository;
import com.team.saver.market.basket.dto.BasketCreateRequest;
import com.team.saver.market.basket.service.BasketService;
import com.team.saver.market.coupon.dto.CouponCreateRequest;
import com.team.saver.market.coupon.entity.ConditionToUse;
import com.team.saver.market.coupon.entity.Coupon;
import com.team.saver.market.coupon.service.CouponService;
import com.team.saver.market.favorite.service.FavoriteService;
import com.team.saver.market.order.dto.OrderCreateRequest;
import com.team.saver.market.order.dto.PaymentType;
import com.team.saver.market.order.entity.Order;
import com.team.saver.market.order.repository.OrderRepository;
import com.team.saver.market.order.service.OrderService;
import com.team.saver.market.review.entity.Review;
import com.team.saver.market.review.entity.ReviewRecommender;
import com.team.saver.market.store.entity.*;
import com.team.saver.market.store.repository.MarketRepository;
import com.team.saver.market.store.util.RecommendAlgorithm;
import com.team.saver.oauth.util.OAuthType;
import com.team.saver.partner.comment.entity.PartnerComment;
import com.team.saver.partner.request.entity.PartnerRecommender;
import com.team.saver.partner.request.entity.PartnerRequest;
import com.team.saver.partner.request.repository.PartnerRequestRepository;
import com.team.saver.promotion.entity.Promotion;
import com.team.saver.promotion.entity.PromotionLocation;
import com.team.saver.promotion.entity.PromotionTag;
import com.team.saver.promotion.entity.PromotionTagRelationShip;
import com.team.saver.promotion.repository.PromotionRepository;
import com.team.saver.promotion.repository.PromotionTagRepository;
import com.team.saver.report.entity.Report;
import com.team.saver.report.entity.ReportContent;
import com.team.saver.report.repository.ReportRepository;
import com.team.saver.report.repository.ReportRepositoryImpl;
import com.team.saver.search.autocomplete.service.AutoCompleteService;
import com.team.saver.search.autocomplete.util.Trie;
import com.team.saver.search.elasticsearch.market.document.MarketDocument;
import com.team.saver.search.elasticsearch.market.repository.MarketDocumentRepository;
import com.team.saver.search.popular.entity.SearchWord;
import com.team.saver.search.popular.repository.SearchWordRepository;
import com.team.saver.search.popular.util.SearchWordScheduler;
import com.team.saver.socket.entity.Chat;
import com.team.saver.socket.entity.ChatRoom;
import com.team.saver.socket.repository.ChatRoomRepository;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static com.team.saver.common.dto.ErrorMessage.AWS_SERVER_EXCEPTION;

@AllArgsConstructor
class StoreData {
    String storeName;

    String tag;

    String imageName;

    String eventMessage;

    List<ReviewData> reviewDataList;

}

@AllArgsConstructor
class ReviewData {

    String data;

    List<String> imageNames;

    int score;

    public ReviewData(String data, String imageName, int score) {
        this.data = data;
        this.imageNames = Arrays.asList(imageName);
        this.score = score;
    }

    public ReviewData(String data, int score, String... imageName) {
        this.data = data;
        this.score = score;
        this.imageNames = Arrays.asList(imageName);
    }
}

@AllArgsConstructor
class PromotionData {

    String introduce;

    PromotionLocation location;

    String tags[];

    String imageName;
}

@AllArgsConstructor
class AttractionData {

    String title;

    String description;

    LocalTime openTime;

    LocalTime closeTime;

    String eventMessage;

    String imageUrl;

}

@AllArgsConstructor
class PartnerRequestData {

    String requestMarketName;

    String marketAddress;

    String detailAddress;

    String title;

    String description;

    String phoneNumber;
}

@AllArgsConstructor
class PartnerCommentData {

    String message;

}

@AllArgsConstructor
class AnnounceData {

    String title;

    AnnounceType announceType;

    boolean isImportant;

}

@AllArgsConstructor
class EventData {

    String title;

    String imageName;

    LocalDate eventStartDate;

    LocalDate eventEndDate;

}


@Component
@RequiredArgsConstructor
public class InitData implements CommandLineRunner {

    private final EntityManager entityManager;
    private final AccountRepository accountRepository;
    private final MarketRepository marketRepository;
    private final RecommendAlgorithm recommendAlgorithm;
    private final Trie trie;
    private final AutoCompleteService autoCompleteService;
    private final SearchWordScheduler searchWordScheduler;
    private final PromotionRepository promotionRepository;
    private final PromotionTagRepository promotionTagRepository;
    private final SearchWordRepository searchWordRepository;
    private final AmazonS3Client amazonS3Client;
    private final MarketDocumentRepository marketDocumentRepository;
    private final AttractionRepository attractionRepository;
    private final PartnerRequestRepository partnerRequestRepository;
    private final AnnounceRepository announceRepository;
    private final CouponService couponService;
    private final FavoriteService favoriteService;
    private final BasketService basketService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final EventRepository eventRepository;
    private final ReportRepository reportRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final VisitantRepository visitantRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.region.static}")
    private String region;

    public void settingInitData() {
        // init Data-Start
        recommendAlgorithm.updateMarketRecommend();
        searchWordScheduler.updateSearchWordScore();
        searchWordScheduler.calculateRankingChangeValue();
        trie.initTrie();
        // init Data-End
    }

    private void deleteAllObjectsInBucket() throws Exception {
        List<S3ObjectSummary> s3objects = amazonS3Client.listObjects(bucketName).getObjectSummaries();

        for (S3ObjectSummary object : s3objects) {
            amazonS3Client.deleteObject(bucketName, object.getKey());
        }
    }

    @Transactional
    public void addPopularSearch(String searchWord) {
        SearchWord searchWordEntity = searchWordRepository.findBySearchWord(searchWord)
                .orElseGet(() -> searchWordRepository.save(SearchWord.createEntity(searchWord)));
        searchWordEntity.updateSearch();

    }

    private String uploadFile(File file) {
        String fileName = UUID.randomUUID().toString();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/png");
        metadata.setContentLength(file.length());

        String imageUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, fileName);
        try {
            amazonS3Client.putObject(bucketName, fileName, new FileInputStream(file), metadata);
        } catch (IOException e) {
            throw new CustomRuntimeException(AWS_SERVER_EXCEPTION, e.getMessage());
        }

        return imageUrl;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if(accountRepository.findAll().size() != 0) {

            return;
        }

        deleteAllObjectsInBucket();
        marketDocumentRepository.deleteAll();

        Random random = new Random();
        accountRepository.deleteAll();
        marketRepository.deleteAll();

        File fileImage_account_1 = new File("src/main/resources/images/profile-1.png");
        String image_url_account_1 = uploadFile(fileImage_account_1);
        Account account = Account.builder()
                .email("email@naver.com")
                .age("20")
                .phone("01012341234")
                .lastedLoginDate(LocalDate.now().minusDays(5))
                .loginCount(1)
                .usePoint(120)
                .profileImage(image_url_account_1)
                .role(UserRole.STUDENT)
                .oAuthType(OAuthType.KAKAO)
                .build();

        accountRepository.save(account);

        File fileImage_account_2 = new File("src/main/resources/images/profile-2.png");
        String image_url_account_2 = uploadFile(fileImage_account_2);
        Account account2 = Account.builder()
                .email("email2@naver.com")
                .age("24")
                .phone("01046544654")
                .lastedLoginDate(LocalDate.now().minusDays(5))
                .loginCount(1)
                .profileImage(image_url_account_2)
                .role(UserRole.STUDENT)
                .oAuthType(OAuthType.NAVER)
                .build();

        accountRepository.save(account2);

        File fileImage_account_3 = new File("src/main/resources/images/profile-3.png");
        String image_url_account_3 = uploadFile(fileImage_account_3);
        Account account3 = Account.builder()
                .email("email3@naver.com")
                .age("38")
                .phone("01046544654")
                .lastedLoginDate(LocalDate.now().minusDays(5))
                .loginCount(1)
                .profileImage(image_url_account_3)
                .role(UserRole.STUDENT)
                .oAuthType(OAuthType.NAVER)
                .build();

        accountRepository.save(account3);

        File fileImage_account_4 = new File("src/main/resources/images/profile-4.png");
        String image_url_account_4 = uploadFile(fileImage_account_4);
        Account account4 = Account.builder()
                .email("email4@naver.com")
                .age("25")
                .phone("01032313579")
                .lastedLoginDate(LocalDate.now().minusDays(5))
                .loginCount(1)
                .profileImage(image_url_account_4)
                .role(UserRole.STUDENT)
                .oAuthType(OAuthType.NAVER)
                .build();

        accountRepository.save(account4);

        File fileImage_account_5 = new File("src/main/resources/images/profile-5.png");
        String image_url_account_5 = uploadFile(fileImage_account_5);
        Account account5 = Account.builder()
                .email("email5@naver.com")
                .age("58")
                .phone("0135161421")
                .lastedLoginDate(LocalDate.now().minusDays(5))
                .loginCount(1)
                .profileImage(image_url_account_5)
                .role(UserRole.STUDENT)
                .oAuthType(OAuthType.NAVER)
                .build();

        accountRepository.save(account5);

        Market market_detail_admin = Market.builder()
                .marketName("잎사이")
                .marketDescription("잎사이")
                .detailAddress("충청북도 충주시 단월동")
                .cookingTime(0)
                .mainCategory(MainCategory.RESTAURANT)
                .locationX(0)
                .locationY(0)
                .eventMessage("-")
                .marketImage("empty")
                .partner(account)
                .closeTime(LocalTime.now())
                .openTime(LocalTime.now())
                .marketPhone("000-0000-0000")
                .isDelete(true)
                .eventCouponMarket(true)
                .build();

        Coupon admin_coupon = Coupon.builder()
                .conditionToUseAmount(10000)
                .priority(-1)
                .market(market_detail_admin)
                .couponDescription("첫 구매시 사용 가능")
                .couponName("잎사이 쿠폰")
                .conditionToUse(ConditionToUse.FIRST_PURCHASE)
                .saleRate(2000)
                .build();

        market_detail_admin.addCoupon(admin_coupon);

        marketRepository.save(market_detail_admin);


        CurrentUser currentUser1 = new CurrentUser(account.getEmail());
        CurrentUser currentUser2 = new CurrentUser(account2.getEmail());
        CurrentUser currentUser3 = new CurrentUser(account3.getEmail());

        List<StoreData> storeData = new ArrayList<>();

        ReviewData review1_1 = new ReviewData("맛은 평범한 듯 맛있어요. 깨끗한 기름에 튀긴 것이 느껴지고 꽈리고추, 떡, 닭똥집, 고구마 등도 함께 튀겨나오는데 구성이 혜자네요! 잘 먹었습니다^^", 4, "chicken1.png", "chicken23.png");
        ReviewData review1_2 = new ReviewData("성시경 맛집이라는 곳이네여 줄 짧아서 먹어봤는데.. 흠 제가 치킨류 알못이라 (미쳐있는 정도가 아니라;;) 그런지 그냥 치킨 맛이었구요 그냥 고추튀김이 맛있어요", 3, "chicken2.png", "chicken24.png", "chicken25.png");
        ReviewData review1_3 = new ReviewData("옛날 시장통닭 맛이 좋습니다. 오랜 노포 느낌의 가게 분위기가 치킨과 잘 어울립니다. 혼자 주문쳐내시는 젊은 여자분, 작은 사장님이신가 ? 는 모르겠는데 일당백의 일처리에 멋있었습니다", "chicken3.png", 4);
        ReviewData review1_4 = new ReviewData("효도치킨 한남동 처음 생겼을때부터 다녔는데, 순살치킨이 강정마냥 작아지고 전 같지 않네요..", "chicken16.png", 2);
        ReviewData review1_5 = new ReviewData("맛있는 치킨과 꽈리고추와 멸치의 궁합이 좋아 계속 들어갔던 곳 !!", 5, "chicken17.png", "chicken18.png", "chicken19.png", "chicken20.png");
        ReviewData review1_6 = new ReviewData("불친절하다는 후기를 많이봤는데 유명한 곳이라 한번 가봄. 멸치 엄청 바삭거리고 치킨도 전체적으로 간이 빡-!!!!! 짜서 무조건 콜라는 마셔야할 것 같음. ", 4, "chicken21.png", "chicken22.png");
        storeData.add(new StoreData("짱돌", "치킨.안주.주류", "Rectangle 2208.png", "첫 구매시 3,000원", Arrays.asList(review1_1, review1_2, review1_3, review1_4, review1_5, review1_6)));

        ReviewData review2_1 = new ReviewData("비주얼은 좋은데 맛아…", "pizza1.png", 4);
        ReviewData review2_2 = new ReviewData("피자 - 도우가 정말 쫄깃하다, 가래떡 먹는 느낌 치즈 포함 토핑들이 신선하고 좋은 재료들로 만들었다는 느낌이 확실하게 듦 맛있게 담백해서 더부룩하지 않고 기분좋은 포만감이 든다", "pizza2.png", 5);
        ReviewData review2_3 = new ReviewData("웨이팅없이 바로들어가서 좋았습니다. 피자 파스타 둘다 그럭저럭 맛있습니다. 피자 데우는 초가 금방꺼져서 마지막엔 식어서 아쉽습니다", "pizza3.png", 3);
        ReviewData review2_4 = new ReviewData("예약하고 방문하세요! 나오는데 오래걸립니다아 맛있고 맥주랑 잘어울림다", "chicken5.png", 3);
        ReviewData review2_5 = new ReviewData("너무 맛있게 잘 먹었어요! 비빔국수랑 누룽지통닭이랑 조합이 짱입니다! 비빔국수 안 시켰으면 후회 할 뻔 했어요", "chicken6.png", 5);
        storeData.add(new StoreData("피자나라 치킨공주", "치킨.피자.안주.주류", "Rectangle 2269.png", "리뷰 작성 시 음료 1개 무료 증정 이벤트 진행 중!", Arrays.asList(review2_1, review2_2, review2_3, review2_4, review2_5)));

        ReviewData review3_1 = new ReviewData("오랜만에 장작집이 생각나서 다녀왓습니다. 전기구이와 비교할수없는 장작구이만의 맛은 여전하네요 ~ 맛있게 잘 먹고 갑니다", "chicken4.png", 5);
        StoreData storeData_detail = new StoreData("태권치킨", "치킨.안주.주류", "Rectangle 2270.png", "선착순 100명 10% 할인 쿠폰 증정 이벤트 진행 중!", Arrays.asList(review3_1));
        Market market_detail = Market.builder()
                .marketName(storeData_detail.storeName)
                .marketDescription(storeData_detail.tag)
                .detailAddress("충청북도 충주시 단월동")
                .cookingTime(40)
                .mainCategory(MainCategory.RESTAURANT)
                .locationX(35.121658)
                .locationY(127.2165987)
                .eventMessage(storeData_detail.eventMessage)
                .marketImage(uploadFile(new File("src/main/resources/images/" + storeData_detail.imageName)))
                .partner(account)
                .closeTime(LocalTime.now())
                .openTime(LocalTime.now())
                .marketPhone("010-1234-1234")
                .build();

        String menuImage_1 = uploadFile(new File("src/main/resources/images/Rectangle 2389.png"));
        String menuImage_2 = uploadFile(new File("src/main/resources/images/Rectangle 2390.png"));
        String menuImage_3 = uploadFile(new File("src/main/resources/images/Rectangle 2391.png"));
        String menuImage_4 = uploadFile(new File("src/main/resources/images/Rectangle 2392.png"));
        MenuContainer menuContainer_1 = MenuContainer.builder().classification("추천메뉴").priority(1).build();

        Menu menu1_1 = Menu.builder().menuName("고추바사삭").imageUrl(menuImage_1).description("청양고추의 은은한 알싸함과 최강조합 마블링 고블링 소스. 1초에 1마리씩 팔리네요.").price(19_900).build();
        MenuOptionContainer menuOptionContainer1_1 = MenuOptionContainer.builder().priority(1).isMultipleSelection(false).classification("옵션").build();
        MenuOptionContainer menuOptionContainer1_2 = MenuOptionContainer.builder().priority(2).isMultipleSelection(true).classification("소스").build();
        MenuOptionContainer menuOptionContainer1_3 = MenuOptionContainer.builder().priority(3).isMultipleSelection(true).classification("음료").build();
        menuOptionContainer1_1.addMenuOption(MenuOption.builder().description("뼈(기본)").optionPrice(0).build());
        menuOptionContainer1_1.addMenuOption(MenuOption.builder().description("순살만(+2000원)").optionPrice(2000).build());
        menuOptionContainer1_1.addMenuOption(MenuOption.builder().description("다리만(+3000원)").optionPrice(3000).build());
        menuOptionContainer1_1.addMenuOption(MenuOption.builder().description("날개만(+2000원)").optionPrice(2000).build());

        menuOptionContainer1_2.addMenuOption(MenuOption.builder().description("마블링소스(+1000원)").optionPrice(1000).build());
        menuOptionContainer1_2.addMenuOption(MenuOption.builder().description("고블링소스(+1000원)").optionPrice(1000).build());
        menuOptionContainer1_2.addMenuOption(MenuOption.builder().description("양념치킨소스(+1000원)").optionPrice(1000).build());
        menuOptionContainer1_2.addMenuOption(MenuOption.builder().description("머스타드(+1000원)").optionPrice(1000).build());

        menuOptionContainer1_3.addMenuOption(MenuOption.builder().description("콜라(+1000원)").optionPrice(1000).build());
        menuOptionContainer1_3.addMenuOption(MenuOption.builder().description("사이다(+1000원)").optionPrice(1000).build());
        menuOptionContainer1_3.addMenuOption(MenuOption.builder().description("소주(+1000원)").isAdultMenu(true).optionPrice(1000).build());
        menuOptionContainer1_3.addMenuOption(MenuOption.builder().description("맥주(+1000원)").isAdultMenu(true).optionPrice(1000).build());

        menu1_1.addMenuOptionContainer(menuOptionContainer1_1);
        menu1_1.addMenuOptionContainer(menuOptionContainer1_2);
        menu1_1.addMenuOptionContainer(menuOptionContainer1_3);

        menuContainer_1.addMenu(menu1_1);
        menuContainer_1.addMenu(Menu.builder().menuName("심장바사삭").imageUrl(menuImage_2).description("청양고추의 은은한 알싸함과 최강조합 마블링 고블링 소스. 1초에 1마리씩 팔리네요.").price(19_900).build());
        menuContainer_1.addMenu(Menu.builder().menuName("다리바사삭").imageUrl(menuImage_3).description("청양고추의 은은한 알싸함과 최강조합 마블링 고블링 소스. 1초에 1마리씩 팔리네요.").price(19_900).build());
        menuContainer_1.addMenu(Menu.builder().menuName("날개바사삭").imageUrl(menuImage_4).description("청양고추의 은은한 알싸함과 최강조합 마블링 고블링 소스. 1초에 1마리씩 팔리네요.").price(19_900).build());
        MenuContainer menuContainer_2 = MenuContainer.builder().classification("대표메뉴").priority(2).build();
        menuContainer_2.addMenu(Menu.builder().menuName("고추바사삭").imageUrl(menuImage_1).description("청양고추의 은은한 알싸함과 최강조합 마블링 고블링 소스. 1초에 1마리씩 팔리네요.").price(19_900).build());
        menuContainer_2.addMenu(Menu.builder().menuName("심장바사삭").imageUrl(menuImage_2).description("청양고추의 은은한 알싸함과 최강조합 마블링 고블링 소스. 1초에 1마리씩 팔리네요.").price(19_900).build());
        menuContainer_2.addMenu(Menu.builder().menuName("다리바사삭").imageUrl(menuImage_3).description("청양고추의 은은한 알싸함과 최강조합 마블링 고블링 소스. 1초에 1마리씩 팔리네요.").price(19_900).build());
        menuContainer_2.addMenu(Menu.builder().menuName("날개바사삭").imageUrl(menuImage_4).description("청양고추의 은은한 알싸함과 최강조합 마블링 고블링 소스. 1초에 1마리씩 팔리네요.").price(19_900).build());
        MenuContainer menuContainer_3 = MenuContainer.builder().classification("세트메뉴").priority(3).build();
        menuContainer_3.addMenu(Menu.builder().menuName("고추바사삭").imageUrl(menuImage_1).description("청양고추의 은은한 알싸함과 최강조합 마블링 고블링 소스. 1초에 1마리씩 팔리네요.").price(19_900).build());
        menuContainer_3.addMenu(Menu.builder().menuName("심장바사삭").imageUrl(menuImage_2).description("청양고추의 은은한 알싸함과 최강조합 마블링 고블링 소스. 1초에 1마리씩 팔리네요.").price(19_900).build());
        menuContainer_3.addMenu(Menu.builder().menuName("다리바사삭").imageUrl(menuImage_3).description("청양고추의 은은한 알싸함과 최강조합 마블링 고블링 소스. 1초에 1마리씩 팔리네요.").price(19_900).build());
        menuContainer_3.addMenu(Menu.builder().menuName("날개바사삭").imageUrl(menuImage_4).description("청양고추의 은은한 알싸함과 최강조합 마블링 고블링 소스. 1초에 1마리씩 팔리네요.").price(19_900).build());
        MenuContainer menuContainer_4 = MenuContainer.builder().classification("단품메뉴").priority(4).build();
        menuContainer_4.addMenu(Menu.builder().menuName("고추바사삭").imageUrl(menuImage_1).description("청양고추의 은은한 알싸함과 최강조합 마블링 고블링 소스. 1초에 1마리씩 팔리네요.").price(19_900).build());
        menuContainer_4.addMenu(Menu.builder().menuName("심장바사삭").imageUrl(menuImage_2).description("청양고추의 은은한 알싸함과 최강조합 마블링 고블링 소스. 1초에 1마리씩 팔리네요.").price(19_900).build());
        menuContainer_4.addMenu(Menu.builder().menuName("다리바사삭").imageUrl(menuImage_3).description("청양고추의 은은한 알싸함과 최강조합 마블링 고블링 소스. 1초에 1마리씩 팔리네요.").price(19_900).build());
        menuContainer_4.addMenu(Menu.builder().menuName("날개바사삭").imageUrl(menuImage_4).description("청양고추의 은은한 알싸함과 최강조합 마블링 고블링 소스. 1초에 1마리씩 팔리네요.").price(19_900).build());

        String menuImage_5 = uploadFile(new File("src/main/resources/images/Rectangle 2394.png"));
        String menuImage_6 = uploadFile(new File("src/main/resources/images/Rectangle 2395.png"));
        String menuImage_7 = uploadFile(new File("src/main/resources/images/Rectangle 2396.png"));
        String menuImage_8 = uploadFile(new File("src/main/resources/images/Rectangle 2397.png"));
        MenuContainer menuContainer_5 = MenuContainer.builder().classification("사이드메뉴").priority(5).build();
        menuContainer_5.addMenu(Menu.builder().menuName("치즈볼(5개)").imageUrl(menuImage_5).description("청양고추의 은은한 알싸함과 최강조합 마블링 고블링 소스. 1초에 1마리씩 팔리네요.").price(5000).build());
        menuContainer_5.addMenu(Menu.builder().menuName("치즈스틱(3개)").imageUrl(menuImage_6).description("청양고추의 은은한 알싸함과 최강조합 마블링 고블링 소스. 1초에 1마리씩 팔리네요.").price(3000).build());
        menuContainer_5.addMenu(Menu.builder().menuName("감자튀김").imageUrl(menuImage_7).description("청양고추의 은은한 알싸함과 최강조합 마블링 고블링 소스. 1초에 1마리씩 팔리네요.").price(5000).build());
        menuContainer_5.addMenu(Menu.builder().menuName("어니언링").imageUrl(menuImage_8).description("청양고추의 은은한 알싸함과 최강조합 마블링 고블링 소스. 1초에 1마리씩 팔리네요.").price(8000).build());

        String menuImage_9 = uploadFile(new File("src/main/resources/images/Rectangle 2399.png"));
        String menuImage_10 = uploadFile(new File("src/main/resources/images/Rectangle 2400.png"));
        String menuImage_11 = uploadFile(new File("src/main/resources/images/Rectangle 2401.png"));
        String menuImage_12 = uploadFile(new File("src/main/resources/images/Rectangle 2402.png"));
        String menuImage_13 = uploadFile(new File("src/main/resources/images/coke.jpg"));
        String menuImage_14 = uploadFile(new File("src/main/resources/images/soju.jpg"));
        MenuContainer menuContainer_6 = MenuContainer.builder().classification("소스").priority(6).build();
        menuContainer_6.addMenu(Menu.builder().menuName("마블링소스").imageUrl(menuImage_9).description("청양고추의 은은한 알싸함과 최강조합 마블링 고블링 소스. 1초에 1마리씩 팔리네요.").price(1000).build());
        menuContainer_6.addMenu(Menu.builder().menuName("고블링소스").imageUrl(menuImage_10).description("청양고추의 은은한 알싸함과 최강조합 마블링 고블링 소스. 1초에 1마리씩 팔리네요.").price(1000).build());
        menuContainer_6.addMenu(Menu.builder().menuName("양념치킨소스").imageUrl(menuImage_11).description("청양고추의 은은한 알싸함과 최강조합 마블링 고블링 소스. 1초에 1마리씩 팔리네요.").price(1000).build());
        menuContainer_6.addMenu(Menu.builder().menuName("머쓱타드").imageUrl(menuImage_12).description("청양고추의 은은한 알싸함과 최강조합 마블링 고블링 소스. 1초에 1마리씩 팔리네요.").price(1000).build());
        MenuContainer menuContainer_7 = MenuContainer.builder().classification("음료/주류").priority(7).build();
        menuContainer_7.addMenu(Menu.builder().menuName("콜라").imageUrl(menuImage_13).price(1000).build());
        menuContainer_7.addMenu(Menu.builder().menuName("사이다").description("시원한 사이다입니다. 마시면 기분이 좋아집니다.").price(1000).build());
        menuContainer_7.addMenu(Menu.builder().menuName("소주").imageUrl(menuImage_14).description("시원한 소주입니다. 마시면 기분이 좋아집니다.").price(5000).build());
        menuContainer_7.addMenu(Menu.builder().menuName("맥주").price(5000).build());

        market_detail.addMenuContainer(menuContainer_1);
        market_detail.addMenuContainer(menuContainer_2);
        market_detail.addMenuContainer(menuContainer_3);
        market_detail.addMenuContainer(menuContainer_4);
        market_detail.addMenuContainer(menuContainer_5);
        market_detail.addMenuContainer(menuContainer_6);
        market_detail.addMenuContainer(menuContainer_7);

        Coupon coupon_1 = Coupon.builder().saleRate(1000).couponName("1,000원 할인 쿠폰").couponDescription("10,000원 이상 구매 시 사용가능").conditionToUse(ConditionToUse.MINIMUM_AMOUNT).conditionToUseAmount(10000).build();
        Coupon coupon_2 = Coupon.builder().saleRate(3000).couponName("3,000원 할인 쿠폰").couponDescription("20,000원 이상 구매 시 사용가능").conditionToUse(ConditionToUse.MINIMUM_AMOUNT).conditionToUseAmount(20000).build();
        Coupon coupon_3 = Coupon.builder().saleRate(5000).couponName("5,000원 할인 쿠폰").couponDescription("50,000원 이상 구매 시 사용가능").conditionToUse(ConditionToUse.MINIMUM_AMOUNT).conditionToUseAmount(50000).build();
        Coupon coupon_4 = Coupon.builder().saleRate(5000).couponName("5,000원 할인 쿠폰").couponDescription("50,000원 이상 구매 시 사용가능").conditionToUse(ConditionToUse.MINIMUM_AMOUNT).conditionToUseAmount(50000).build();

        market_detail.addCoupon(coupon_1);
        market_detail.addCoupon(coupon_2);
        market_detail.addCoupon(coupon_3);
        market_detail.addCoupon(coupon_4);
        addPopularSearch(market_detail.getMarketName());
        autoCompleteService.addSearchWord(market_detail.getMarketName());
        marketRepository.save(market_detail);
        marketDocumentRepository.save(MarketDocument.createEntity(market_detail));

        ReviewData review4_1 = new ReviewData("대학생때부터 진짜 자주 가던 곳이고 항상 스시는 스시도쿠가 맛있다고 말해왔고 인스타도 초창기부터 팔로워해왔는데 오랜만에 포장하러가니깐 진짜 싸가지없음. 포장세트메뉴 밑에 단품스시도 있어서 보는데 갑자기 휙하고 이건 볼 필요없다고 뺏어감.", "sushi1.png", 1);
        ReviewData review4_2 = new ReviewData("가성비는 있지만 초밥의 밥이 푸석(?)퍼석(?)해서 좀 실망했다....흠...그 밖에 음식에 실망한 몇가지 포인트가 있지만 긴 말은 생략하겠다", "sushi2.png", 4);
        ReviewData review4_3 = new ReviewData("평일 오픈하자마자 방문했는데 사람 많아서 놀람. 예약하고 왔더니 사이드업그레이드 해주서서 푸짐하게 신선한 스시 배불리 먹고가요~~!!", "sushi3.png", 5);
        storeData.add(new StoreData("스시마당", "초밥.우동.냉모밀.덮밥", "Rectangle 2208-1.png", "첫 구매시 3,000원 할인 이벤트 진행 중! 대충 메세지 임을 알려줍니다.", Arrays.asList(review4_1, review4_2, review4_3)));

        ReviewData review5_1 = new ReviewData("오랜만에 장작집이 생각나서 다녀왓습니다. 전기구이와 비교할수없는 장작구이만의 맛은 여전하네요 ~ 맛있게 잘 먹고 갑니다", "chicken7.png", 5);
        ReviewData review5_2 = new ReviewData("예약하고 방문하세요! 나오는데 오래걸립니다아 맛있고 맥주랑 잘어울림다", "chicken8.png", 4);
        ReviewData review5_3 = new ReviewData("너무 맛있게 잘 먹었어요! 비빔국수랑 누룽지통닭이랑 조합이 짱입니다! 비빔국수 안 시켰으면 후회 할 뻔 했어요", "chicken9.png", 4);
        storeData.add(new StoreData("BHC치킨", "치킨.안주.주류", "Rectangle 2412.png", "리뷰 작성 시 음료 1개 무료 증정 이벤트 진행 중! 대충 메세지 임을 알려줍니다. 이건 말도 안된다고", Arrays.asList(review5_1, review5_2, review5_3)));

        ReviewData review6_1 = new ReviewData("직원들은 조금 별로긴 했는데 살면서 먹은 족발중에 가장 맛있었음.", "bossam1.png", 3);
        ReviewData review6_2 = new ReviewData("딱 상상하는 그 족발 맛과 막국수 맛을 구현하는 집.", "bossam2.png", 4);
        ReviewData review6_3 = new ReviewData("족발 진짜맛있어요~~!!  한동안 주1회씩은 꼭먹었었오요 ㅋㅋㅋ  야들야들하고 기름지고 최고예여", "bossam3.png", 3);
        storeData.add(new StoreData("장충동 왕족발보쌈", "족발.보쌈", "Rectangle 2413.png", "선착순 100명 10% 할인 쿠폰 증정 이벤트 진행 중! 대충 메세지 임을 알려줍니다. 이건 말도 안된다고 아잇 정말 맛업ㅈㅅ어 죽겠네 ", Arrays.asList(review6_1, review6_2, review6_3)));

        ReviewData review7_1 = new ReviewData("데스크 직원분이 친절하시네요. 감자도 금방 한 것 같고", "burger1.png", 4);
        ReviewData review7_2 = new ReviewData("키오스크 문제로 쿠폰이 적용이 안되어서 (분명히 바코드 댔을 때 삑ㅡ소리 났음)햄버거 받을 때 카운터에 문의했더니 결제취소, 재결제 해주셨습니다.", "burger2.png", 1);
        ReviewData review7_3 = new ReviewData("다녀본 버거킹중 제일 나음 주문표상 요청사항 잘 반영함", "burger3.png", 3);
        ReviewData review7_4 = new ReviewData("진짜 ♩♬~ 맛없음 소스도 별로 없음 알바생 싸다구 ♩♬~ 때리고 싶음 거기다 다 못생김", "burger6.png", 1);
        storeData.add(new StoreData("버거킹", "햄버거.음료", "Rectangle 2415.png", "첫 구매시 3,000원 할인 이벤트 진행 중! 맛있게 드세오!", Arrays.asList(review7_1, review7_2, review7_3, review7_4)));

        ReviewData review8_1 = new ReviewData("못 먹는 야채가 있어서 지정한 야채만 넣어달라고 했더니 모.든.야.채 를 다 넣어주셨네요 ^^ 번창하시고 죽어도 갈 일 없을듯", "subway1.png", 1);
        ReviewData review8_2 = new ReviewData("친절하신대.", "subway2.png", 4);
        ReviewData review8_3 = new ReviewData("음..? 점심쯤에 종종 들렀는데 불친절한건 잘 못느꼈어요... 한적한 시간대에만 방문했을지도 모르겠네요", "subway3.png", 2);
        storeData.add(new StoreData("서브웨이", "샌드위치.음료", "Rectangle 2416.png", "첫 구매시 3,000원 할인 이벤트 진행 중!", Arrays.asList(review8_1, review8_2, review8_3)));

        ReviewData review9_1 = new ReviewData("후기가 넘 안좋아서 버거 맛없을까바 좀 걱정했는데 신메뉴 레몬크림새우버거 맛있었습니다~ 감튀도 따끈! 매장도 넓고 쾌적~~ 불광에 24시 롯데리아 있어서 좋습니다", "burger4.png", 5);
        ReviewData review9_2 = new ReviewData("버거보다는 치즈스틱이 맛있음.", "burger5.png", 3);
        storeData.add(new StoreData("롯데리아", "햄버거.음료", "Rectangle 2417.png", "첫 구매시 3,000원 할인 이벤트 진행 중! 맛있게 드세오!", Arrays.asList(review9_1, review9_2)));

        ReviewData review10_1 = new ReviewData("아이스크림 세로로 퍼주시는거 너무 좋았어요! 홍제역 베라는 정말 그지 같았는데, ", "icecream1.png", 1);
        ReviewData review10_2 = new ReviewData("배라는 여기로 다녀야겠어요!! 아이스크림 예쁘게 꽉꽉 눌러담아주시고 친절하시고 빨라서 좋았습니다. 손님이 많아서 바빠보이셨는데도 신경써주셔서 감사해요", "icecream2.png", 4);
        ReviewData review10_3 = new ReviewData("더워서 아이스크림 먹으러 왔는데 너무 추워서 아이스크림을 못 먹겠어요~~~~~남자 직원?분도 친절하신데요 추워서 별3개요~ 다른점은 별5개요", "icecream3.png", 2);
        storeData.add(new StoreData("베스킨라빈스", "아이스크림.음료", "Rectangle 2419.png", "첫 구매시 3,000원 할인 이벤트 진행 중! 맛있게 드세오! 그럼 맛없게 먹겠냐", Arrays.asList(review10_1, review10_2, review10_3)));

        ReviewData review11_1 = new ReviewData("너무 맛있어요 매일매일 가고싶어요 쭈꾸미볶음도 맛있는데 감자전이 진짜 예술이에요 두꺼운데 겉은 바삭하고 속은 촉촉하고 정말 최고예요!!", "octopus1.png", 5);
        ReviewData review11_2 = new ReviewData("점심을 먹을려고 방문을 했습니다. 처음간곳인데 맛이 너무 맛있어요~^^", "octopus2.png", 5);
        ReviewData review11_3 = new ReviewData("오동통한 쭈꾸미볶음! 양념도 자극적이지 않아서 맛있게 먹었어요. 밥을 따로 시키지 않아도 나오는 점이 마음에 들었습니다.", "octopus3.png", 4);
        ReviewData review11_4 = new ReviewData("순두부찌개를 주문했는데 너무 싱거웠습니다 과장 조금 보태서 따뜻한 맹물 떠먹는 것 같았어요 사람들은 굉장히 많았는데 아무래도 메뉴 선택이 잘못 된 듯", "restaurant2.png", 1);
        storeData.add(new StoreData("쭈꾸대장", "쭈꾸미.볶음밥.계란찜.주류", "Rectangle 2420.png", "잎 사이가 정말 좋아요 정말 맛있어요! 사장님이 정말 맛있습니다.", Arrays.asList(review11_1, review11_2, review11_3, review11_4)));

        ReviewData review12_1 = new ReviewData("가격 , 맛, 80년대  분위기 좋습니다. ", "chicken10.png", 5);
        ReviewData review12_2 = new ReviewData("밤 9시이후 원쁠원하면 가성비는 좋은데 치킨 강국 대한민국 국민의 입맛에 미리 튀겨놓는 kfc치킨은 좀 느끼하고 짜고 후추맛만 엄청 강하고 그렇네요.", "chicken11.png", 3);
        ReviewData review12_3 = new ReviewData("어제밤 사온 치킨 아침에 기대하며 먹어보려는데 헉! 튀김옷에 머리카락이 똬악! 당겨서뽑아보려는데 붙어서 안 뽑힌다.", "chicken12.png", 4);
        ReviewData review12_4 = new ReviewData("통닭이랑 못난이 감자 가성비 좋게 먹었습니다! 다만 가게 자체가 협소하고 위생에 있어 ㅎㅎ 감안하고 먹어야할 듯 합니다.", "chicken14.png", 3);
        ReviewData review12_5 = new ReviewData("서빙하는 외국인이 불진철하고 기분이 나쁘게 만들었어요. 이런 서비스 진짜 처음이다 시설, 서비스 그냥 그럼 치킨은 싼맛에 먹는 정도 오히려 닭똥집은 맛있음", "chicken15.png", 1);
        storeData.add(new StoreData("KFC치킨", "치킨.안주.주류", "Rectangle 2421.png", "첫 구매시 3,000원 할인 이벤트 진행 중! 대충 메세지 임을 알려줍니다.", Arrays.asList(review12_1, review12_2, review12_3, review12_4, review12_5)));

        ReviewData review13_1 = new ReviewData("제가 요즘 마라떡볶이에 미쳐있음 마라떡,주먹밥,계란찜사서", "tteokbokki1.png", 5);
        ReviewData review13_2 = new ReviewData("엽떡이니까 당연히 맛은 있는데 양이 제가 가본 엽떡 체인점 중 가장 적네요", "tteokbokki2.png", 1);
        ReviewData review13_3 = new ReviewData("2인엽떡 넘 맛있게 잘먹고가요 친절하시고 감사합니다^^", "tteokbokki3.png", 2);
        storeData.add(new StoreData("엽기떡볶이", "떡볶이.튀김.마라", "Rectangle 2199.png", "리뷰 작성 시 음료 1개 무료 증정 이벤트 진행 중! 대충 메세지 임을 알려줍니다. 이건 말도 안된다고", Arrays.asList(review13_1, review13_2, review13_3)));

        ReviewData review14_1 = new ReviewData("3마리에 소맥 3개 세명이 2차로 간단한 맥주 굿~", "chicken13.png", 4);
        storeData.add(new StoreData("네네치킨", "치킨.안주.주류", "Rectangle 2272.png", "선착순 100명 10% 할인 쿠폰 증정 이벤트 진행 중! 대충 메세지 임을 알려줍니다. 이건 말도 안된다고 아잇 정말 맛업ㅈㅅ어 죽겠네 ", Arrays.asList(review14_1)));

        ReviewData review15_1 = new ReviewData("여섯가지 계절반찬과 추천 짜글이. 찌개류는 짜서 잘 안먹는데 밥을 적셔 먹어도 좋을 정도로 짜지 않았다. 들깨소스연근도 좋았지만 다시마채샐러드는 놀라웠다. 나물과 버섯 열무김치 등 오늘 반찬들은 한정식집 급이었다. 9천", "restaurant1.png", 5);
        ReviewData review15_2 = new ReviewData("this place it is really recommemdable for people who wanna try korean food around sicheong. they have a full card full of korean typical food.", "restaurant3.png", 5);
        storeData.add(new StoreData("엄마집밥", "한식.국밥.주류", "Rectangle 2423.png", "선착순 100명 10% 할인 쿠폰 증정 이벤트 진행 중! 대충 메세지 임을 알려줍니다. 이건 말도 안된다고 아잇 정말 맛업ㅈㅅ어 죽겠네 어그로 끌지마라 임니니니니", Arrays.asList(review15_1, review15_2)));

        ReviewData review16_1 = new ReviewData("1시에도 대기줄이 있네요. 세트a 주문했어요. 돈가스 퀄리티가 좋았어요. 냉모밀이 맛있어요. 국물은 조금 단 편인데 돈가스와 같이 먹으니 조화가 좋았어요. 치즈카츠가 궁금하네요. 여자분도 냉모밀 리필해서 드시더라구요. ", "porkcutlet1.png", 5);
        ReviewData review16_2 = new ReviewData("돈까스는 고기가 얇고 끝으로 갈수록 조금 물리지만, 치돈이 꿀떡꿀떡 잘 넘어가요!", "porkcutlet2.png", 4);
        ReviewData review16_3 = new ReviewData("광화문 교보문고 근처 돈까스  냉모밀 맛집", "porkcutlet3.png", 5);
        storeData.add(new StoreData("왕돈까쓰", "돈까스", "Rectangle 2426.png", "선착순 100명 10% 할인 쿠폰 증정 이벤트 진행 중! 대충 메세지 임을 알려줍니다. 이건 말도 안된다고 아잇 정말 맛업ㅈㅅ어 죽겠네 어그로 끌지마라 임니니니니 라항항항항항항하", Arrays.asList(review16_1, review16_2, review16_3)));

        int menuIndex = 1;
        for (StoreData data : storeData) {
            // locationX = 33 ~ 38 , LocationY = 125 ~ 130
            double randomX = random.nextDouble(5);
            double randomY = random.nextDouble(5);
            File image = new File("src/main/resources/images/" + data.imageName);

            // Market
            Market market = Market.builder()
                    .marketName(data.storeName)
                    .marketDescription(data.tag)
                    .detailAddress("충청북도 충주시 단월동")
                    .mainCategory(MainCategory.RESTAURANT)
                    .locationX(33 + randomX)
                    .locationY(125 + randomY)
                    .cookingTime(40)
                    .eventMessage(data.eventMessage)
                    .marketImage(uploadFile(image))
                    .partner(account)
                    .closeTime(LocalTime.now())
                    .openTime(LocalTime.now())
                    .marketPhone("010-1234-1234")
                    .build();

            // Menu
            int titleRandom = 12;
            for (int i = 0; i < titleRandom; i++) {
                int contentRandom = i + 1;

                MenuContainer menuContainer_data = MenuContainer.builder().classification(String.format("%d 메뉴", i + 1)).priority(2).build();
                for (int j = 0; j < contentRandom; j++) {
                    Menu menu = Menu.builder().menuName(String.format("%d 상세메뉴", menuIndex++)).imageUrl(menuImage_1).description("청양고추의 은은한 알싸함과 최강조합 마블링 고블링 소스. 1초에 1마리씩 팔리네요.").price(9900).build();

                    MenuOptionContainer menuOptionContainer_data_1 = MenuOptionContainer.builder().priority(1).isMultipleSelection(false).classification("옵션_1").build();
                    menuOptionContainer_data_1.addMenuOption(MenuOption.builder().description("옵션 1_1(기본)").optionPrice(0).build());
                    menuOptionContainer_data_1.addMenuOption(MenuOption.builder().description("옵션 1_2(+1000원)").optionPrice(1000).build());
                    menuOptionContainer_data_1.addMenuOption(MenuOption.builder().description("옵션 1_3(+2000원)").optionPrice(2000).build());
                    menuOptionContainer_data_1.addMenuOption(MenuOption.builder().description("옵션 1_4(+3000원)").optionPrice(3000).build());

                    MenuOptionContainer menuOptionContainer_data_2 = MenuOptionContainer.builder().priority(1).isMultipleSelection(true).classification("옵션_2").build();
                    menuOptionContainer_data_2.addMenuOption(MenuOption.builder().description("옵션 2_1(기본)").optionPrice(0).build());
                    menuOptionContainer_data_2.addMenuOption(MenuOption.builder().description("옵션 2_2(+4000원)").optionPrice(4000).build());
                    menuOptionContainer_data_2.addMenuOption(MenuOption.builder().description("옵션 2_3(+5000원)").optionPrice(5000).build());

                    MenuOptionContainer menuOptionContainer_data_3 = MenuOptionContainer.builder().priority(1).isMultipleSelection(false).classification("옵션_3").build();
                    menuOptionContainer_data_3.addMenuOption(MenuOption.builder().description("옵션 3_1(기본)").optionPrice(0).build());
                    menuOptionContainer_data_3.addMenuOption(MenuOption.builder().description("옵션 3_2(+6000원)").optionPrice(6000).build());

                    menu.addMenuOptionContainer(menuOptionContainer_data_1);
                    menu.addMenuOptionContainer(menuOptionContainer_data_2);
                    menu.addMenuOptionContainer(menuOptionContainer_data_3);
                    menuContainer_data.addMenu(menu);
                }

                market.addMenuContainer(menuContainer_data);
            }
            MenuContainer menuContainer = MenuContainer.builder().classification("음료/주류").priority(7).build();
            menuContainer.addMenu(Menu.builder().menuName("콜라").imageUrl(menuImage_13).price(1000).build());
            menuContainer.addMenu(Menu.builder().menuName("사이다").description("시원한 사이다입니다. 마시면 기분이 좋아집니다.").price(1000).build());
            menuContainer.addMenu(Menu.builder().menuName("소주").imageUrl(menuImage_14).description("시원한 소주입니다. 마시면 기분이 좋아집니다.").price(5000).build());
            menuContainer.addMenu(Menu.builder().menuName("맥주").price(5000).build());
            market.addMenuContainer(menuContainer);

            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
            // Coupon
            for (int i = 0; i < 3; i++) {
                int randomData = random.nextInt(10) * 10000;
                int randomData_2 = random.nextInt(10) * 500;

                CouponCreateRequest couponCreateRequest = new CouponCreateRequest(String.format("%s원 할인 쿠폰", numberFormat.format(randomData_2)), randomData, ConditionToUse.MINIMUM_AMOUNT, randomData_2);
                Coupon coupon = Coupon.createEntity(couponCreateRequest);

                market.addCoupon(coupon);
            }

            // Review
            for (ReviewData reviewData : data.reviewDataList) {
                int recommenderCount = random.nextInt(30);

                Review review = Review.builder()
                        .reviewer(account)
                        .content(reviewData.data)
                        .score(reviewData.score)
                        .build();

                for (String imageName : reviewData.imageNames) {
                    File fileImage = new File("src/main/resources/images/" + imageName);
                    String imageUrl = uploadFile(fileImage);

                    review.addReviewImage(imageUrl);
                }

                // Recommendation
                for (int i = 0; i < recommenderCount; i++) {
                    ReviewRecommender reviewRecommender = ReviewRecommender.builder()
                            .review(review)
                            .account(account)
                            .build();

                    review.addRecommender(reviewRecommender);
                }

                market.addReview(review);
            }

            autoCompleteService.addSearchWord(market.getMarketName());
            marketDocumentRepository.save(MarketDocument.createEntity(market));
            addPopularSearch(market.getMarketName());
            marketRepository.save(market);
        }

        // 홍보 데이터 ( 메인 )
        List<PromotionData> promotionData = new ArrayList<>();
        promotionData.add(new PromotionData("스카이캡슐\n반값으로 입장하기", PromotionLocation.MAIN, new String[]{"부산", "관광지"}, "Rectangle 2436.png"));
        promotionData.add(new PromotionData("튤립정원\n축제 학생할인 받는 방법", PromotionLocation.MAIN, new String[]{"튤립", "인생사진"}, "Rectangle 2437.png"));
        promotionData.add(new PromotionData("양떼목장\n체험해보고 싶다면?", PromotionLocation.MAIN, new String[]{"양", "목장체험"}, "Rectangle 2439.png"));
        promotionData.add(new PromotionData("데이트하기 좋은\n호숫가 위 보트체험", PromotionLocation.MAIN, new String[]{"호수", "데이트명소"}, "Rectangle 2440.png"));
        promotionData.add(new PromotionData("할인 받고 튤립 호수공원에서 인생샷 남기기", PromotionLocation.ATTRACTION, new String[]{"튤립", "인생샷"}, "Rectangle 2450.png"));
        promotionData.add(new PromotionData("부산여행 가고 싶은데 돈이 걱정이라면?", PromotionLocation.ATTRACTION, new String[]{"부산", "여행"}, "Rectangle 2452.png"));

        for (PromotionData data : promotionData) {
            File fileImage = new File("src/main/resources/images/" + data.imageName);
            String imageUrl = uploadFile(fileImage);

            Promotion promotion = Promotion.builder()
                    .introduce(data.introduce)
                    .imageUrl(imageUrl)
                    .promotionLocation(data.location)
                    .build();

            for (String tag : data.tags) {
                PromotionTag promotionTag = PromotionTag.builder()
                        .tagContent(tag)
                        .build();
                promotionTagRepository.save(promotionTag);

                promotion.addTag(PromotionTagRelationShip.builder()
                        .promotionTag(promotionTag)
                        .promotion(promotion)
                        .build());
            }

            promotionRepository.save(promotion);
        }

        // 관광 시설
        List<AttractionData> attractionData = new ArrayList<>();
        attractionData.add(new AttractionData("롯데월드", "놀이공원.어트랙션.퍼레이드", LocalTime.now(), LocalTime.now().plusHours(5), "학생할인 최대 30%까지 진행 중", "Rectangle 2295.png"));
        attractionData.add(new AttractionData("경복궁", "경복궁.한옥.한복", LocalTime.now(), LocalTime.now().plusHours(5), "한복 체험 10% 할인 가능", "Rectangle 2455.png"));
        attractionData.add(new AttractionData("KTX 승차권", "KTK.대중교통", LocalTime.now(), LocalTime.now().plusHours(5), "승차권 주중 15% 할인 예매 가능", "Rectangle 2457.png"));
        attractionData.add(new AttractionData("서울스카이", "서울.전망대.야경", LocalTime.now(), LocalTime.now().plusHours(5), "입장권 1+1 행사 이벤트 진행 중", "Rectangle 2459.png"));
        attractionData.add(new AttractionData("제주도", "제주도.관광지.바다", LocalTime.now(), LocalTime.now().plusHours(5), "제주도 내 관광지 할인 쿠폰 제공", "Rectangle 2461.png"));
        attractionData.add(new AttractionData("남산타워", "서울.전망대.야경", LocalTime.now(), LocalTime.now().plusHours(5), "입장권 1+1 행사 이벤트 진행 중", "Rectangle 2470.png"));
        attractionData.add(new AttractionData("부산 아쿠아리움", "부산.아쿠아리움", LocalTime.now(), LocalTime.now().plusHours(5), "입장권 5% 할인 및 식사권 제공", "Rectangle 2468.png"));
        attractionData.add(new AttractionData("캐리비안베이", "워터파크.어트랙션.퍼레이드", LocalTime.now(), LocalTime.now().plusHours(5), "학생할인 20% 할인 입장권 제공", "Rectangle 2467.png"));
        attractionData.add(new AttractionData("부산 스카이캡슐", "부산.관광지.스카이캡슐", LocalTime.now(), LocalTime.now().plusHours(5), "승차권 주중 15% 할인 예매 가능", "Rectangle 2472.png"));
        attractionData.add(new AttractionData("전주 한옥마을", "한옥.한복", LocalTime.now(), LocalTime.now().plusHours(5), "한복 대여 10% 할인쿠폰 제공", "Rectangle 2471.png"));
        for (AttractionData data : attractionData) {
            File fileImage = new File("src/main/resources/images/" + data.imageUrl);
            String imageUrl = uploadFile(fileImage);

            // locationX = 33 ~ 38 , LocationY = 125 ~ 130
            double randomX = random.nextDouble(5);
            double randomY = random.nextDouble(5);

            Attraction attraction = Attraction.builder()
                    .attractionName(data.title)
                    .attractionDescription(data.description)
                    .openTime(data.openTime)
                    .closeTime(data.closeTime)
                    .eventMessage(data.eventMessage)
                    .imageUrl(imageUrl)
                    .locationX(33 + randomX)
                    .locationY(125 + randomY)
                    .build();

            attractionRepository.save(attraction);
        }

        // 파트너 요청
        List<PartnerRequestData> partnerRequestData = new ArrayList<>();
        partnerRequestData.add(new PartnerRequestData("잎사이", "서울특별시 ㅇㅇ구 ㅇㅇ로 12길 3-45", "애옹빌라 302호", "잎사이 치킨집", "잎사이 치킨집 제발 등록해주시면 안될까요...? 여기 너무 맛있고 맨날 시켜먹는데 할인쿠폰까지 있으면 진짜 너무 좋을 것 같아서 남겨봅니다.. 잎사이 항상 잘 애용하고 있어요! 잎사이 최고!! 역시 최고의 앱!", "010 - 1234 - 5678"));
        partnerRequestData.add(new PartnerRequestData("화로상회 건대점", "서울특별시 ㅇㅇ구 ㅇㅇ로 12길 3-45", "애옹빌라 302호", "화로상회 건대점", "화로상회는 언제쯤 들어올까요? 전에 다른 지역에서 가봤는데 너무 맛있었어서 또 먹고 싶은데....", "010 - 1234 - 5678"));
        partnerRequestData.add(new PartnerRequestData("냥냥치킨", "서울특별시 ㅇㅇ구 ㅇㅇ로 12길 3-45", "애옹빌라 302호", "이번에 들어온 냥냥치킨", "이번에 들어온 냥냥치킨도 가능할까요? 궁금한데 아직 가본 적은 없어서 나중에 배달시켜 먹어보고 싶어서 신청해봅니다!!", "010 - 1234 - 5678"));
        partnerRequestData.add(new PartnerRequestData("매드슬로우", "서울특별시 ㅇㅇ구 ㅇㅇ로 12길 3-45", "애옹빌라 302호", "매드슬로우", "매드슬로우 칵테일 바 전에 가봤는데 메뉴랑 칵테일이 너무 맛있어서자주 먹고싶은데 가격대가 좀 있어서,,, 부탁드립니다!", "010 - 1234 - 5678"));
        partnerRequestData.add(new PartnerRequestData("마녀식당", "서울특별시 ㅇㅇ구 ㅇㅇ로 12길 3-45", "애옹빌라 302호", "마녀식당", "여기 메뉴들 다 신박하고 맛도 있어서 자주 가는데 요즘 통 바빠서 밖에 잘 못나가서요ㅠㅠ 마녀식당 가능하다면 부탁드립니다.", "010 - 1234 - 5678"));
        partnerRequestData.add(new PartnerRequestData("마포곱창타운", "서울특별시 ㅇㅇ구 ㅇㅇ로 12길 3-45", "애옹빌라 302호", "마포곱창타운", "마포곱창타운 단골입니다. 긴말 안합니다. 여기 업체 등록되면 단연 불굴의 1등 맛집될 것이라고 보장합니다. 제발 마포곱창타운 해주세요!!", "010 - 1234 - 5678"));
        partnerRequestData.add(new PartnerRequestData("충주 규카츠", "서울특별시 ㅇㅇ구 ㅇㅇ로 12길 3-45", "애옹빌라 302호", "규카츠집", "보니까 규카츠집은 없던데 따로 들어올 업체는 없나용?ㅡ? 제가 규카츠를 엄청 좋아하는데 없어서 부탁드려봅니당!", "010 - 1234 - 5678"));
        partnerRequestData.add(new PartnerRequestData("메가커피", "서울특별시 ㅇㅇ구 ㅇㅇ로 12길 3-45", "애옹빌라 302호", "메가커피 원해요!", "메가커피 자주 가는데 할인쿠폰 생기면 좋을 것 같아서 신청해봅니다! 보니까 음식점은 많은데 카페는 별로 없더라구요.. 메가커피 꼭 부탁드립니다!!", "010 - 1234 - 5678"));
        partnerRequestData.add(new PartnerRequestData("충주 케이크", "서울특별시 ㅇㅇ구 ㅇㅇ로 12길 3-45", "애옹빌라 302호", "케이크 전문점", "저는 케이크 덕후인데 케이크 배달해주는 곳은 거의 없어서 직접 사먹으러 나가야 하는게 불편해요... 그래서 케이크전가 너무 좋아요!!", "010 - 1234 - 5678"));
        partnerRequestData.add(new PartnerRequestData("춘리 마라탕", "서울특별시 ㅇㅇ구 ㅇㅇ로 12길 3-45", "애옹빌라 302호", "춘리마라탕 들어왔으면", "마라탕 중에 제일 맛있는 춘리마라탕이 없는게 말이 안됩니다! 잎사이는 당장 춘리마라탕을 등록하라!!!", "010 - 1234 - 5678"));
        partnerRequestData.add(new PartnerRequestData("충주 아구찜", "서울특별시 ㅇㅇ구 ㅇㅇ로 12길 3-45", "애옹빌라 302호", "아구찜 하는 곳", "저기 뭐냐 그 충주호였나 근처에 아구찜 맛집있던데 거기는 너무 멀어서 먹고 싶을 때 찾아가기 힘들어요.. 배달로 편리하게 먹고싶어요..", "010 - 1234 - 5678"));
        partnerRequestData.add(new PartnerRequestData("건대토스트", "서울특별시 ㅇㅇ구 ㅇㅇ로 12길 3-45", "애옹빌라 302호", "건대토스트", "여기 배달하긴 하던데 좀더 할인 받은 가격으로 먹으면 좋겠다 싶어서 한번 요청드려봅니다....", "010 - 1234 - 5678"));

        List<PartnerCommentData> partnerCommentData = new ArrayList<>();
        partnerCommentData.add(new PartnerCommentData("헉 너무 공감이라 추천을 안 누를 수가 없네용"));
        partnerCommentData.add(new PartnerCommentData("그니까요 잎사이 치킨집 대체 언제 들어오는지,,,"));

        for (PartnerRequestData data : partnerRequestData) {
            PartnerRequest request = PartnerRequest.builder()
                    .requestMarketName(data.requestMarketName)
                    .marketAddress(data.marketAddress)
                    .detailAddress(data.detailAddress)
                    .title(data.title)
                    .description(data.description)
                    .phoneNumber(data.phoneNumber)
                    .requestUser(account)
                    .build();

            int recommend = random.nextInt(20);
            for (int i = 0; i < recommend; i++) {
                PartnerRecommender recommender = PartnerRecommender.builder()
                        .partnerRequest(request)
                        .account(account2)
                        .build();

                request.addPartnerRecommender(recommender);
            }

            for (PartnerCommentData data2 : partnerCommentData) {
                PartnerComment partnerComment = PartnerComment.builder()
                        .message(data2.message)
                        .writer(account3)
                        .partnerRequest(request)
                        .build();

                request.addPartnerComment(partnerComment);
            }

            partnerRequestRepository.save(request);
        }

        // Events
        List<AnnounceData> announceData = new ArrayList<>();
        announceData.add(new AnnounceData("잎사이 관련 자주 올라오는 질문사항", AnnounceType.ANNOUNCE, false));
        announceData.add(new AnnounceData("6월 이벤트 진행 안내사항", AnnounceType.EVENT, false));
        announceData.add(new AnnounceData("파트너쉽 업체 등록하는 방법", AnnounceType.ANNOUNCE, false));
        announceData.add(new AnnounceData("잎사이 1.2.3 버전 버그 수정 안내", AnnounceType.ANNOUNCE, false));
        announceData.add(new AnnounceData("잎사이 쿠폰보관함 관련 문의사항 안내", AnnounceType.ANNOUNCE, false));
        announceData.add(new AnnounceData("잎사이 쿠폰보관함 관련 문의사항 안내", AnnounceType.ANNOUNCE, false));
        announceData.add(new AnnounceData("파트너쉽 업체 등록하는 방법", AnnounceType.ANNOUNCE, false));
        announceData.add(new AnnounceData("잎사이 사용 관련 공지사항", AnnounceType.ANNOUNCE, true));
        announceData.add(new AnnounceData("잎사이 보안 관련 안내사항", AnnounceType.EVENT, true));
        announceData.add(new AnnounceData("신규 회원전용 잎사이 사용방법 안내", AnnounceType.ANNOUNCE, true));

        for (AnnounceData data : announceData) {
            Announce announce = Announce.builder()
                    .isImportant(data.isImportant)
                    .title(data.title)
                    .announceType(data.announceType)
                    .description("잎사이 사용 관련 공지사항 내용글입니다. \n" +
                            "\n" +
                            "잎사이 사용 관련 공지사항 내용글입니다. 잎사이 사용 관련 공지사항 내용글입니다. 잎사이 사용 관련 공지사항 내용글입니다. 잎사이 사용 관련 공지사항 내용글입니다. 잎사이 사용 관련 공지사항 내용글입니다. \n" +
                            "\n" +
                            "잎사이 사용 관련 공지사항 내용글입니다. 잎사이 사용 관련 공지사항 내용글입니다. 잎사이 사용 관련 공지사항 내용글입니다. \n" +
                            "\n" +
                            "잎사이 사용 관련 공지사항 내용글입니다. 잎사이 사용 관련 공지사항 내용글입니다. 잎사이 사용 관련 공지사항 내용글입니다. 잎사이 사용 관련 공지사항 내용글입니다. 잎사이 사용 관련 공지사항 내용글입니다. ")
                    .build();

            announceRepository.save(announce);
        }

        entityManager.flush();

        couponService.downloadCoupon(currentUser1, 1);
        couponService.downloadCoupon(currentUser2, 1);
        couponService.downloadCoupon(currentUser3, 1);
        couponService.downloadCoupon(currentUser1, 2);
        couponService.downloadCoupon(currentUser1, 3);
        couponService.downloadCoupon(currentUser1, 4);
        couponService.downloadCoupon(currentUser1, 5);
        couponService.downloadCoupon(currentUser1, 6);
        couponService.downloadCoupon(currentUser1, 7);
        couponService.downloadCoupon(currentUser1, 8);
        favoriteService.addFavorite(currentUser1, 1);
        favoriteService.addFavorite(currentUser1, 2);
        favoriteService.addFavorite(currentUser1, 3);
        favoriteService.addFavorite(currentUser1, 4);
        favoriteService.addFavorite(currentUser1, 5);
        favoriteService.addFavorite(currentUser1, 6);
        favoriteService.addFavorite(currentUser1, 7);
        favoriteService.addFavorite(currentUser1, 8);
        favoriteService.addFavorite(currentUser1, 9);
        favoriteService.addFavorite(currentUser1, 10);
        favoriteService.addFavorite(currentUser1, 11);
        favoriteService.addFavorite(currentUser1, 12);
        BasketCreateRequest basketCreateRequest1 = BasketCreateRequest.builder()
                .menuId(1)
                .amount(1)
                .menuOptionIds(Arrays.asList(1L,2L,3L))
                .build();
        basketService.addBasket(currentUser1, 1, basketCreateRequest1);
        BasketCreateRequest basketCreateRequest2 = BasketCreateRequest.builder()
                .menuId(2)
                .amount(2)
                .menuOptionIds(Arrays.asList(1L))
                .build();
        basketService.addBasket(currentUser1, 1, basketCreateRequest2);
        BasketCreateRequest basketCreateRequest3 = BasketCreateRequest.builder()
                .menuId(3)
                .amount(1)
                .menuOptionIds(Arrays.asList(4L,5L))
                .build();
        basketService.addBasket(currentUser1, 1, basketCreateRequest3);
        BasketCreateRequest basketCreateRequest4 = BasketCreateRequest.builder()
                .menuId(4)
                .amount(3)
                .menuOptionIds(Arrays.asList(1L))
                .build();
        basketService.addBasket(currentUser1, 2, basketCreateRequest4);
        BasketCreateRequest basketCreateRequest5 = BasketCreateRequest.builder()
                .menuId(3)
                .amount(2)
                .menuOptionIds(Arrays.asList(4L,5L))
                .build();
        basketService.addBasket(currentUser1, 2, basketCreateRequest5);
        BasketCreateRequest basketCreateRequest6 = BasketCreateRequest.builder()
                .menuId(5)
                .amount(8)
                .menuOptionIds(Arrays.asList(4L,5L))
                .build();
        basketService.addBasket(currentUser1, 2, basketCreateRequest6);

        orderService.addOrder(currentUser1, new OrderCreateRequest(1,1, Arrays.asList(1L,2L), PaymentType.KAKAO_PAY));
        orderService.addOrder(currentUser1, new OrderCreateRequest(0,1, Arrays.asList(1L), PaymentType.KAKAO_PAY));
        orderService.addOrder(currentUser1, new OrderCreateRequest(1,1, Arrays.asList(1L,2L,3L), PaymentType.NAVER_PAY));
        orderService.addOrder(currentUser1, new OrderCreateRequest(0,1, Arrays.asList(1L,2L,3L), PaymentType.KAKAO_PAY));
        orderService.addOrder(currentUser1, new OrderCreateRequest(1,1, Arrays.asList(2L,3L), PaymentType.NAVER_PAY));
        orderService.addOrder(currentUser1, new OrderCreateRequest(0,1, Arrays.asList(2L,3L), PaymentType.NAVER_PAY));
        orderService.addOrder(currentUser1, new OrderCreateRequest(0,2, Arrays.asList(4L), PaymentType.KG_INICIS));
        orderService.addOrder(currentUser1, new OrderCreateRequest(0,2, Arrays.asList(4L), PaymentType.KG_INICIS));
        orderService.addOrder(currentUser1, new OrderCreateRequest(0,2, Arrays.asList(4L,5L), PaymentType.KAKAO_PAY));
        orderService.addOrder(currentUser1, new OrderCreateRequest(0,2, Arrays.asList(4L,5L), PaymentType.KAKAO_PAY));

        List<ReviewData> reviewDataList = new ArrayList<>();
        reviewDataList.add(new ReviewData("예약하고 방문하세요! 나오는데 오래걸립니다아 맛있고 맥주랑 잘어울림다", "chicken8.png", 4));
        reviewDataList.add(new ReviewData("너무 맛있게 잘 먹었어요! 비빔국수랑 누룽지통닭이랑 조합이 짱입니다! 비빔국수 안 시켰으면 후회 할 뻔 했어요", "chicken9.png", 4));
        reviewDataList.add(new ReviewData("오랜만에 장작집이 생각나서 다녀왓습니다. 전기구이와 비교할수없는 장작구이만의 맛은 여전하네요 ~ 맛있게 잘 먹고 갑니다", "chicken7.png", 5));
        List<Order> orderList = orderRepository.findAllById(Arrays.asList(1L,2L,3L));
        int index = 0;
        for (ReviewData reviewData: reviewDataList) {
            Review review = Review.builder()
                    .reviewer(account)
                    .score(4)
                    .content(reviewData.data)
                    .order(orderList.get(index))
                    .market(market_detail)
                    .build();

            orderList.get(index++).setReview(review);
            market_detail.addReview(review);
        }

        List<EventData> eventData = new ArrayList<>();
        eventData.add(new EventData("캐시백 이벤트로 만원 돌려받으세요!", "Rectangle 22222.png" , LocalDate.now().minusDays(7) , LocalDate.now().plusDays(7)));
        eventData.add(new EventData("리뷰작성 후 추첨으로 3,000원 받아가세요!", "Rectangle 2223.png" , LocalDate.now().minusDays(7) , LocalDate.now().plusDays(7)));
        eventData.add(new EventData("가입 시 스페셜 웰컴킷 증정 이벤트 진행중!", "Rectangle 2222.png" , LocalDate.of(2000,3,2) , LocalDate.of(2001,6,21)));
        for(EventData event : eventData) {
            String eventImages = uploadFile(new File("src/main/resources/images/" + event.imageName));

            Event eventEntity = Event.builder()
                    .title(event.title)
                    .description("이벤트 관련 내용글입니다.\n" +
                            "\n" +
                            "이벤트 관련 내용글입니다.이벤트 관련 내용글입니다.이벤트 관련 내용글입니다.이벤트 관련 내용글입니다.이벤트 관련 내용글입니다.이벤트 관련 내용글입니다.\n" +
                            "\n" +
                            "이벤트 관련 내용글입니다.이벤트 관련 내용글입니다.이벤트 관련 내용글입니다.이벤트 관련 내용글입니다.\n" +
                            "\n" +
                            "이벤트 관련 내용글입니다.이벤트 관련 내용글입니다.이벤트 관련 내용글입니다.\n" +
                            "\n" +
                            "이벤트 관련 내용글입니다.")
                    .imageUrl(eventImages)
                    .eventStartDate(event.eventStartDate)
                    .eventEndDate(event.eventEndDate)
                    .build();

            eventRepository.save(eventEntity);
        }

        // report
        Report report_1 = Report.builder().title("욕설신고합니다.").reporter(account).content("욕설신고합니다.").reportContent(ReportContent.REVIEW).build();
        Report report_2 = Report.builder().title("비방글 신고").reporter(account).content("비방글 신고").reportContent(ReportContent.PARTNER_REQUEST).build();
        Report report_3 = Report.builder().title("지속적인 욕설 신고하려구요").reporter(account).content("계정정지 부탁드립니다.").reportContent(ReportContent.REVIEW).build();
        Report report_4 = Report.builder().title("계정정지 부탁드립니다.").reporter(account).content("욕설신고합니다.").reportContent(ReportContent.REVIEW).build();
        Report report_5 = Report.builder().title("계속 같은 사람이 시비걸어요").reporter(account).content("계속 같은 사람이 시비걸어요").reportContent(ReportContent.REVIEW).build();
        reportRepository.save(report_1);
        reportRepository.save(report_2);
        reportRepository.save(report_3);
        reportRepository.save(report_4);
        reportRepository.save(report_5);


        // Chat
        ChatRoom chatRoom_1 = ChatRoom.builder().account(account).build();
        chatRoom_1.addChat(Chat.createEntity("message1", false));
        chatRoom_1.addChat(Chat.createEntity("message2", false));
        chatRoom_1.addChat(Chat.createEntity("궁금한게 있어오!ㅡ!", false));
        ChatRoom chatRoom_2 = ChatRoom.builder().account(account2).build();
        chatRoom_2.addChat(Chat.createEntity("message1", false));
        chatRoom_2.addChat(Chat.createEntity("message2", false));
        chatRoom_2.addChat(Chat.createEntity("이럴 때는 어떻게 해야하나요?", false));
        ChatRoom chatRoom_3 = ChatRoom.builder().account(account3).build();
        chatRoom_3.addChat(Chat.createEntity("message1", false));
        chatRoom_3.addChat(Chat.createEntity("message2", false));
        chatRoom_3.addChat(Chat.createEntity("이벤트 관련 오류가 있어서 문의드립니다.", false));
        ChatRoom chatRoom_4 = ChatRoom.builder().account(account4).build();
        chatRoom_4.addChat(Chat.createEntity("message1", false));
        chatRoom_4.addChat(Chat.createEntity("message2", false));
        chatRoom_4.addChat(Chat.createEntity("업체 등록은 언제쯤 반영되나요?", false));
        ChatRoom chatRoom_5 = ChatRoom.builder().account(account5).build();
        chatRoom_5.addChat(Chat.createEntity("message1", false));
        chatRoom_5.addChat(Chat.createEntity("message2", false));
        chatRoom_5.addChat(Chat.createEntity("포인트는 어떻게 사용할 수 있는 건가요?", false));

        chatRoomRepository.save(chatRoom_1);
        chatRoomRepository.save(chatRoom_2);
        chatRoomRepository.save(chatRoom_3);
        chatRoomRepository.save(chatRoom_4);
        chatRoomRepository.save(chatRoom_5);

        // visitor
        for(int i = 15; i >= 1; i--) {
            int random_amount = random.nextInt(20);

            for(int j = 0; j < random_amount; j++) {
                Visitant visitant = Visitant.builder().userIp("0001").userAgent("userAgent").visitDate(LocalDate.now().minusDays(i)).build();

                visitantRepository.save(visitant);
            }
        }

        settingInitData();
    }
}
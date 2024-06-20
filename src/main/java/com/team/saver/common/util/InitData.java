package com.team.saver.common.init;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.team.saver.account.entity.Account;
import com.team.saver.account.entity.UserRole;
import com.team.saver.account.repository.AccountRepository;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.market.coupon.entity.Coupon;
import com.team.saver.market.review.entity.Review;
import com.team.saver.market.store.entity.MainCategory;
import com.team.saver.market.store.entity.Market;
import com.team.saver.market.store.entity.Menu;
import com.team.saver.market.store.repository.MarketRepository;
import com.team.saver.market.store.util.RecommendAlgorithm;
import com.team.saver.oauth.util.OAuthType;
import com.team.saver.search.popular.util.SearchWordScheduler;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

}

@AllArgsConstructor
class ReviewData {
    String title;

    String data;

    String imageName;
}

@Component
@RequiredArgsConstructor
public class InitData implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final MarketRepository marketRepository;
    private final RecommendAlgorithm recommendAlgorithm;
    private final SearchWordScheduler searchWordScheduler;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.region.static}")
    private String region;

    public void settingInitData() {
        // init Data-Start
        recommendAlgorithm.updateMarketRecommend();
        searchWordScheduler.updateSearchWordScore();
        searchWordScheduler.calculateRankingChangeValue();
        // init Data-End
    }

    private void deleteAllObjectsInBucket() throws Exception {
        List<S3ObjectSummary> s3objects = amazonS3Client.listObjects(bucketName).getObjectSummaries();

        for(S3ObjectSummary object : s3objects) {
            amazonS3Client.deleteObject(bucketName, object.getKey());
        }
    }

    private String uploadFile(File file) {
        String fileName = UUID.randomUUID().toString();

        ObjectMetadata metadata= new ObjectMetadata();
        metadata.setContentType("image/png");
        metadata.setContentLength(file.length());

        String imageUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName ,region , fileName);
        try{
            amazonS3Client.putObject(bucketName, fileName, new FileInputStream(file) , metadata);
        } catch (IOException e) {
            throw new CustomRuntimeException(AWS_SERVER_EXCEPTION, e.getMessage());
        }

        return imageUrl;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        deleteAllObjectsInBucket();

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

        List<StoreData> storeData = new ArrayList<>();
        storeData.add(new StoreData("짱돌" , "치킨.안주.주류", "Rectangle 2208.png" , "첫 구매시 3,000원 \n"));
        storeData.add(new StoreData("피자나라 치킨공주" , "치킨.피자.안주.주류", "Rectangle 2269.png" , "리뷰 작성 시 음료 1개\n" + "무료 증정 이벤트 진행 중!"));
        storeData.add(new StoreData("태권치킨" , "치킨.안주.주류", "Rectangle 2270.png" , "선착순 100명 10% 할인\n" + "쿠폰 증정 이벤트 진행 중!"));
        storeData.add(new StoreData("스시마당" , "초밥.우동.냉모밀.덮밥", "Rectangle 2208-1.png" , "첫 구매시 3,000원 \n" + "할인 이벤트 진행 중! \n 대충 메세지 임을 알려줍니다."));
        storeData.add(new StoreData("BHC치킨" , "치킨.안주.주류", "Rectangle 2412.png" , "리뷰 작성 시 음료 1개\n" + "무료 증정 이벤트 진행 중! \n 대충 메세지 임을 알려줍니다. \n 이건 말도 안된다고"));
        storeData.add(new StoreData("장충동 왕족발보쌈" , "족발.보쌈", "Rectangle 2413.png" , "선착순 100명 10% 할인\n" + "쿠폰 증정 이벤트 진행 중! \n 대충 메세지 임을 알려줍니다. \n 이건 말도 안된다고 \n 아잇 정말 맛업ㅈㅅ어 죽겠네 "));
        storeData.add(new StoreData("버거킹" , "햄버거.음료", "Rectangle 2415.png" , "첫 구매시 3,000원 \n" + "할인 이벤트 진행 중! \n 맛있게 드세오!"));
        storeData.add(new StoreData("서브웨이" , "샌드위치.음료", "Rectangle 2416.png" , "첫 구매시 3,000원 \n" + "할인 이벤트 진행 중!"));
        storeData.add(new StoreData("롯데리아" , "햄버거.음료", "Rectangle 2417.png" , "첫 구매시 3,000원 \n" + "할인 이벤트 진행 중! \n 맛있게 드세오!"));
        storeData.add(new StoreData("베스킨라빈스" , "아이스크림.음료", "Rectangle 2419.png" , "첫 구매시 3,000원 \n" + "할인 이벤트 진행 중! \n 맛있게 드세오! \n 그럼 맛없게 먹겠냐"));
        storeData.add(new StoreData("쭈꾸대장" , "쭈꾸미.볶음밥.계란찜.주류", "Rectangle 2420.png" , "잎 사이가 정말 좋아요 \n 정말 맛있어요! \n 사장님이 정말 맛있습니다."));
        storeData.add(new StoreData("KFC치킨" , "치킨.안주.주류", "Rectangle 2421.png" , "첫 구매시 3,000원 \n" + "할인 이벤트 진행 중! \n 대충 메세지 임을 알려줍니다."));
        storeData.add(new StoreData("엽기떡볶이" , "떡볶이.튀김.마라", "Rectangle 2199.png" , "리뷰 작성 시 음료 1개\n" + "무료 증정 이벤트 진행 중! \n 대충 메세지 임을 알려줍니다. \n 이건 말도 안된다고"));
        storeData.add(new StoreData("네네치킨" , "치킨.안주.주류", "Rectangle 2272.png" , "선착순 100명 10% 할인\n" + "쿠폰 증정 이벤트 진행 중! \n 대충 메세지 임을 알려줍니다. \n 이건 말도 안된다고 \n 아잇 정말 맛업ㅈㅅ어 죽겠네 "));
        storeData.add(new StoreData("엄마집밥" , "한식.국밥.주류", "Rectangle 2423.png" , "\"선착순 100명 10% 할인\\n\" + \"쿠폰 증정 이벤트 진행 중! \\n 대충 메세지 임을 알려줍니다. \\n 이건 말도 안된다고 \\n 아잇 정말 맛업ㅈㅅ어 죽겠네 \n 어그로 끌지마라 임니니니니"));
        storeData.add(new StoreData("왕돈까쓰" , "돈까스", "Rectangle 2426.png" , "\"선착순 100명 10% 할인\\n\" + \"쿠폰 증정 이벤트 진행 중! \\n 대충 메세지 임을 알려줍니다. \\n 이건 말도 안된다고 \\n 아잇 정말 맛업ㅈㅅ어 죽겠네 \n 어그로 끌지마라 임니니니니 \n 라항항항항항항하"));
        storeData.add(new StoreData("이찌방라멘" , "라멘", "Rectangle 2429.png" , "잎 사이가 정말 좋아요"));
        storeData.add(new StoreData("싱싱상회" , "회.대게", "Rectangle 2432.png" , "하하"));

        for(StoreData data : storeData) {
            double randomX = random.nextDouble(99999);
            double randomY = random.nextDouble(99999);
            File image = new File("src/main/resources/images/" + data.imageName);

            Market market = Market.builder()
                    .marketName(data.storeName)
                    .marketDescription(data.tag)
                    .detailAddress("충청북도 충주시 단월동")
                    .mainCategory(MainCategory.RESTAURANT)
                    .locationY(randomX)
                    .locationX(randomY)
                    .eventMessage(data.eventMessage)
                    .marketImage(uploadFile(image))
                    .partner(account)
                    .closeTime(LocalTime.now())
                    .openTime(LocalTime.now())
                    .marketPhone("01012341234")
                    .build();

            // classification

            // Menu
            int priceRandom = random.nextInt(20);
            Menu menu1 = Menu.builder().menuName("메뉴1").price(priceRandom * 1000).build();
            market.addMenu(menu1);
            Menu menu2 = Menu.builder().menuName("메뉴2").price(priceRandom * 1500).build();
            market.addMenu(menu2);
            Menu menu3 = Menu.builder().menuName("메뉴3").price(priceRandom * 1400).build();
            market.addMenu(menu3);
            Menu menu4 = Menu.builder().menuName("메뉴4").price(priceRandom * 2000).build();
            market.addMenu(menu4);
            Menu menu5 = Menu.builder().menuName("메뉴5").price(priceRandom * 3000).build();
            market.addMenu(menu5);

            // Coupon
            for (int i = 0; i < 5; i++) {
                int randomData = random.nextInt(10) * 1000;

                Coupon coupon = Coupon.builder()
                        .couponName("쿠폰 이름 " + i)
                        .couponDescription("쿠폰 설명")
                        .market(market)
                        .saleRate(randomData)
                        .build();

                market.addCoupon(coupon);
            }

            // Review
            int reviewCount = random.nextInt(12) + 1;
            List<ReviewData> reviewDataList = new ArrayList<>();
            reviewDataList.add(new ReviewData("너무 맛있어요!! " , "여러분 여기 진짜 맛집이에요!!!! 너모너모 맛있어요ㅠ 잎사이 쿠폰으로 어디서도 보지 못한 할인을 받아서 가격도 착해오!!" , "Rectangle 2221.png"));
            reviewDataList.add(new ReviewData("파스타가 이쁘고 알바생이 맛있어요!! " , "하,, 파스타가 이렇게 귀여워도 되는거예요?여기서 처음 시켜봤는데 귀여운 음식만 한가득이라 고민만 오조오억번 했답니당,,," , "Rectangle 2221-1.png"));
            reviewDataList.add(new ReviewData("이... 이게무슨! " , "긴말 필요없습니다.. 연어 환장하시는 분은 꼭 사드세요.. 젭알.....8ㅅ8" , "Rectangle 2435.png"));
            //String reviewImage[] = new String[]{"Rectangle 2221.png" , "Rectangle 2221-1.png" , "Rectangle 2435.png"};

            for (int i = 0; i < reviewCount; i++) {
                int randomScore = random.nextInt(5) + 1;
                int imageCount = random.nextInt(2);
                ReviewData reviewData = reviewDataList.get(i % reviewDataList.size());

                Review review = Review
                        .builder()
                        .reviewer(account)
                        .title(reviewData.title)
                        .content(reviewData.data)
                        .score(randomScore)
                        .build();

                File fileImage = new File("src/main/resources/images/" + reviewData.imageName);
                review.addReviewImage(uploadFile(fileImage));

//                for(int j = 0; j < imageCount; j++) {
//                    fileImage = new File("src/main/resources/images/" + reviewImage[j % reviewImage.length]);
//
//                    review.addReviewImage(uploadFile(fileImage));
//                }

                market.addReview(review);
            }
            marketRepository.save(market);
        }

        settingInitData();
    }
}
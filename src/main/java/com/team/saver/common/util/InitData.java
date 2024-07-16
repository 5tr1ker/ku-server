package com.team.saver.common.init;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.team.saver.account.entity.Account;
import com.team.saver.account.entity.UserRole;
import com.team.saver.account.repository.AccountRepository;
import com.team.saver.attraction.promotion.entity.Promotion;
import com.team.saver.attraction.promotion.entity.PromotionTag;
import com.team.saver.attraction.promotion.entity.PromotionTagRelationShip;
import com.team.saver.attraction.promotion.repository.PromotionRepository;
import com.team.saver.attraction.promotion.repository.PromotionTagRepository;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.market.coupon.entity.Coupon;
import com.team.saver.market.review.entity.Review;
import com.team.saver.market.review.entity.ReviewRecommender;
import com.team.saver.market.store.entity.MainCategory;
import com.team.saver.market.store.entity.Market;
import com.team.saver.market.store.entity.Menu;
import com.team.saver.market.store.repository.MarketRepository;
import com.team.saver.market.store.util.RecommendAlgorithm;
import com.team.saver.oauth.util.OAuthType;
import com.team.saver.search.autocomplete.service.AutoCompleteService;
import com.team.saver.search.autocomplete.util.Trie;
import com.team.saver.search.elasticsearch.market.document.MarketDocument;
import com.team.saver.search.elasticsearch.market.repository.MarketDocumentRepository;
import com.team.saver.search.popular.util.SearchWordScheduler;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
class AttractionData {

    String title;

    String introduce;

    String tags[];

    String imageName;
}

@Component
@RequiredArgsConstructor
public class InitData implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final MarketRepository marketRepository;
    private final RecommendAlgorithm recommendAlgorithm;
    private final Trie trie;
    private final AutoCompleteService autoCompleteService;
    private final SearchWordScheduler searchWordScheduler;
    private final PromotionRepository promotionRepository;
    private final PromotionTagRepository promotionTagRepository;
    private final AmazonS3Client amazonS3Client;
    private final MarketDocumentRepository marketDocumentRepository;

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
                .profileImage(image_url_account_1)
                .role(UserRole.STUDENT)
                .oAuthType(OAuthType.KAKAO)
                .build();

        accountRepository.save(account);

        File fileImage_account_2 = new File("src/main/resources/images/profile-1.png");
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

        List<StoreData> storeData = new ArrayList<>();

        ReviewData review1_1 = new ReviewData("맛은 평범한 듯 맛있어요. 깨끗한 기름에 튀긴 것이 느껴지고 꽈리고추, 떡, 닭똥집, 고구마 등도 함께 튀겨나오는데 구성이 혜자네요! 잘 먹었습니다^^", 4, "chicken1.png" , "chicken23.png");
        ReviewData review1_2 = new ReviewData("성시경 맛집이라는 곳이네여 줄 짧아서 먹어봤는데.. 흠 제가 치킨류 알못이라 (미쳐있는 정도가 아니라;;) 그런지 그냥 치킨 맛이었구요 그냥 고추튀김이 맛있어요", 3, "chicken2.png" , "chicken24.png" , "chicken25.png");
        ReviewData review1_3 = new ReviewData("옛날 시장통닭 맛이 좋습니다. 오랜 노포 느낌의 가게 분위기가 치킨과 잘 어울립니다. 혼자 주문쳐내시는 젊은 여자분, 작은 사장님이신가 ? 는 모르겠는데 일당백의 일처리에 멋있었습니다", "chicken3.png", 4);
        ReviewData review1_4 = new ReviewData("효도치킨 한남동 처음 생겼을때부터 다녔는데, 순살치킨이 강정마냥 작아지고 전 같지 않네요..", "chicken16.png", 2);
        ReviewData review1_5 = new ReviewData("맛있는 치킨과 꽈리고추와 멸치의 궁합이 좋아 계속 들어갔던 곳 !!", 5, "chicken17.png" , "chicken18.png" , "chicken19.png" , "chicken20.png");
        ReviewData review1_6 = new ReviewData("불친절하다는 후기를 많이봤는데 유명한 곳이라 한번 가봄. 멸치 엄청 바삭거리고 치킨도 전체적으로 간이 빡-!!!!! 짜서 무조건 콜라는 마셔야할 것 같음. ", 4, "chicken21.png" , "chicken22.png");
        storeData.add(new StoreData("짱돌", "치킨.안주.주류", "Rectangle 2208.png", "첫 구매시 3,000원", Arrays.asList(review1_1, review1_2, review1_3, review1_4, review1_5, review1_6)));

        ReviewData review2_1 = new ReviewData("비주얼은 좋은데 맛아…", "pizza1.png", 4);
        ReviewData review2_2 = new ReviewData("피자 - 도우가 정말 쫄깃하다, 가래떡 먹는 느낌 치즈 포함 토핑들이 신선하고 좋은 재료들로 만들었다는 느낌이 확실하게 듦 맛있게 담백해서 더부룩하지 않고 기분좋은 포만감이 든다", "pizza2.png", 5);
        ReviewData review2_3 = new ReviewData("웨이팅없이 바로들어가서 좋았습니다. 피자 파스타 둘다 그럭저럭 맛있습니다. 피자 데우는 초가 금방꺼져서 마지막엔 식어서 아쉽습니다", "pizza3.png", 3);
        ReviewData review2_4 = new ReviewData("예약하고 방문하세요! 나오는데 오래걸립니다아 맛있고 맥주랑 잘어울림다", "chicken5.png", 3);
        ReviewData review2_5 = new ReviewData("너무 맛있게 잘 먹었어요! 비빔국수랑 누룽지통닭이랑 조합이 짱입니다! 비빔국수 안 시켰으면 후회 할 뻔 했어요", "chicken6.png", 5);
        storeData.add(new StoreData("피자나라 치킨공주", "치킨.피자.안주.주류", "Rectangle 2269.png", "리뷰 작성 시 음료 1개 무료 증정 이벤트 진행 중!", Arrays.asList(review2_1, review2_2, review2_3, review2_4, review2_5)));

        ReviewData review3_1 = new ReviewData("오랜만에 장작집이 생각나서 다녀왓습니다. 전기구이와 비교할수없는 장작구이만의 맛은 여전하네요 ~ 맛있게 잘 먹고 갑니다", "chicken4.png", 5);
        storeData.add(new StoreData("태권치킨", "치킨.안주.주류", "Rectangle 2270.png", "선착순 100명 10% 할인 쿠폰 증정 이벤트 진행 중!", Arrays.asList(review3_1)));

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
                for(int i = 0; i < recommenderCount; i++) {
                    ReviewRecommender reviewRecommender = ReviewRecommender.builder()
                            .review(review)
                            .account(account)
                            .build();

                    review.addRecommender(reviewRecommender);
                }

                market.addReview(review);
            }

            marketRepository.save(market);
            autoCompleteService.addSearchWord(market.getMarketName());
            marketDocumentRepository.save(MarketDocument.createEntity(market));
        }

        // 관광 명소
        List<AttractionData> attractionData = new ArrayList<>();
        attractionData.add(new AttractionData("스카이캡슐", "스카이캡슐\n반값으로 입장하기", new String[]{"부산", "관광지"}, "Rectangle 2436.png"));
        attractionData.add(new AttractionData("튤립정원", "튤립정원\n축제 학생할인 받는 방법", new String[]{"튤립", "인생사진"}, "Rectangle 2437.png"));
        attractionData.add(new AttractionData("양떼목장", "양떼목장\n체험해보고 싶다면?", new String[]{"양", "목장체험"}, "Rectangle 2439.png"));
        attractionData.add(new AttractionData("보트체험", "데이트하기 좋은\n호숫가 위 보트체험", new String[]{"호수", "데이트명소"}, "Rectangle 2440.png"));

        for (AttractionData data : attractionData) {
            File fileImage = new File("src/main/resources/images/" + data.imageName);
            String imageUrl = uploadFile(fileImage);

            Promotion promotion = Promotion.builder()
                    .introduce(data.introduce)
                    .imageUrl(imageUrl)
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

        settingInitData();
    }
}
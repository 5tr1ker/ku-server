package com.team.saver.market.review.service;

import com.team.saver.account.entity.Account;
import com.team.saver.account.service.AccountService;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.market.order.entity.Order;
import com.team.saver.market.order.repository.OrderRepository;
import com.team.saver.market.review.dto.*;
import com.team.saver.market.review.entity.Review;
import com.team.saver.market.review.entity.ReviewRecommender;
import com.team.saver.market.review.repository.ReviewImageRepository;
import com.team.saver.market.review.repository.ReviewRecommenderRepository;
import com.team.saver.market.review.repository.ReviewRepository;
import com.team.saver.market.store.entity.Market;
import com.team.saver.market.store.repository.MarketRepository;
import com.team.saver.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.team.saver.common.dto.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MarketRepository marketRepository;
    private final AccountService accountService;
    private final OrderRepository orderRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final S3Service s3Service;
    private final ReviewRecommenderRepository reviewRecommenderRepository;

    public List<ReviewResponse> findByMarketId(CurrentUser currentUser,
                                               long marketId,
                                               SortType sortType,
                                               Pageable pageable) {
        return reviewRepository.findByMarketId(currentUser.getEmail() , marketId, sortType, pageable);
    }

    public List<ReviewResponse> findByUserEmail(CurrentUser currentUser) {
        return reviewRepository.findByUserEmail(currentUser.getEmail());
    }

    public ReviewResponse findDetailByReviewId(CurrentUser currentUser, long reviewId) {
        return reviewRepository.findDetailByReviewId(currentUser.getEmail(), reviewId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_REVIEW));
    }

    @Transactional
    public void updateReview(CurrentUser currentUser, long reviewId, ReviewUpdateRequest request, List<MultipartFile> images) {
        Review review = reviewRepository.findByReviewerAndReviewId(currentUser.getEmail(), reviewId)
                .orElseThrow(() -> new CustomRuntimeException(ONLY_UPDATE_WRITER));

        if(images != null) {
            addReviewImage(review, images);
        }
        reviewImageRepository.deleteById(request.getRemoveImageIds());

        review.update(request);
    }

    public List<PhotoReviewResponse> findAllReviewImageByMarketId(long marketId, Long size) {
        if(size == null) {
            return reviewRepository.findAllReviewImageByMarketId(marketId);
        }

        return reviewRepository.findAllReviewImageByMarketId(marketId, size);
    }

    @Transactional
    public void addReview(CurrentUser currentUser, long marketId, ReviewCreateRequest request , List<MultipartFile> images) {
        Market market = marketRepository.findById(marketId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_MARKET));
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_ORDER));
        Account account = accountService.getProfile(currentUser);

        if(reviewRepository.findByOrderAndMarket(order, market).isPresent()) {
            throw new CustomRuntimeException(IS_EXISTS_REVIEW);
        }
        Review review = Review.createEntity(account, request, order);

        addReviewImage(review, images);
        market.addReview(review);
    }

    private void addReviewImage(Review review, List<MultipartFile> images) {
        for(MultipartFile multipartFile : images) {
            String imageUrl = s3Service.uploadImage(multipartFile);

            review.addReviewImage(imageUrl);
        }
    }

    @Transactional
    public void deleteReview(CurrentUser currentUser, long reviewId) {
        Review review = reviewRepository.findByReviewerAndReviewId(currentUser.getEmail(), reviewId)
                .orElseThrow(() -> new CustomRuntimeException(ONLY_DELETE_WRITER));

        review.delete();
    }

    public ReviewStatisticResponse findReviewStatisticsByEmail(CurrentUser currentUser) {
        return reviewRepository.findReviewStatisticsByEmail(currentUser.getEmail())
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_USER));
    }

    @Transactional(noRollbackFor = CustomRuntimeException.class)
    public void recommendReview(CurrentUser currentUser, long reviewId) {
        List<ReviewRecommender> result = reviewRepository.findRecommenderByEmailAndReviewId(currentUser.getEmail(), reviewId);
        if(result.size() != 0) {
            reviewRecommenderRepository.deleteAll(result);

            throw new CustomRuntimeException(CANCEL_RECOMMENDER);
        }

        Account account = accountService.getProfile(currentUser);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_REVIEW));

        review.addRecommender(ReviewRecommender.createEntity(account, review));
    }

    public List<ReviewResponse> findBestReview(CurrentUser currentUser, long marketId, Pageable pageable) {
        return reviewRepository.findBestReview(currentUser.getEmail(), marketId, pageable);
    }

    public List<ReviewResponse> findRandomBestReview(CurrentUser currentUser, long minimum, Pageable pageable) {
        return reviewRepository.findRandomBestReview(currentUser.getEmail(), minimum, pageable);
    }

    public ReviewStatisticsResponse findReviewStatisticsByMarketId(long marketId) {
        return reviewRepository.findReviewStatisticsByMarketId(marketId);
    }

    public long findPhotoReviewCountByMarketId(long marketId) {
        return reviewRepository.findPhotoReviewCountByMarketId(marketId);
    }
}

package com.team.saver.market.review.service;

import com.team.saver.account.entity.Account;
import com.team.saver.account.service.AccountService;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.market.review.dto.*;
import com.team.saver.market.review.entity.Review;
import com.team.saver.market.review.entity.ReviewRecommender;
import com.team.saver.market.review.repository.ReviewRepository;
import com.team.saver.market.store.entity.Market;
import com.team.saver.market.store.repository.MarketRepository;
import com.team.saver.s3.service.S3Service;
import jakarta.mail.Multipart;
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
    private final S3Service s3Service;

    public List<ReviewResponse> findByMarketId(long marketId, SortType sortType) {
        return reviewRepository.findByMarketId(marketId, sortType.getFirstOrderSpecifier(), sortType.getSecondOrderSpecifier());
    }

    public List<ReviewResponse> findByUserEmail(CurrentUser currentUser) {
        return reviewRepository.findByUserEmail(currentUser.getEmail());
    }

    @Transactional
    public void updateReview(CurrentUser currentUser, long reviewId, ReviewUpdateRequest request) {
        Review review = reviewRepository.findByReviewerAndReviewId(currentUser.getEmail(), reviewId)
                .orElseThrow(() -> new CustomRuntimeException(ONLY_UPDATE_WRITER));

        review.update(request);
    }

    @Transactional
    public void addReview(CurrentUser currentUser, long marketId, ReviewRequest request , List<MultipartFile> images) {
        Market market = marketRepository.findById(marketId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_MARKET));

        Account account = accountService.getProfile(currentUser);
        Review review = Review.createEntity(account, request);

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

        reviewRepository.delete(review);
    }

    @Transactional
    public void recommendReview(CurrentUser currentUser, long reviewId) {
        if(reviewRepository.findRecommenderByEmailAndReviewId(currentUser.getEmail(), reviewId).isPresent()) {
            throw new CustomRuntimeException(EXIST_RECOMMENDER);
        };

        Account account = accountService.getProfile(currentUser);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_REVIEW));

        review.addRecommender(ReviewRecommender.createEntity(account, review));
    }

    public List<ReviewResponse> findBestReview(Pageable pageable) {
        return reviewRepository.findBestReview(pageable);
    }

    public ReviewStatisticsResponse findReviewStatisticsByMarketId(long marketId) {
        return reviewRepository.findReviewStatisticsByMarketId(marketId);
    }
}

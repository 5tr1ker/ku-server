package com.team.saver.market.review.service;

import com.team.saver.account.entity.Account;
import com.team.saver.account.service.AccountService;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.market.review.dto.ReviewRequest;
import com.team.saver.market.review.dto.ReviewResponse;
import com.team.saver.market.review.entity.Review;
import com.team.saver.market.review.repository.ReviewRepository;
import com.team.saver.market.store.entity.Market;
import com.team.saver.market.store.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.team.saver.common.dto.ErrorMessage.NOT_FOUND_MARKET;
import static com.team.saver.common.dto.ErrorMessage.ONLY_DELETE_WRITER;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MarketRepository marketRepository;
    private final AccountService accountService;

    public List<ReviewResponse> findReviewByMarketId(long marketId) {
        return reviewRepository.findReviewByMarketId(marketId);
    }

    @Transactional
    public void addReview(long marketId, CurrentUser currentUser, ReviewRequest request) {
        Market market = marketRepository.findById(marketId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_MARKET));

        Account account = accountService.getProfile(currentUser);
        Review review = Review.createReview(account, request);

        market.addReview(review);
    }

    @Transactional
    public void deleteReview(CurrentUser currentUser, long reviewId) {
        Review review = reviewRepository.findByReviewerAndReviewId(currentUser.getEmail(), reviewId)
                .orElseThrow(() -> new CustomRuntimeException(ONLY_DELETE_WRITER));

        reviewRepository.delete(review);
    }

}

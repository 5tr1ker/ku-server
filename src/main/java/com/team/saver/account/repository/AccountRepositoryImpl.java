package com.team.saver.account.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.account.dto.MyPageResponse;
import com.team.saver.account.entity.QAccount;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.querydsl.jpa.JPAExpressions.select;
import static com.team.saver.account.entity.QAccount.account;
import static com.team.saver.market.favorite.entity.QFavorite.favorite;
import static com.team.saver.market.coupon.entity.QCoupon.coupon;
import static com.team.saver.market.coupon.entity.QDownloadCoupon.downloadCoupon;

@RequiredArgsConstructor
public class AccountRepositoryImpl implements CustomAccountRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<MyPageResponse> getMyPageInfo(String email, long userExp, long userLevel) {
        QAccount account1 = new QAccount("account1");
        QAccount account2 = new QAccount("account2");
        final NumberPath userExpPath = Expressions.numberPath(Integer.class, String.valueOf(userExp));
        final NumberPath userLevelPath = Expressions.numberPath(Integer.class, String.valueOf(userLevel));

        MyPageResponse result = jpaQueryFactory.select(
                        Projections.constructor(MyPageResponse.class,
                                account.accountId,
                                account.email,
                                account.profileImage,
                                userLevelPath,
                                userExpPath,
                                userExpPath.subtract(account.usePoint),
                                select(favorite.count()).from(favorite).innerJoin(favorite.account, account2).on(account2.eq(account)),
                                select(coupon.count()).from(downloadCoupon).innerJoin(downloadCoupon.account, account1).on(account1.eq(account))
                        )
                ).from(account)
                .where(account.email.eq(email))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}

package com.team.saver.market.deliveryaddress.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.market.deliveryaddress.dto.DeliveryAddressResponse;
import com.team.saver.market.deliveryaddress.entity.DeliveryAddress;
import com.team.saver.market.deliveryaddress.entity.QDeliveryAddress;
import lombok.RequiredArgsConstructor;

import static com.team.saver.account.entity.QAccount.account;
import static com.team.saver.market.deliveryaddress.entity.QDeliveryAddress.deliveryAddress;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DeliveryAddressRepositoryImpl implements CustomDeliveryAddressRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<DeliveryAddress> findByEmailAndId(String email, long deliveryAddressId) {
        DeliveryAddress result = jpaQueryFactory.select(deliveryAddress)
                .from(deliveryAddress)
                .innerJoin(deliveryAddress.account, account).on(account.email.eq(email))
                .where(deliveryAddress.deliveryAddressId.eq(deliveryAddressId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<DeliveryAddressResponse> findDetailByEmailAndId(String email, long deliveryAddressId) {
        QDeliveryAddress deliveryAddress2 = new QDeliveryAddress("deliveryAddress2");

        DeliveryAddressResponse result = jpaQueryFactory.select(
                        Projections.constructor(
                                DeliveryAddressResponse.class,
                                deliveryAddress.deliveryAddressId,
                                deliveryAddress.name,
                                deliveryAddress.zipCode,
                                deliveryAddress.address,
                                deliveryAddress.detailAddress,
                                deliveryAddress.phone,
                                deliveryAddress.eq(deliveryAddress2)
                        )
                )
                .from(account)
                .innerJoin(account.deliveryAddresses , deliveryAddress).on(deliveryAddress.deliveryAddressId.eq(deliveryAddressId))
                .leftJoin(account.defaultDeliveryAddress, deliveryAddress2)
                .where(account.email.eq(email))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<DeliveryAddressResponse> findDetailByEmail(String email) {
        QDeliveryAddress deliveryAddress2 = new QDeliveryAddress("deliveryAddress2");
        BooleanExpression isDefaultAddress = deliveryAddress.eq(deliveryAddress2);
        NumberTemplate<Integer> isDefaultAddressAsNumber = Expressions.numberTemplate(Integer.class, "case when {0} then 1 else 0 end", isDefaultAddress);

        return jpaQueryFactory.select(
                        Projections.constructor(
                                DeliveryAddressResponse.class,
                                deliveryAddress.deliveryAddressId,
                                deliveryAddress.name,
                                deliveryAddress.zipCode,
                                deliveryAddress.address,
                                deliveryAddress.detailAddress,
                                deliveryAddress.phone,
                                isDefaultAddress
                        )
                )
                .from(account)
                .innerJoin(account.deliveryAddresses , deliveryAddress)
                .leftJoin(account.defaultDeliveryAddress, deliveryAddress2)
                .orderBy(isDefaultAddressAsNumber.desc(), deliveryAddress.deliveryAddressId.desc())
                .where(account.email.eq(email))
                .fetch();
    }
}

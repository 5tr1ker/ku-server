package com.team.saver.market.deliveryaddress.repository;

import com.team.saver.market.deliveryaddress.dto.DeliveryAddressResponse;
import com.team.saver.market.deliveryaddress.entity.DeliveryAddress;

import java.util.List;
import java.util.Optional;

public interface CustomDeliveryAddressRepository {

    Optional<DeliveryAddress> findByEmailAndId(String email, long deliveryAddressId);

    Optional<DeliveryAddressResponse> findDetailByEmailAndId(String email, long deliveryAddressId);

    List<DeliveryAddressResponse> findDetailByEmail(String email);

}

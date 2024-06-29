package com.team.saver.market.deliveryaddress.repository;

import com.team.saver.market.deliveryaddress.entity.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long>, CustomDeliveryAddressRepository {

}

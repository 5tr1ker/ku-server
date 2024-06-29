package com.team.saver.market.deliveryaddress.service;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.market.deliveryaddress.dto.DeliveryAddressCreateRequest;
import com.team.saver.market.deliveryaddress.dto.DeliveryAddressUpdateRequest;
import com.team.saver.market.deliveryaddress.repository.DeliveryAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryAddressService {

    private final DeliveryAddressRepository deliveryAddressRepository;

    public void addDeliveryAddress(CurrentUser currentUser, DeliveryAddressCreateRequest request) {

    }

    public void updateDeliveryAddress(CurrentUser currentUser, DeliveryAddressUpdateRequest request) {

    }

    public void deleteDeliveryAddress(CurrentUser currentUser, long deliveryAddressId) {

    }

    public void updateDefaultDeliveryAddress(CurrentUser currentUser, long deliveryAddressId) {

    }

}

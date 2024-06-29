package com.team.saver.market.deliveryaddress.service;

import com.team.saver.account.entity.Account;
import com.team.saver.account.service.AccountService;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.ErrorMessage;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.market.deliveryaddress.dto.DeliveryAddressCreateRequest;
import com.team.saver.market.deliveryaddress.dto.DeliveryAddressResponse;
import com.team.saver.market.deliveryaddress.dto.DeliveryAddressUpdateRequest;
import com.team.saver.market.deliveryaddress.entity.DeliveryAddress;
import com.team.saver.market.deliveryaddress.repository.DeliveryAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.team.saver.common.dto.ErrorMessage.NOT_FOUND_DELIVERY_ADDRESS;

@Service
@RequiredArgsConstructor
public class DeliveryAddressService {

    private final DeliveryAddressRepository deliveryAddressRepository;
    private final AccountService accountService;

    @Transactional
    public void addDeliveryAddress(CurrentUser currentUser, DeliveryAddressCreateRequest request) {
        Account account = accountService.getProfile(currentUser);

        DeliveryAddress deliveryAddress = deliveryAddressRepository.save(DeliveryAddress.createEntity(request));
        account.addDeliveryAddress(deliveryAddress);

        System.out.println(request.toString());
        if(request.isDefaultAddress()) {
            account.setDefaultDeliveryAddress(deliveryAddress);
        }

    }

    @Transactional
    public void updateDeliveryAddress(CurrentUser currentUser, long deliveryAddressId, DeliveryAddressUpdateRequest request) {
        DeliveryAddress deliveryAddress = deliveryAddressRepository.findByEmailAndId(currentUser.getEmail(), deliveryAddressId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_DELIVERY_ADDRESS));

        deliveryAddress.update(request);

        if(request.isDefaultAddress()) {
            Account account = accountService.getProfile(currentUser);

            account.setDefaultDeliveryAddress(deliveryAddress);
        }
    }

    @Transactional
    public void deleteDeliveryAddress(CurrentUser currentUser, long deliveryAddressId) {
        DeliveryAddress deliveryAddress = deliveryAddressRepository.findByEmailAndId(currentUser.getEmail(), deliveryAddressId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_DELIVERY_ADDRESS));

        deliveryAddressRepository.delete(deliveryAddress);
    }

    public List<DeliveryAddressResponse> findDeliveryAddress(CurrentUser currentUser) {
        return deliveryAddressRepository.findDetailByEmail(currentUser.getEmail());
    }

    public DeliveryAddressResponse findDeliveryAddressById(CurrentUser currentUser, long deliveryAddressId) {
        return deliveryAddressRepository.findDetailByEmailAndId(currentUser.getEmail(), deliveryAddressId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_DELIVERY_ADDRESS));
    }

    @Transactional
    public void updateDefaultDeliveryAddress(CurrentUser currentUser, long deliveryAddressId) {
        Account account = accountService.getProfile(currentUser);
        DeliveryAddress deliveryAddress = deliveryAddressRepository.findByEmailAndId(account.getEmail(), deliveryAddressId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_DELIVERY_ADDRESS));

        account.setDefaultDeliveryAddress(deliveryAddress);
    }


}

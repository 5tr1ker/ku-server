package com.team.saver.market.deliveryaddress.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeliveryAddressResponse {

    private long deliveryAddressId;

    private String name;

    private String zipCode;

    private String address;

    private String detailAddress;

    private String phone;

    private long isDefaultDeliveryAddress;

}

package com.team.saver.market.deliveryaddress.dto;

import lombok.Getter;

@Getter
public class DeliveryAddressUpdateRequest {

    private String name;

    private String zipCode;

    private String address;

    private String detailAddress;

    private String phone;

}

package com.team.saver.market.deliveryaddress.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DeliveryAddressCreateRequest {

    private String name;

    private String zipCode;

    private String address;

    private String detailAddress;

    private String phone;

    private boolean defaultAddress;

}

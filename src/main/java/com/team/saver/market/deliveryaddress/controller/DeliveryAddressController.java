package com.team.saver.market.deliveryaddress.controller;

import com.team.saver.market.deliveryaddress.service.DeliveryAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeliveryAddressController {

    private final DeliveryAddressService deliveryAddressService;

}

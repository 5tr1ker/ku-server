package com.team.saver.partner.request.dto;

import lombok.Getter;

@Getter
public class PartnerRequestUpdateRequest {

    private String requestMarketName;

    private String marketAddress;

    private String detailAddress;

    private String phoneNumber;

    private String title;

    private String description;

    private double locationX;

    private double locationY;

}

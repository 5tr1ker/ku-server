package com.team.saver.partner.request.dto;

import jakarta.persistence.Column;
import lombok.Getter;

@Getter
public class PartnerRequestCreateRequest {

    private String requestMarketName;

    private String marketAddress;

    private String detailAddress;

    private String phoneNumber;

    private String title;

    private String description;

    private double locationX;

    private double locationY;

}

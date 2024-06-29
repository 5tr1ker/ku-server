package com.team.saver.market.deliveryaddress.entity;

import com.team.saver.account.entity.Account;
import com.team.saver.market.deliveryaddress.dto.DeliveryAddressCreateRequest;
import com.team.saver.market.deliveryaddress.dto.DeliveryAddressUpdateRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DeliveryAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long deliveryAddressId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String detailAddress;

    @Column(nullable = false)
    private String phone;

    public static DeliveryAddress createEntity(DeliveryAddressCreateRequest request) {
        return DeliveryAddress.builder()
                .name(request.getName())
                .zipCode(request.getZipCode())
                .address(request.getAddress())
                .detailAddress(request.getDetailAddress())
                .phone(request.getPhone())
                .build();
    }

    public void update(DeliveryAddressUpdateRequest request) {
        this.name = request.getName();
        this.zipCode = request.getZipCode();
        this.address = request.getAddress();
        this.detailAddress = request.getDetailAddress();
        this.phone = getPhone();
    }

    @Override
    public boolean equals(Object deliveryAddress) {
        DeliveryAddress entity = (DeliveryAddress) deliveryAddress;
        if(entity.deliveryAddressId == this.deliveryAddressId) {
            return true;
        }

        return false;
    }
}

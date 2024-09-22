package com.team.saver.account.entity;

import com.team.saver.account.dto.AccountNotificationSetting;
import com.team.saver.account.dto.AccountUpdateRequest;
import com.team.saver.market.deliveryaddress.entity.DeliveryAddress;
import com.team.saver.oauth.dto.AccountInfo;
import com.team.saver.oauth.util.OAuthType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountId;

    @Column(nullable = false)
    private String email;

    private String schoolEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private School school;

    private String phone;

    private String age;

    private String name;

    @Setter
    private String profileImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OAuthType oAuthType;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDate joinDate;

    @Column(nullable = false)
    private LocalDate lastedLoginDate;

    @Column(nullable = false)
    private long loginCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    private DeliveryAddress defaultDeliveryAddress;

    @Builder.Default
    @OneToMany(mappedBy = "account", cascade = { CascadeType.PERSIST })
    private List<DeliveryAddress> deliveryAddresses = new ArrayList<>();

    @Builder.Default
    @Column(nullable = false)
    private long usePoint = 0;

    @Builder.Default
    @Column(nullable = false)
    private boolean autoUpdate = true;

    @Builder.Default
    @Column(nullable = false)
    private boolean pushAlert = true;

    @Builder.Default
    @Column(nullable = false)
    private boolean autoLogin = false;

    @Builder.Default
    @Column(nullable = false)
    private boolean isDelete = false;

    public static Account createEntity(AccountInfo accountInfo, OAuthType type, String publicUrl) {
        return Account.builder()
                .email(accountInfo.getEmail())
                .phone(accountInfo.getPhone())
                .age(accountInfo.getAge())
                .name(accountInfo.getName())
                .school(School.EMPTY)
                .profileImage(publicUrl + "/images/default.png")
                .loginCount(1)
                .lastedLoginDate(LocalDate.now())
                .oAuthType(type)
                .role(UserRole.PARTNER)
                .build();
    }

    public void addDeliveryAddress(DeliveryAddress deliveryAddress) {
        deliveryAddress.setAccount(this);
        this.deliveryAddresses.add(deliveryAddress);
    }

    public void delete() {
        this.isDelete = true;
    }

    public void updateRoleToStudent() {
        this.role = UserRole.STUDENT;
    }

    public void updateRoleToPartner() {
        role = UserRole.PARTNER;
    }

    public void updateRoleToAdmin() {
        role = UserRole.ADMIN;
    }

    public void updateLastedLoginDate() {
        this.lastedLoginDate = LocalDate.now();
        this.loginCount += 1;
    }

    public void updateSchoolEmail(String schoolEmail) {
        this.schoolEmail = schoolEmail;
    }

    public void updateSchool(School school) {
        this.school = school;
    }

    public void transferAccountInfo(Account account) {
        this.oAuthType = account.getOAuthType();
        this.email = account.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.getRole()));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void update(AccountUpdateRequest request) {
        this.phone = request.getPhone();
        this.age = request.getAge();
        this.name = request.getName();
    }

    public void updateSetting(AccountNotificationSetting request) {
        this.pushAlert = request.isPushAlert();
        this.autoUpdate = request.isAutoUpdate();
        this.autoLogin = request.isAutoLogin();
    }
}

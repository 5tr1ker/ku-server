package com.team.saver.account.entity;

import com.team.saver.oauth.dto.AccountInfo;
import com.team.saver.oauth.util.OAuthType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

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

    private String phone;

    private String age;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OAuthType oAuthType;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDate joinDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    public static Account createEntity(AccountInfo accountInfo, OAuthType type) {
        return Account.builder()
                .email(accountInfo.getEmail())
                .phone(accountInfo.getPhone())
                .age(accountInfo.getAge())
                .name(accountInfo.getName())
                .oAuthType(type)
                .role(UserRole.NORMAL)
                .build();
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
}

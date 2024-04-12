package com.team.saver.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .requestMatchers(HttpMethod.POST ,"/market/search" , "/market/search/**").permitAll()
                .requestMatchers("/account/sign-up" , "/account/sign-in" , "/mail/**" , "/account/oauth/sign-in" ,"/partner/request").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/**").hasRole("NORMAL");

        return http.build();
    }

}

package com.team.saver.security.jwt.support;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = getTokenFromCookie(request);

        if (token != null && jwtTokenProvider.validateAccessToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

    public static String getTokenFromCookie(ServletRequest request) {
        Cookie cookies[] = ((HttpServletRequest) request).getCookies();

        if(cookies != null) {
            return Arrays.stream(cookies)
                    .filter(c -> c.getName().equals("accessToken")).findFirst().map(Cookie::getValue)
                    .orElse(null);
        }

        return null;
    }

}

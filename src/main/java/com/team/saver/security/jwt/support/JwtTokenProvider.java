package com.team.saver.security.jwt.support;

import com.team.saver.account.entity.Account;
import com.team.saver.account.entity.UserRole;
import com.team.saver.account.repository.AccountRepository;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.security.jwt.dto.Token;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

import static com.team.saver.common.dto.ErrorMessage.NOT_FOUND_USER;
import static com.team.saver.common.dto.ErrorMessage.TAMPERED_TOKEN;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserDetailsService loginService;
    private final AccountRepository accountRepository;
    private long tokenValidTime = 30 * 60 * 1000L; // 30 minutes
    private String secretKey = "FZ4617yUKJK5935th5Tyh5hs4GHS45";
    private final String DOMAIN_URL = "LOCALHOST";

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = loginService.loadUserByUsername(this.getUserPk(token));
        if (userDetails == null) return null;

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String getUserPkIgnoreExpire(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
    }

    public Token reissueToken(HttpServletResponse response, HttpServletRequest request) {
        String token = getTokenFromCookie(request);
        String userPk = getUserPkIgnoreExpire(token);
        Account account = accountRepository.findByEmail(userPk)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_USER));

        return login(response, account.getEmail(), account.getRole());
    }

    public Token login(HttpServletResponse response, String userPk, UserRole roles) {
        Token token = createJwtToken(userPk, roles);

        addJwtCookieAtResponse(response, token);

        return token;
    }

    public String getTokenFromCookie(ServletRequest request) {
        Cookie cookies[] = ((HttpServletRequest) request).getCookies();

        if(cookies != null) {
            return Arrays.stream(cookies)
                    .filter(c -> c.getName().equals("accessToken")).findFirst().map(Cookie::getValue)
                    .orElse(null);
        }

        return null;
    }

    public Token createJwtToken(String userPk, UserRole roles) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);

        String accessToken = createAccessToken(claims);

        return Token.builder().accessToken(accessToken).key(userPk).build();
    }

    public void addJwtCookieAtResponse(HttpServletResponse response, Token token) {
        String accessToken = token.getAccessToken();

        setAccessTokenCookie(response, accessToken, 30 * 60 * 1000L);
    }

    public void deleteJwtCookieFromResponse(HttpServletResponse response) {
        setAccessTokenCookie(response, null, 0);
    }

    private void setAccessTokenCookie(HttpServletResponse response, String value, long maxAge) {
        ResponseCookie cookie = ResponseCookie.from("accessToken" , value)
                .path("/")
                .maxAge(maxAge)
                //.secure(true)
                //.domain(DOMAIN_URL)
                //.httpOnly(true)
                //.sameSite("none")
                .build();

        response.addHeader("Set-Cookie" , cookie.toString());
    }

    public boolean validateAccessToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return false;
        } catch (SignatureException e) {
            throw new CustomRuntimeException(TAMPERED_TOKEN);
        }
    }

    private String createAccessToken(Claims claims) {
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

}

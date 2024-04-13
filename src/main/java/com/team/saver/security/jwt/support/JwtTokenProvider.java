package com.team.saver.security.jwt.support;

import com.team.saver.account.entity.UserRole;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.security.jwt.dto.Token;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

import static com.team.saver.common.dto.ErrorMessage.TAMPERED_TOKEN;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserDetailsService loginService;

    private long tokenValidTime = 30 * 60 * 1000L; // 30 minutes
    private String secretKey = "FZ4617yUKJK5935th5Tyh5hs4GHS45";

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

    public Token createJwtToken(String userPk, UserRole roles) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);

        String accessToken = createAccessToken(claims);

        return Token.builder().accessToken(accessToken).key(userPk).build();
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

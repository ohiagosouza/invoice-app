package com.hiagosouza.api.quoted.security;


import com.hiagosouza.api.quoted.enums.PlanType;
import com.hiagosouza.api.quoted.enums.UserRole;
import com.hiagosouza.api.quoted.model.UserModel;
import com.hiagosouza.api.quoted.services.impl.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtils {
    @Value("${JWT_SECRET_KEY}")
    private String secretKey;

    private Key signingKey;

    private UserService userService;

    public JwtUtils(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public Key getSigningKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + TimeUnit.HOURS.toMillis(1));
        UserModel user = userService.findByEmail(email);
        PlanType userPlanType = user.getPlanType();
        List<UserRole> userRoles = user.getUserRoles();

        return Jwts.builder()
                .subject(email)
                .claim("roles", userRoles)
                .claim("planType", userPlanType)
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(getSigningKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public String extractUsername(String token) {
        try {
            return Jwts.parser()
                    .verifyWith((SecretKey) getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (Exception e) {
            throw new RuntimeException("***** JWT token validation failed *****");
        }

    }

    public boolean isTokenExpired(String token) {
        try {
            Date expiration = Jwts.parser()
                    .verifyWith((SecretKey) getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}


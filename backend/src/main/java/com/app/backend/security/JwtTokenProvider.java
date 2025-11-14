package com.app.backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;

@Component

public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private long jwtExpiration;

    private SecretKey getSigniKey(){
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
        .subject(username)
        .issuedAt(now)
        .expiration(expiryDate)
        .signWith(getSigniKey())
        .compact();
    }

    public String getUsernameFromToken(String token){
        Claims claims = Jwts.parser()
        .verifyWith(getSigniKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();

        return claims.getSubject();
    }
    public boolean validateToken(String authToken){
        try{
            Jwts.parser()
            .verifyWith(getSigniKey())
            .build()
            .parseSignedClaims(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }
}

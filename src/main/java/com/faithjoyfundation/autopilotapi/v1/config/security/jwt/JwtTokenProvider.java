package com.faithjoyfundation.autopilotapi.v1.config.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import static com.faithjoyfundation.autopilotapi.v1.config.security.jwt.JwtProperties.*;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;

    public JwtTokenProvider() {
        var secret = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // generate JWT token
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
       /* Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Claims claims = Jwts.claims()
                .add("authorities", authorities)
                .add("username", username)
                .build();*/

        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + EXPIRATION_TIME);


        return Jwts.builder()
                .subject(username)
                //.claims(claims)
                .issuedAt(new Date())
                .expiration(expireDate)
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    // extract username from JWT token
    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parse(token);
            return true;
        } catch (SignatureException | UnsupportedJwtException | MalformedJwtException | ExpiredJwtException |
                 IllegalArgumentException e) {
           return false;
        }
    }
}

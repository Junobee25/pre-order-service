package com.sideproject.preorderservice.util;

import com.sideproject.preorderservice.domain.entity.UserAccount;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

public class JwtTokenUtils {

    public static String extractEmail(String token, String key) {
        return extractClaim(token, key, Claims::getSubject);
    }

    public static <T> T extractClaim(String token, String key, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token, key);
        return claimResolver.apply(claims);
    }


    public static Claims extractAllClaims(String token, String key) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(key))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static String getEmail(String token, String key) {
        return extractAllClaims(token, key).get("email", String.class);
    }

    public static Boolean isTokenExpired(String token, String key) {
        Date expiration = extractAllClaims(token, key).getExpiration();
        return expiration.before(new Date());
    }

    public static Boolean isTokenValid(String token, String key, UserAccount userAccount) {
        final String email = extractEmail(token, key);
        return (email.equals(userAccount.getUsername())) && !isTokenExpired(token, key);
    }

    public static String generateToken(String email, String key, Long expiredTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("email", email);

        return buildToken(key, expiredTimeMs, claims);
    }

    public static String generateRefreshToken(String email, String key, Long expiredTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("email", email);

        return buildToken(key, expiredTimeMs, claims);
    }

    private static String buildToken(String key, Long expiredTimeMs, Claims claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs))
                .signWith(getKey(key), SignatureAlgorithm.HS256)
                .compact();
    }

    private static Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    private static Key getKey(String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

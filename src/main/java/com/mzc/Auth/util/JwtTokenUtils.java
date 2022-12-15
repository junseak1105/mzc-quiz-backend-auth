package com.mzc.Auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

// JwtToken 관리
public class JwtTokenUtils {

    public static String getHostEmail(String token, String key){
       return extractClaim(token, key).get("hostEmail", String.class);
    }

    public static boolean isExpired(String token, String key){
       Date expireDate = extractClaim(token,key).getExpiration();
       return expireDate.before(new Date());
    }

    private static Claims extractClaim(String token, String key){
        return Jwts.parserBuilder()
                .setSigningKey(getKey(key))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰 생성(HostEmail 생성, key 값 지정, 토큰 유효 시간 설정)
    public static String generateToken(String hostEmail, String key, long expiredTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("hostEmail", hostEmail);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis())) // 토큰 생성 시간
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs)) // 토큰 만료 시간
                .signWith(getKey(key), SignatureAlgorithm.HS256) // 여러 알고리즘 존재 -> hash 알고리즘 사용
                .compact();
    }

    // key 값 반환 메서드
    private static Key getKey(String key){
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}

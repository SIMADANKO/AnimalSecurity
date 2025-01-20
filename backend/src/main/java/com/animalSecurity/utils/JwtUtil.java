package com.animalSecurity.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import javax.crypto.KeyGenerator;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "eyJhbGciOiJIUzI1NiJ9eyJzdWIiOiJTaXp1a2EiLCJpYXQiOjE3MzY0NDQyOTQsImV4cCI6MTczNjQ0Nzg5NH0iLcq72DA40nJccBJliifWlYnsfxwrTK5niCldEbbObs";  // 使用 Base64 编码的密钥
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24 hour

    // 生成随机密钥
    private static SecretKey generateSecretKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        keyGenerator.init(256);  // 256 位密钥
        return keyGenerator.generateKey();
    }

    // 生成 JWT
    public static String generateToken(String username)  {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignKey()) // 使用静态密钥进行签名
                .compact();
    }

    // 验证 JWT 并提取声明
    public static Claims extractClaims(String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7); // 去除 Bearer 前缀
            }

            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY)))  // 解码 BASE64 密钥
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            System.out.println("JWT Token validation failed: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error processing JWT token", e);
        }
    }

    private static Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);  // 解码密钥
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // 验证 Token 是否有效
    public static boolean validateToken(String token, String username) {
        String extractedUsername = extractClaims(token).getSubject();
        return extractedUsername.equals(username) && !isTokenExpired(token);
    }

    // 检查 Token 是否过期
    private static boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}
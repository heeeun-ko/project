package com.example.project.domain.auth.jwt;

import com.example.project.domain.user.enums.UserRole;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {

  @Value("${jwt.secret}")
  private String secret;

  private SecretKey secretKey;

  public static final long ACCESS_TOKEN_EXPIRE = 1000L * 60 * 60 * 2; // 2시간
  public static final long REFRESH_TOKEN_EXPIRE = 1000L * 60 * 60 * 24 * 2; // 2일

  @PostConstruct
  public void init() {
    this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
  }

  public String createAccessToken(Long userId, UserRole role) {
    return Jwts.builder()
        .setSubject(String.valueOf(userId))
        .claim("role", role.name())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE))
        .signWith(secretKey, SignatureAlgorithm.HS256)
        .compact();
  }

  public String createRefreshToken(Long userId) {
    return Jwts.builder()
        .setSubject(String.valueOf(userId))
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE))
        .signWith(secretKey, SignatureAlgorithm.HS256)
        .compact();
  }

  public Long getUserId(String token) {
    return Long.valueOf(
        Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject()
    );
  }

  public UserRole getUserRole(String token) {
    return UserRole.valueOf(
        Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .get("role", String.class)
    );
  }

  public boolean validate(String token) {
    try {
      getUserId(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}

package com.github.gubbib.backend.JWT;

import com.github.gubbib.backend.Domain.User.User;
import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenProvider {
    private final Key key;
    private final long accessTokenValidityInMillis;
    private final long refreshTokenValidityInMillis;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-validity}") long accessTokenValidityInMillis,
            @Value("${jwt.refresh-token-validity}") long refreshTokenValidityInMillis
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessTokenValidityInMillis = accessTokenValidityInMillis;
        this.refreshTokenValidityInMillis = refreshTokenValidityInMillis;
    }

    // access token 생성
    public String createAccessToken(User user) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + accessTokenValidityInMillis);

        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setIssuedAt(now)
                .setExpiration(expiration)
                .addClaims(Map.of(
                        "email", user.getEmail(),
                        "role", user.getRole(),
                        "provider", user.getProvider(),
                        "type", "access"
                ))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // refresh token 생성
    public String createRefreshToken(User user) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + refreshTokenValidityInMillis);

        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setIssuedAt(now)
                .setExpiration(expiration)
                .addClaims(Map.of(
                        "email", user.getEmail(),
                        "role", user.getRole(),
                        "provider", user.getProvider(),
                        "type", "refresh"
                ))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰 파싱 및 검증
    public Jws<Claims> parseToken(String token){
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

        } catch(ExpiredJwtException e){
            throw new GlobalException(ErrorCode.AUTH_TOKEN_EXPIRED);
        } catch(JwtException | IllegalArgumentException e){
            throw new GlobalException(ErrorCode.AUTH_TOKEN_INVALID);
        }
    }

    // refreshToken 검증
    public Jws<Claims> parseRefreshToken(String token){
        Jws<Claims> jws = parseToken(token);
        Claims claims = jws.getBody();

        String type = claims.get("type", String.class);

        if(!"refresh".equals(type)){
            throw new GlobalException(ErrorCode.AUTH_REFRESH_TOKEN_INVALID);
        }

        return jws;
    }

    // getter
    public Long getAccessTokenValidityInMillis(){
        return accessTokenValidityInMillis;
    }
    public Long getRefreshTokenValidityInMillis(){
        return refreshTokenValidityInMillis;
    }

    public Long getUserId(String token){
        return Long.parseLong(parseToken(token).getBody().getSubject());
    }

    public String getEmail(String token){
        return parseToken(token).getBody().get("email", String.class);
    }

    public String getRole(String token){
        return parseToken(token).getBody().get("role", String.class);
    }

    public String getProvider(String token){
        return parseToken(token).getBody().get("provider", String.class);
    }

    public boolean isRefreshToken(String token){
        Claims claims = parseToken(token).getBody();
        String type = claims.get("type", String.class);

        return "refresh".equals(type);
    }
}
package com.github.gubbib.backend.Service.Security;

import com.github.gubbib.backend.Domain.User.User;
import com.github.gubbib.backend.JWT.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtCookieService {

    private final JwtTokenProvider jwtTokenProvider;

    public ResponseCookie createAccessToken(User user) {
        String accessToken = jwtTokenProvider.createAccessToken(user);

        return ResponseCookie.from("Access_Token_Cookie", accessToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(jwtTokenProvider.getAccessTokenValidityInMillis()/1000)
                .sameSite("Lax")
                .build();
    }


    public ResponseCookie createRefreshToken(User user) {
        String refreshToken = jwtTokenProvider.createRefreshToken(user);
        return ResponseCookie.from("Refresh_Token_Cookie", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/auth")
                .maxAge(jwtTokenProvider.getRefreshTokenValidityInMillis()/1000)
                .sameSite("Strict")
                .build();
    }

    public ResponseCookie clearAccessTokenCookie(){
        return ResponseCookie.from("Access_Token_Cookie", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();
    }

    public ResponseCookie clearRefreshTokenCookie(){
        return ResponseCookie.from("Refresh_Token_Cookie", "")
                .httpOnly(true)
                .secure(true)
                .path("/auth")
                .maxAge(0)
                .sameSite("Strict")
                .build();
    }
}

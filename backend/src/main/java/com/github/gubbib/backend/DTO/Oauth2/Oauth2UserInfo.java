package com.github.gubbib.backend.DTO.Oauth2;

import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;
import lombok.Builder;

import java.util.Map;

@Builder
public record Oauth2UserInfo(
    String name,
    String email,
    String provider
) {
    public static Oauth2UserInfo of(String registrationId, Map<String, Object> attributes){
        return switch(registrationId){
            case "google"   ->   ofGoogle(attributes);
            case "kakao"    ->   ofKakao(attributes);
            case "naver"    ->   ofNaver(attributes);
            default -> throw new GlobalException(ErrorCode.AUTH_PROVIDER_MISMATCH);
        };
    }

    private static Oauth2UserInfo ofGoogle(Map<String, Object> attributes){
        return Oauth2UserInfo.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .provider("GOOGLE")
                .build();
    }
    private static Oauth2UserInfo ofKakao(Map<String, Object> attributes){
        return null;
    }
    private static Oauth2UserInfo ofNaver(Map<String, Object> attributes){
        return null;
    }
}

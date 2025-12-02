package com.github.gubbib.backend.DTO.Oauth2;

import com.github.gubbib.backend.Exception.Auth.AuthProviderMismatchException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
            default -> throw new AuthProviderMismatchException();
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

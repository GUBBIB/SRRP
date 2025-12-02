package com.github.gubbib.backend.DTO.Oauth2;

import com.github.gubbib.backend.Exception.Auth.AuthProviderMismatchException;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

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

    private static Map<String, Object> ofGoogle(Map<String, Object> attributes){

    }
    private static Map<String, Object> ofKakao(Map<String, Object> attributes){

    }
    private static Map<String, Object> ofNaver(Map<String, Object> attributes){

    }
}

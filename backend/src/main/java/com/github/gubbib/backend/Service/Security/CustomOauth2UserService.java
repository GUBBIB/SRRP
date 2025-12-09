package com.github.gubbib.backend.Service.Security;

import com.github.gubbib.backend.DTO.Oauth2.Oauth2UserInfo;
import com.github.gubbib.backend.Domain.User.User;
import com.github.gubbib.backend.Exception.User.UserNotFoundException;
import com.github.gubbib.backend.Repository.User.UserRepository;
import com.github.gubbib.backend.Security.CustomUserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("✅ [OAuth2] loadUser 진입");

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        Map<String, Object> attributes = oAuth2User.getAttributes();

        Oauth2UserInfo oauth2UserInfo = Oauth2UserInfo.of(registrationId, attributes);

        String email = oauth2UserInfo.email();
        String name = oauth2UserInfo.name();
        String provider = oauth2UserInfo.provider();
        log.info("✅ [OAuth2] provider={}, email={}, name={}", userRequest.getClientRegistration().getRegistrationId(), email, name);

        User user = userRepository.findByEmail(email).orElse(null);

        boolean isNewUser = false;

        if(user == null){
            isNewUser = true;
        }
        log.info("✅ [OAuth2] user={}, isNewUser={}", user,  isNewUser);

        return new CustomUserPrincipal(attributes, isNewUser);

    }
}

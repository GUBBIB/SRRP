package com.github.gubbib.backend.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException
    {

        log.debug("✅ [OAuth2] Oauth2SuccessHandler 진입");

        CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
        String redirectUrl;
        if(principal != null && principal.isNewUser()){
            log.debug("✅ [OAuth2] 신규 가입자");
            redirectUrl = "http://localhost:8080/swagger-ui/index.html";
        } else {
            log.debug("✅ [OAuth2] 기존 가입자");
            redirectUrl = "http://localhost:8080/swagger-ui/index.html";
        }
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}

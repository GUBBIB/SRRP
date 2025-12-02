package com.github.gubbib.backend.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
        String redirectUrl;
        if(principal != null && principal.isNewUser()){
            redirectUrl = "https://회원가입 페이지로 이동";
        } else {
            redirectUrl = "https://도메인주소/oauth2/auth/login";
        }
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}

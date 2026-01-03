package com.github.gubbib.backend.Service.Auth;

import com.github.gubbib.backend.DTO.Auth.*;
import com.github.gubbib.backend.Security.CustomUserPrincipal;

public interface AuthService {
    AuthResultDTO register(RegisterRequestDTO requestDTO);
    AuthResultDTO login(LoginRequestDTO requestDTO);
    AuthResultDTO logout();

    String generateCode();
    void sendVerificationMail(CustomUserPrincipal userPrincipal, StudentVerifyRequestDTO studentVerifyRequestDTO);

    void verifyStudent(CustomUserPrincipal userPrincipal, VerificationCodeDTO dto);
}

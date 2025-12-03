package com.github.gubbib.backend.Service.Auth;

import com.github.gubbib.backend.DTO.Auth.AuthResultDTO;
import com.github.gubbib.backend.DTO.Auth.LoginRequestDTO;
import com.github.gubbib.backend.DTO.Auth.RegisterRequestDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    AuthResultDTO register(RegisterRequestDTO requestDTO);
    AuthResultDTO login(LoginRequestDTO requestDTO);
    AuthResultDTO logout();
}

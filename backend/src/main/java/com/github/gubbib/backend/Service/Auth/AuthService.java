package com.github.gubbib.backend.Service.Auth;

import com.github.gubbib.backend.DTO.Auth.AuthResponseDTO;
import com.github.gubbib.backend.DTO.Auth.LoginRequestDTO;
import com.github.gubbib.backend.DTO.Auth.RegisterRequestDTO;

public interface AuthService {
    AuthResponseDTO register(RegisterRequestDTO requestDTO);
    AuthResponseDTO login(LoginRequestDTO requestDTO);
}

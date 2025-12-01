package com.github.gubbib.backend.Controller.Auth;

import com.github.gubbib.backend.DTO.Auth.AuthResponseDTO;
import com.github.gubbib.backend.DTO.Auth.LoginRequestDTO;
import com.github.gubbib.backend.DTO.Auth.RegisterRequestDTO;
import com.github.gubbib.backend.Domain.User.User;
import com.github.gubbib.backend.Service.Auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/api/v1/auth")
public class AuthController {

    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO requestDTO){

        AuthResponseDTO response = authService.register(requestDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO requestDTO){

        AuthResponseDTO response = authService.login(requestDTO);
        return ResponseEntity.ok(response);
    }

}

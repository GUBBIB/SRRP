package com.github.gubbib.backend.Service.Auth;

import com.github.gubbib.backend.DTO.Auth.AuthResponseDTO;
import com.github.gubbib.backend.DTO.Auth.RegisterRequestDTO;
import com.github.gubbib.backend.Domain.User.User;
import com.github.gubbib.backend.Repository.User.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImp implements AuthService {

    PasswordEncoder passwordEncoder;
    UserRepository userRepository;

    @Override
    public AuthResponseDTO register(RegisterRequestDTO requestDTO) {

//        예외 추가 예정
//        if(userRepository.existsByEmail(requestDTO.email())){
//            throw
//        } else if(userRepository.existsByNickname(requestDTO.nickname())){
//            throw
//        }

        User result = User.builder()
                .email(requestDTO.email())
                .name(requestDTO.name())
                .password(passwordEncoder.encode(requestDTO.password()))
                .nickname(requestDTO.nickname())
                .build();

        User saved = userRepository.save(result);

        // jwt 코드 추가 후 수정 예정
        String accessToken = "dummy accessToken"; 
        String refreshToken = "dummy refreshToken";

        return new AuthResponseDTO(
                saved.getId(),
                saved.getEmail(),
                accessToken,
                refreshToken
        );
    }

    @Override
    public AuthResponseDTO login(RegisterRequestDTO requestDTO) {
        return null;
    }
}

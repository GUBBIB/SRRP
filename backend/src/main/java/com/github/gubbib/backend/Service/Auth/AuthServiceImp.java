package com.github.gubbib.backend.Service.Auth;

import com.github.gubbib.backend.DTO.Auth.AuthResponseDTO;
import com.github.gubbib.backend.DTO.Auth.LoginRequestDTO;
import com.github.gubbib.backend.DTO.Auth.RegisterRequestDTO;
import com.github.gubbib.backend.Domain.User.User;
import com.github.gubbib.backend.Exception.Auth.AuthEmailDuplicationException;
import com.github.gubbib.backend.Exception.Auth.AuthInvalidCredentialsException;
import com.github.gubbib.backend.Exception.User.UserNicknameDuplicationException;
import com.github.gubbib.backend.JWT.JwtTokenProvider;
import com.github.gubbib.backend.Repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public AuthResponseDTO register(RegisterRequestDTO requestDTO) {

        if(userRepository.existsByEmail(requestDTO.email())){
            throw new AuthEmailDuplicationException();
        } else if(userRepository.existsByNickname(requestDTO.nickname())){
            throw new UserNicknameDuplicationException();
        }

        User result = User.builder()
                .email(requestDTO.email())
                .name(requestDTO.name())
                .password(passwordEncoder.encode(requestDTO.password()))
                .nickname(requestDTO.nickname())
                .build();

        User saved = userRepository.save(result);

        String accessToken = jwtTokenProvider.createAccessToken(saved);
        String refreshToken = jwtTokenProvider.createRefreshToken(saved);

        return new AuthResponseDTO(
                saved.getId(),
                saved.getEmail(),
                accessToken,
                refreshToken
        );
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO requestDTO) {

        // 이메일 비번 틀렸을 경우
        User user = userRepository.findByEmail(requestDTO.email())
                .orElseThrow(AuthInvalidCredentialsException::new);
        if(!passwordEncoder.matches(requestDTO.password(), user.getPassword())){
            throw new AuthInvalidCredentialsException();
        }

        String accessToken = jwtTokenProvider.createAccessToken(user);
        String refreshToken = jwtTokenProvider.createRefreshToken(user);

        return new AuthResponseDTO(
                user.getId(),
                user.getEmail(),
                accessToken,
                refreshToken
        );
    }
}

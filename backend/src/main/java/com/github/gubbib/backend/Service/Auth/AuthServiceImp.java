package com.github.gubbib.backend.Service.Auth;

import com.github.gubbib.backend.DTO.Auth.AuthResponseDTO;
import com.github.gubbib.backend.DTO.Auth.AuthResultDTO;
import com.github.gubbib.backend.DTO.Auth.LoginRequestDTO;
import com.github.gubbib.backend.DTO.Auth.RegisterRequestDTO;
import com.github.gubbib.backend.Domain.User.User;
import com.github.gubbib.backend.Exception.Auth.AuthEmailDuplicationException;
import com.github.gubbib.backend.Exception.Auth.AuthInvalidCredentialsException;
import com.github.gubbib.backend.Exception.User.UserNicknameDuplicationException;
import com.github.gubbib.backend.JWT.JwtTokenProvider;
import com.github.gubbib.backend.Repository.User.UserRepository;
import com.github.gubbib.backend.Service.Security.JwtCookieService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImp implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtCookieService jwtCookieService;

    @Override
    public AuthResultDTO register(RegisterRequestDTO requestDTO) {

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

        AuthResponseDTO authResponseDTO = AuthResponseDTO.builder()
                .userId(saved.getId())
                .email(saved.getEmail())
                .nickname(saved.getNickname())
                .build();

        ResponseCookie accessTokenCookie = jwtCookieService.createAccessToken(saved);
        ResponseCookie refreshTokenCookie = jwtCookieService.createRefreshToken(saved);

        return new AuthResultDTO(
                authResponseDTO,
                accessTokenCookie,
                refreshTokenCookie
        );
    }

    @Override
    public AuthResultDTO login(LoginRequestDTO requestDTO) {

        // 이메일 비번 틀렸을 경우
        User user = userRepository.findByEmail(requestDTO.email())
                .orElseThrow(AuthInvalidCredentialsException::new);
        if(!passwordEncoder.matches(requestDTO.password(), user.getPassword())){
            throw new AuthInvalidCredentialsException();
        }

        ResponseCookie accessTokenCookie = jwtCookieService.createAccessToken(user);
        ResponseCookie refreshTokenCookie = jwtCookieService.createRefreshToken(user);

        AuthResponseDTO authResponseDTO = AuthResponseDTO.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();

        return new AuthResultDTO(
                authResponseDTO,
                accessTokenCookie,
                refreshTokenCookie
        );
    }

    @Override
    public AuthResultDTO logout() {
        return new AuthResultDTO(
                null,
                jwtCookieService.clearAccessTokenCookie(),
                jwtCookieService.clearRefreshTokenCookie()
        );
    }
}

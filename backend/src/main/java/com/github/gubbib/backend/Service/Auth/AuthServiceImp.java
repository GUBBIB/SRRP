package com.github.gubbib.backend.Service.Auth;

import com.github.gubbib.backend.DTO.Auth.AuthResponseDTO;
import com.github.gubbib.backend.DTO.Auth.AuthResultDTO;
import com.github.gubbib.backend.DTO.Auth.LoginRequestDTO;
import com.github.gubbib.backend.DTO.Auth.RegisterRequestDTO;
import com.github.gubbib.backend.Domain.User.User;
import com.github.gubbib.backend.Domain.User.UserRole;
import com.github.gubbib.backend.Exception.Auth.AuthInvalidCredentialsException;
import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;
import com.github.gubbib.backend.Repository.User.UserRepository;
import com.github.gubbib.backend.Service.Security.JwtCookieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImp implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtCookieService jwtCookieService;

    @Override
    @Transactional(readOnly = false)
    public AuthResultDTO register(RegisterRequestDTO requestDTO) {

        if(userRepository.existsByEmail(requestDTO.email())){
            throw new GlobalException(ErrorCode.AUTH_EMAIL_DUPLICATION);
        } else if(userRepository.existsByNicknameAndRoleNot(requestDTO.nickname(),  UserRole.SYSTEM)){
            throw new GlobalException(ErrorCode.USER_NICKNAME_DUPLICATION);
        }

        String email = requestDTO.email();
        String name = requestDTO.name();
        String password = passwordEncoder.encode(requestDTO.password());
        String nickname = requestDTO.nickname();

        User result = User.createLocal(email, password, name, nickname);

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
        User user = userRepository.findByEmailAndRoleNot(requestDTO.email(),  UserRole.SYSTEM)
                .orElseThrow(AuthInvalidCredentialsException::new);
        if(!passwordEncoder.matches(requestDTO.password(), user.getPassword())){
            throw new GlobalException(ErrorCode.AUTH_INVALID_CREDENTIALS);
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

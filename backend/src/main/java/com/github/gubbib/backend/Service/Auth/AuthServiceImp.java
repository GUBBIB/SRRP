package com.github.gubbib.backend.Service.Auth;

import com.github.gubbib.backend.DTO.Auth.*;
import com.github.gubbib.backend.Domain.User.User;
import com.github.gubbib.backend.Domain.User.UserRole;
import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;
import com.github.gubbib.backend.Repository.User.UserRepository;
import com.github.gubbib.backend.Security.CustomUserPrincipal;
import com.github.gubbib.backend.Service.Mail.MailService;
import com.github.gubbib.backend.Service.Redis.StudentVerificationService;
import com.github.gubbib.backend.Service.Security.JwtCookieService;
import com.github.gubbib.backend.Service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Duration;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImp implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtCookieService jwtCookieService;
    private final UserService userService;
    private final StudentVerificationService studentVerificationService;
    private final SecureRandom secureRandom =  new SecureRandom();
    private final MailService mailService;

    @Override
    public String generateCode() {
        return String.valueOf(
                secureRandom.nextInt(900_000) + 100_000
        );
    }

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
                .orElseThrow(() -> new GlobalException(ErrorCode.AUTH_INVALID_CREDENTIALS));
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

    @Override
    @Transactional
    public void sendVerificationMail(CustomUserPrincipal userPrincipal, StudentVerifyRequestDTO dto) {
        User u = userService.checkUser(userPrincipal);

        if(u.isStudents()){
            throw new GlobalException(ErrorCode.AUTH_ALREADY_VERIFIED);
        }

        String code = generateCode();

        studentVerificationService.increaseVerificationRequestCount(u.getEmail());
        studentVerificationService.saveVerificationCode(
                u.getEmail(),
                code,
                Duration.ofMinutes(5)
        );

        mailService.sendStudentVerificationMail(
                dto.email(),
                code
        );
    }

    @Override
    public void verifyStudent(CustomUserPrincipal userPrincipal, VerificationCodeDTO dto) {
        User u = userService.checkUser(userPrincipal);

        if(u.isStudents()){
            throw new GlobalException(ErrorCode.AUTH_ALREADY_VERIFIED);
        }

        String savedCode = studentVerificationService.getVerificationCode(u.getEmail());

        if(savedCode == null){
            throw new GlobalException(ErrorCode.AUTH_VERIFICATION_CODE_EXPIRED);
        }

        if (!savedCode.equals(dto.code())) {
            throw new GlobalException(ErrorCode.AUTH_VERIFICATION_CODE_INVALID);
        }

        u.verifyStudent();
        userRepository.save(u);

        studentVerificationService.deleteVerificationCode(u.getEmail());
    }
}

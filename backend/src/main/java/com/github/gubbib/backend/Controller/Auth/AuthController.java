package com.github.gubbib.backend.Controller.Auth;

import com.github.gubbib.backend.DTO.Auth.AuthResponseDTO;
import com.github.gubbib.backend.DTO.Auth.AuthResultDTO;
import com.github.gubbib.backend.DTO.Auth.LoginRequestDTO;
import com.github.gubbib.backend.DTO.Auth.RegisterRequestDTO;
import com.github.gubbib.backend.DTO.Error.ErrorResponseDTO;
import com.github.gubbib.backend.Service.Auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/api/v1/auth")
@Tag(name = "인증", description = "인증 관련 API")
public class AuthController {

    private final AuthService authService;


    @Operation(summary = "회원가입", description = "이메일, 비밀번호 등을 입력해 회원가입을 진행한다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "회원가입 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "중복된 이메일",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            )
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO requestDTO){

        AuthResultDTO response = authService.register(requestDTO);
        AuthResponseDTO authResponseDTO = response.authResponseDTO();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, response.accessCookie().toString())
                .header(HttpHeaders.SET_COOKIE, response.refreshCookie().toString())
                .body(authResponseDTO);
    }

    @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인을 진행한다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponseDTO.class))
            ),
            @ApiResponse(responseCode = "401", description = "잘못된 비밀번호",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            ),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            )
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO requestDTO){

        AuthResultDTO response = authService.login(requestDTO);
        AuthResponseDTO authResponseDTO = response.authResponseDTO();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, response.accessCookie().toString())
                .header(HttpHeaders.SET_COOKIE, response.refreshCookie().toString())
                .body(authResponseDTO);
    }

    @Operation(summary = "로그아웃", description = "현재 유저를 로그아웃한다. (쿠키 초기화)")
    @ApiResponses({
            @ApiResponse(responseCode = "200",  description = "로그아웃 성공")
    })
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(){
        AuthResultDTO response = authService.logout();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, response.accessCookie().toString())
                .header(HttpHeaders.SET_COOKIE, response.refreshCookie().toString())
                .build();
    }

}

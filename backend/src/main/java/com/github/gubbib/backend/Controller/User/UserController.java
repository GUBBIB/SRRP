package com.github.gubbib.backend.Controller.User;

import com.github.gubbib.backend.DTO.Error.ErrorResponseDTO;
import com.github.gubbib.backend.DTO.User.*;
import com.github.gubbib.backend.Security.CustomUserPrincipal;
import com.github.gubbib.backend.Service.User.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/api/v1/users")
@Tag(name = "유저", description = "유저 관련 API")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "내 정보 조회",
            description = "현재 로그인한 유저의 기본 프로필 정보를 조회한다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = UserInfoDTO.class))
            ),
            @ApiResponse(responseCode = "401", description = "인증 실패 (토큰 없음/만료)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            )
    })
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserInfoDTO> me(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal userPrincipal
    ) {
        UserInfoDTO userInfoDTO = userService.me(userPrincipal);

        return ResponseEntity.ok()
                .body(userInfoDTO);
    }

    @Operation(
            summary = "내가 작성한 게시글 목록 조회",
            description = "현재 로그인한 유저가 작성한 게시글 목록을 최신순으로 조회한다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = UserMyPostDTO.class))
                    )
            ),
            @ApiResponse(responseCode = "401", description = "인증 실패 (토큰 없음/만료)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            )
    })
    @GetMapping("/my/posts")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UserMyPostDTO>> myPost(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal userPrincipal
    ) {

        List<UserMyPostDTO> userMyPostList = userService.myPostList(userPrincipal);

        return ResponseEntity.ok()
                .body(userMyPostList);
    }

    @Operation(
            summary = "내가 작성한 댓글 목록 조회",
            description = "현재 로그인한 유저가 작성한 댓글 목록을 최신순으로 조회한다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = UserMyCommentDTO.class))
                    )
            ),
            @ApiResponse(responseCode = "401", description = "인증 실패 (토큰 없음/만료)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            )
    })
    @GetMapping("/my/comments")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UserMyCommentDTO>> myComment(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal userPrincipal
    ) {
        List<UserMyCommentDTO> userMyCommentList = userService.myCommentList(userPrincipal);

        return ResponseEntity.ok()
                .body(userMyCommentList);
    }

    @Operation(
            summary = "내가 좋아요한 게시글 목록 조회",
            description = "현재 로그인한 유저가 좋아요를 누른 게시글 목록을 최신순으로 조회한다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = UserMyLikePostDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 실패 (토큰 없음/만료)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @GetMapping("/my/like/post")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UserMyLikePostDTO>> myLikePost(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal userPrincipal
    ) {
        List<UserMyLikePostDTO> userMyLikePostList = userService.myLikePostList(userPrincipal);

        return ResponseEntity.ok()
                .body(userMyLikePostList);
    }

    @Operation(
            summary = "내가 좋아요한 댓글 목록 조회",
            description = "현재 로그인한 유저가 좋아요를 누른 댓글 목록을 최신순으로 조회한다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = UserMyLikeCommentDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 실패 (토큰 없음/만료)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @GetMapping("/my/like/comment")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UserMyLikeCommentDTO>> myLikeComment(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal userPrincipal
    ) {
        List<UserMyLikeCommentDTO> userMyLikeCommentList = userService.myLikeCommentList(userPrincipal);

        return ResponseEntity.ok()
                .body(userMyLikeCommentList);
    }

    @Operation(
            summary = "유저 조회",
            description = "다른 유저의 정보를 조회한다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SearchUserInfoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 실패 (토큰 없음/만료)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "조회 대상 유저 없음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @GetMapping("/{userId}")
    public ResponseEntity<SearchUserInfoDTO> searchUser(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal userPrincipal,
            @PathVariable Long userId
    ){
        SearchUserInfoDTO searchUserInfoDTO = userService.searchUserInfo(userPrincipal, userId);

        return ResponseEntity.ok()
                .body(searchUserInfoDTO);
    }

    @Operation(
            summary = "닉네임 중복 확인",
            description = "입력한 닉네임이 사용 가능한지 확인한다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "닉네임 사용 가능 (중복 없음)"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "닉네임 중복됨",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 실패 (토큰 없음/만료)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @GetMapping("/check-nickname")
    public ResponseEntity<Void> checkNickname(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal userPrincipal,
            @RequestParam String nickname
    ){
        userService.checkNickname(userPrincipal, nickname);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "닉네임 변경",
            description = "현재 로그인된 사용자의 닉네임을 변경한다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "닉네임 변경 성공"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "입력 값 오류",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "닉네임 중복",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 실패 (토큰 없음/만료)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @PutMapping("/modify/nickname")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> modifyNickname(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal userPrincipal,
            @RequestBody ModifyUserNicknameDTO modifyUserNicknameDTO
    ){
        userService.modifyNickname(userPrincipal, modifyUserNicknameDTO);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "비밀번호 변경",
            description = "현재 비밀번호를 검증한 뒤 새 비밀번호로 변경한다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "비밀번호 변경 성공 (응답 본문 없음)"),
            @ApiResponse(
                    responseCode = "400",
                    description = "현재 비밀번호 불일치 또는 유효성 검증 실패",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 실패 (토큰 없음/만료)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            )
    })
    @PostMapping("/modify/password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> modifyPassword(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal userPrincipal,
            @RequestBody ModifyUserPasswordDTO modifyUserPasswordDTO
    ){
        userService.modifyPassword(userPrincipal, modifyUserPasswordDTO);

        return ResponseEntity.noContent().build();
    }



}
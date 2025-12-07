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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/api/v1/users")
@Tag(name = "User", description = "유저 관련 API")
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
    @GetMapping("/my-post")
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
    @GetMapping("/my-comment")
    public ResponseEntity<List<UserMyCommentDTO>> myComment(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserPrincipal userPrincipal
    ) {
        List<UserMyCommentDTO> userMyCommentList = userService.myCommentList(userPrincipal);

        return ResponseEntity.ok()
                .body(userMyCommentList);
    }

    @Operation(
            summary = "유저 조회",
            description = "다른 유저를 조회한다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = SearchUserInfoDTO.class))
                    )
            ),
            @ApiResponse(responseCode = "401", description = "인증 실패 (토큰 없음/만료)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            )
    })
    @GetMapping("/{userId}")
    public ResponseEntity<SearchUserInfoDTO> searchUser(
            @AuthenticationPrincipal CustomUserPrincipal userPrincipal,
            @PathVariable Long userId
    ){
        SearchUserInfoDTO searchUserInfoDTO = userService.searchUserInfo(userPrincipal, userId);

        return ResponseEntity.ok()
                .body(searchUserInfoDTO);
    }


    @GetMapping("/check-nickname")
    public ResponseEntity<Void> checkNickname(
            @AuthenticationPrincipal CustomUserPrincipal userPrincipal,
            @RequestParam String nickname
    ){
        userService.checkNickname(userPrincipal, nickname);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/modify/nickname")
    public ResponseEntity<Void> modifyNickname(
            @AuthenticationPrincipal CustomUserPrincipal userPrincipal,
            @RequestBody ModifyUserNicknameDTO modifyUserNicknameDTO
    ){
        userService.modifyNickname(userPrincipal, modifyUserNicknameDTO);

        return ResponseEntity.noContent().build();
    }

}
package com.github.gubbib.backend.Controller.Comment;

import com.github.gubbib.backend.DTO.Comment.CommentCreateRequestDTO;
import com.github.gubbib.backend.DTO.Comment.CommentCreateResponseDTO;
import com.github.gubbib.backend.DTO.Comment.CommentResponseDTO;
import com.github.gubbib.backend.DTO.Error.ErrorResponseDTO;
import com.github.gubbib.backend.Security.CustomUserPrincipal;
import com.github.gubbib.backend.Service.Comment.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "댓글", description = "댓글 관련 API")
public class CommentController {

    private final CommentService commentService;

    @Operation(
            summary = "게시글 댓글 목록 조회",
            description = "특정 게시판(boardId)과 게시글(postId)에 대한 전체 댓글 목록을 조회한다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CommentResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "게시글 또는 게시판을 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @GetMapping("/{boardId}/{postId}")
    public ResponseEntity<CommentResponseDTO> getPostComments(
            @AuthenticationPrincipal CustomUserPrincipal userPrincipal,
            @PathVariable Long boardId,
            @PathVariable Long postId
    ){
        CommentResponseDTO response =  commentService.getPostComments(userPrincipal, boardId, postId);

        return ResponseEntity.ok()
                .body(response);
    }

    @Operation(
            summary = "댓글 작성",
            description = "로그인한 사용자가 게시글에 댓글을 작성합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "댓글 작성 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CommentCreateResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 (내용 누락, 형식 오류 등)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증되지 않은 사용자",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "게시글을 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommentCreateResponseDTO> createComment(
            @AuthenticationPrincipal CustomUserPrincipal userPrincipal,
            @RequestBody CommentCreateRequestDTO commentCreateRequestDTO
    ){
        CommentCreateResponseDTO response = commentService.createComment(userPrincipal, commentCreateRequestDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}

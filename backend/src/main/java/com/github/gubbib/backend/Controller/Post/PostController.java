package com.github.gubbib.backend.Controller.Post;

import com.github.gubbib.backend.DTO.Error.ErrorResponseDTO;
import com.github.gubbib.backend.DTO.Post.PostCreateRequestDTO;
import com.github.gubbib.backend.DTO.Post.PostCreateResponseDTO;
import com.github.gubbib.backend.DTO.Post.PostDetailDTO;
import com.github.gubbib.backend.Security.CustomUserPrincipal;
import com.github.gubbib.backend.Service.Post.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

import java.net.URI;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "게시글", description = "게시글 관련 API")
public class PostController {

    private final PostService postService;

    @Operation(
            summary = "게시글 상세 조회",
            description = """
                특정 게시판(boardId)의 특정 게시글(postId)에 대한 상세 정보를 조회합니다.
                로그인 여부에 따라 isOwner, isLikedByCurrentUser 값이 달라집니다.
                """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상세 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostDetailDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "해당 게시판 또는 게시글을 찾을 수 없음",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @GetMapping("/{boardId}/{postId}")
    public ResponseEntity<PostDetailDTO> getPostDetail(
            @AuthenticationPrincipal CustomUserPrincipal userPrincipal,
            @Parameter(description = "게시판 ID") @PathVariable Long boardId,
            @Parameter(description = "게시글 ID") @PathVariable Long postId
    ){

        PostDetailDTO postdetailDTO = postService.getPostDetail(userPrincipal, boardId, postId);

        return  ResponseEntity.ok()
                .body(postdetailDTO);
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PostCreateResponseDTO> createPost(
            @AuthenticationPrincipal CustomUserPrincipal userPrincipal,
            @RequestBody PostCreateRequestDTO postCreateRequestDTO
    ){
        PostCreateResponseDTO response = postService.createPost(userPrincipal, postCreateRequestDTO);

        // URI.create 로 할시 조회 경로 수정 필요
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

}
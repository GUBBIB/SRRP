package com.github.gubbib.backend.Controller.Comment;

import com.github.gubbib.backend.Security.CustomUserPrincipal;
import com.github.gubbib.backend.Service.Comment.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "Comment", description = "댓글 관련 API")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{boardId}/{postId}")
    public ResponseEntity<> getPostComments(
            @AuthenticationPrincipal CustomUserPrincipal userPrincipal,
            @PathVariable Long boardId,
            @PathVariable Long postId
    ){


        return ResponseEntity.ok()
                .body();
    }
}

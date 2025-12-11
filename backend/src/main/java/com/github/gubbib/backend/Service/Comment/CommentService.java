package com.github.gubbib.backend.Service.Comment;

import com.github.gubbib.backend.DTO.Comment.CommentResponseDTO;
import com.github.gubbib.backend.Security.CustomUserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

public interface CommentService {
    CommentResponseDTO getPostComments(@AuthenticationPrincipal CustomUserPrincipal userPrincipal, Long boardId, Long postId);
}

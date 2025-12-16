package com.github.gubbib.backend.Service.Comment;

import com.github.gubbib.backend.DTO.Comment.CommentCreateRequestDTO;
import com.github.gubbib.backend.DTO.Comment.CommentCreateResponseDTO;
import com.github.gubbib.backend.DTO.Comment.CommentResponseDTO;
import com.github.gubbib.backend.Domain.Comment.Comment;
import com.github.gubbib.backend.Security.CustomUserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

public interface CommentService {
    Comment existParentComment(Long parentId);
    CommentResponseDTO getPostComments(@AuthenticationPrincipal CustomUserPrincipal userPrincipal, Long boardId, Long postId);
    CommentCreateResponseDTO createComment(@AuthenticationPrincipal CustomUserPrincipal userPrincipal, CommentCreateRequestDTO dto);
}

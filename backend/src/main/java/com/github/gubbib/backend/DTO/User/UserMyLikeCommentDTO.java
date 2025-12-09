package com.github.gubbib.backend.DTO.User;

import java.time.LocalDateTime;

public record UserMyLikeCommentDTO(
        Long commentId,
        Long postId,
        Long boardId,
        String comment,
        String author,
        Long userId,
        LocalDateTime createdAt
) {
}

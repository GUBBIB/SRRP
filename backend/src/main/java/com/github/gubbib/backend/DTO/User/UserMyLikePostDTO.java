package com.github.gubbib.backend.DTO.User;

import java.time.LocalDateTime;

public record UserMyLikePostDTO(
        Long postId,
        Long boardId,
        String title,
        String author,
        Long userId,
        LocalDateTime createdAt,
        Long viewCount,
        Long commentCnt
) {
}

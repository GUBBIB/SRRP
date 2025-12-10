package com.github.gubbib.backend.DTO.Post;

import java.time.LocalDateTime;

public record PostListDTO(
        Long boardId,
        Long postId,
        String title,
        String nickname,
        Long userId,
        Long commentCounter,
        LocalDateTime createdAt
) {
}

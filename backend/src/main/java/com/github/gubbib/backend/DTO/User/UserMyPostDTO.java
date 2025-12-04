package com.github.gubbib.backend.DTO.User;

import com.github.gubbib.backend.Domain.User.User;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserMyPostDTO(
        String title,
        String content,
        String board_name,
        Long comments_cnt,
        Long postId,
        Long userId,
        String nickname,
        String profile_image_url,
        LocalDateTime createdAt
) {
}

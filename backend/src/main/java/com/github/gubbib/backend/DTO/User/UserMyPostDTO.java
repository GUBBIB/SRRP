package com.github.gubbib.backend.DTO.User;

import com.github.gubbib.backend.Domain.User.User;
import lombok.Builder;

@Builder
public record UserMyPostDTO(
        String title,
        String content,
        String board_name,
        Long comments_cnt,
        User user
) {
}

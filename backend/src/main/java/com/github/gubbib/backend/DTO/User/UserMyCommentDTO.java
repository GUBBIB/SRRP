package com.github.gubbib.backend.DTO.User;

import com.github.gubbib.backend.Domain.Comment.Comment;
import com.github.gubbib.backend.Domain.User.User;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserMyCommentDTO(
        Long commentId,
        String comment,
        Long postId,
        String title,
        Long boardId,
        String boardName,
        Long userId,
        String nickname,
        String profile_image_url,
        LocalDateTime createdAt
) {
}

package com.github.gubbib.backend.DTO.User;

import com.github.gubbib.backend.Domain.Comment.Comment;
import com.github.gubbib.backend.Domain.User.User;
import lombok.Builder;

@Builder
public record UserMyCommentDTO(
        Comment comment,
        User user
) {
}

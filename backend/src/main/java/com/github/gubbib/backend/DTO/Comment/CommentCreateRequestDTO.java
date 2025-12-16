package com.github.gubbib.backend.DTO.Comment;

import lombok.Builder;

@Builder
public record CommentCreateRequestDTO(
        Long postId,
        Long boardId,
        String comment,
        Long parentId
) {
}

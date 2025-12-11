package com.github.gubbib.backend.DTO.Comment;

import lombok.Builder;

import java.util.List;

@Builder
public record CommentResponseDTO(
        Long boardId,
        Long postId,
        List<CommentListDTO> comments
) {
}

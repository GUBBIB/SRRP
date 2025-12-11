package com.github.gubbib.backend.DTO.Comment;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentListDTO(
        Long commentId,
        Long userId,
        String nickname,
        Boolean isOwner,
        Boolean isLikedByCurrentUser,
        String comment,
        Long parentId,
        Long depth,
        LocalDateTime createdAt
) {
    public CommentListDTO addFlag(boolean isOwner, boolean isLikedByCurrentUser){
        return new CommentListDTO(
                commentId,
                userId,
                nickname,
                isOwner,
                isLikedByCurrentUser,
                comment,
                parentId,
                depth,
                createdAt
        );
    }
}

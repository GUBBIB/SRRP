package com.github.gubbib.backend.DTO.Post;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostDetailDTO(
        Long postId,
        Long boardId,
        Long userId,
        String boardName,
        String nickname,
        String title,
        String content,
        Long viewCount,
        Long commentCount,
        Long likeCount,
        Boolean isLikedByCurrentUser,
        Boolean isOwner,
        LocalDateTime createdAt
) {
    public PostDetailDTO addFlags(Boolean isLikedByCurrentUser, Boolean isOwner) {
        return new  PostDetailDTO(
                postId,
                boardId,
                userId,
                boardName,
                nickname,
                title,
                content,
                viewCount,
                commentCount,
                likeCount,
                isLikedByCurrentUser,
                isOwner,
                createdAt
        );
    }
}

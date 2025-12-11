package com.github.gubbib.backend.Service.Like;

public interface LikeService {
    boolean isLikedPost(Long postId, Long userId);
    boolean isLikedComment(Long commentId, Long userId);
}

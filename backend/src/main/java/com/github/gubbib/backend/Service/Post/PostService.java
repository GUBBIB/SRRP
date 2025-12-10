package com.github.gubbib.backend.Service.Post;

import com.github.gubbib.backend.DTO.Post.PostDetailDTO;
import com.github.gubbib.backend.Security.CustomUserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

public interface PostService {
    PostDetailDTO getPostDetail(@AuthenticationPrincipal CustomUserPrincipal userPrincipal, Long boardId, Long postId);
}

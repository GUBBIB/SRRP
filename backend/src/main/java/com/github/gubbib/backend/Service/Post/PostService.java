package com.github.gubbib.backend.Service.Post;

import com.github.gubbib.backend.DTO.Post.PostCreateRequestDTO;
import com.github.gubbib.backend.DTO.Post.PostCreateResponseDTO;
import com.github.gubbib.backend.DTO.Post.PostDetailDTO;
import com.github.gubbib.backend.Security.CustomUserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.github.gubbib.backend.DTO.Post.PostDeleteResponseDTO;
import com.github.gubbib.backend.DTO.Post.PostListDTO;
import com.github.gubbib.backend.DTO.Post.PostUpdateRequestDTO;
import com.github.gubbib.backend.DTO.Post.PostUpdateResponseDTO;


import java.util.List;

public interface PostService {
    PostDetailDTO getPostDetail(@AuthenticationPrincipal CustomUserPrincipal userPrincipal, Long boardId, Long postId);
    PostCreateResponseDTO createPost(@AuthenticationPrincipal CustomUserPrincipal userPrincipal, PostCreateRequestDTO dto);


    List<PostListDTO> getPostList(Long boardId);

    PostUpdateResponseDTO updatePost(CustomUserPrincipal userPrincipal, Long boardId, Long postId,  PostUpdateRequestDTO dto);

    PostDeleteResponseDTO deletePost(CustomUserPrincipal userPrincipal, Long boardId, Long postId);
}


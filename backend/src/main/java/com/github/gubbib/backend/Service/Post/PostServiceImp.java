package com.github.gubbib.backend.Service.Post;

import com.github.gubbib.backend.DTO.Post.PostDetailDTO;
import com.github.gubbib.backend.Domain.Post.Post;
import com.github.gubbib.backend.Exception.Post.PostNotFoundException;
import com.github.gubbib.backend.Repository.Like.LikeRepository;
import com.github.gubbib.backend.Repository.Post.PostRepository;
import com.github.gubbib.backend.Security.CustomUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class PostServiceImp implements PostService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;


    private Post existsPost(Long boardId, Long postId) {
        return postRepository.findByBoard_IdAndId(boardId, postId)
                .orElseThrow(PostNotFoundException::new);
    }

    @Override
    public PostDetailDTO getPostDetail(CustomUserPrincipal userPrincipal, Long boardId, Long postId) {
        Post  post = existsPost(boardId, postId);

        PostDetailDTO postDetailDTO = postRepository.findPostDetail(boardId, postId);

        Long currentUserId = (userPrincipal != null) ? userPrincipal.getId() : null;

        boolean isOwner = false;
        boolean isLiked = false;

        if(currentUserId != null){
            isOwner = post.getUser().getId().equals(currentUserId);
            isLiked = likeRepository.existsByPost_IdAndUser_Id(postId, currentUserId);
        }

        postDetailDTO.addFlags(isOwner, isLiked);


        return postDetailDTO;
    }
}

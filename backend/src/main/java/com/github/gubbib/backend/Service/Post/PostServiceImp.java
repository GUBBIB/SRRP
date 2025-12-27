package com.github.gubbib.backend.Service.Post;

import com.github.gubbib.backend.DTO.Post.PostCreateRequestDTO;
import com.github.gubbib.backend.DTO.Post.PostCreateResponseDTO;
import com.github.gubbib.backend.DTO.Post.PostDetailDTO;
import com.github.gubbib.backend.Domain.Board.Board;
import com.github.gubbib.backend.Domain.Like.LikeType;
import com.github.gubbib.backend.Domain.Post.Post;
import com.github.gubbib.backend.Domain.User.User;
import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;
import com.github.gubbib.backend.Repository.Like.LikeRepository;
import com.github.gubbib.backend.Repository.Post.PostRepository;
import com.github.gubbib.backend.Security.CustomUserPrincipal;
import com.github.gubbib.backend.Service.BoardPost.BoardPostService;
import com.github.gubbib.backend.Service.Redis.ViewCounterService;
import com.github.gubbib.backend.Service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class PostServiceImp implements PostService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final ViewCounterService viewCounterService;
    private final UserService userService;
    private final BoardPostService boardPostService;


    private Post existsPost(Long boardId, Long postId) {
        return postRepository.findByBoard_IdAndId(boardId, postId)
                .orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));
    }

    @Override
    public PostDetailDTO getPostDetail(CustomUserPrincipal userPrincipal, Long boardId, Long postId) {
        Post  post = existsPost(boardId, postId);

        PostDetailDTO postDetailDTO = postRepository.findPostDetail(boardId, postId, LikeType.POST);

        Long currentUserId = (userPrincipal != null) ? userPrincipal.getId() : null;

        boolean isOwner = false;
        boolean isLiked = false;

        if(currentUserId != null){
            isOwner = post.getUser().getId().equals(currentUserId);
            isLiked = likeRepository.existsByPost_IdAndUser_Id(postId, currentUserId);
        }

        postDetailDTO.addFlags(isOwner, isLiked);

        if(!isOwner) viewCounterService.increasePostView(postId);

        return postDetailDTO;
    }

    @Override
    public PostCreateResponseDTO createPost(CustomUserPrincipal userPrincipal, PostCreateRequestDTO dto) {
        User user = userService.checkUser(userPrincipal);
        Board board = boardPostService.existsBoard(dto.boardId());

        Post p = Post.create(
                dto.title(),
                dto.content(),
                user,
                board
        );

        postRepository.save(p);

        PostCreateResponseDTO response =
                PostCreateResponseDTO.builder()
                    .postId(p.getId())
                    .boardId(board.getId())
                    .build();

        return response;
    }
}

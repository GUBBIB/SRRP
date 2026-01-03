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

import com.github.gubbib.backend.DTO.Post.PostDeleteResponseDTO;
import com.github.gubbib.backend.DTO.Post.PostListDTO;
import com.github.gubbib.backend.DTO.Post.PostUpdateRequestDTO;
import com.github.gubbib.backend.DTO.Post.PostUpdateResponseDTO;

import java.util.List;

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
        return postRepository.findByBoard_IdAndIdAndIsDeletedFalse(boardId, postId)
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
    @Transactional(readOnly=false)
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

    @Override
    public List<PostListDTO> getPostList(Long boardId) {
        boardPostService.existsBoard(boardId);
        return postRepository.findAllByBoardId(boardId);
    }

    @Override
    @Transactional(readOnly=false)
    public PostUpdateResponseDTO updatePost(CustomUserPrincipal userPrincipal, Long boardId, Long postId, PostUpdateRequestDTO dto) {
        User user = userService.checkUser(userPrincipal);
        Post post = boardPostService.existPost(boardId, postId);

        if (!post.getUser().getId().equals(user.getId())) {
            throw new GlobalException(ErrorCode.ACCESS_DENIED);
        }

        post.update(dto.title(), dto.content());

        return PostUpdateResponseDTO.builder()
                .postId(post.getId())
                .boardId(boardId)
                .build();
    }

    @Override
    @Transactional(readOnly=false)
    public PostDeleteResponseDTO deletePost(CustomUserPrincipal userPrincipal, Long boardId, Long postId) {
        User user = userService.checkUser(userPrincipal);
        Post post = boardPostService.existPost(boardId, postId);

        if (!post.getUser().getId().equals(user.getId())) {
            throw new GlobalException(ErrorCode.ACCESS_DENIED);
        }

        // 논리적 삭제
        post.softDelete();

        return PostDeleteResponseDTO.builder()
                .postId(post.getId())
                .boardId(boardId)
                .build();
    }
}

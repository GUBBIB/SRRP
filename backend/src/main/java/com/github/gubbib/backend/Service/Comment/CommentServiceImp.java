package com.github.gubbib.backend.Service.Comment;

import com.github.gubbib.backend.DTO.Comment.CommentListDTO;
import com.github.gubbib.backend.DTO.Comment.CommentResponseDTO;
import com.github.gubbib.backend.Domain.Post.Post;
import com.github.gubbib.backend.Repository.Comment.CommentRepository;
import com.github.gubbib.backend.Repository.Like.LikeRepository;
import com.github.gubbib.backend.Security.CustomUserPrincipal;
import com.github.gubbib.backend.Service.BoardPost.BoardPostService;
import com.github.gubbib.backend.Service.Like.LikeServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ =  @Autowired)
@Transactional(readOnly = true)
public class CommentServiceImp implements CommentService {

    private final CommentRepository commentRepository;
    private final LikeServiceImp likeService;
    private final BoardPostService boardPostService;

    @Override
    public CommentResponseDTO getPostComments(CustomUserPrincipal userPrincipal, Long boardId, Long postId) {
        Post p = boardPostService.existPost(boardId, postId);

        Long currentUserId = (userPrincipal != null) ? userPrincipal.getId() : null;

        List<CommentListDTO> commentList = commentRepository.findPostComment(boardId, postId);

        for(CommentListDTO commentListDTO : commentList){
            boolean isOwner = false;
            boolean isLikedByCurrentUser = false;

            if(currentUserId != null) {
                isOwner = currentUserId == commentListDTO.userId();
                isLikedByCurrentUser = likeService.isLikedComment(commentListDTO.commentId(), currentUserId);
            }

            commentListDTO.addFlag(isOwner, isLikedByCurrentUser);
        }

        CommentResponseDTO response = CommentResponseDTO.builder()
                .boardId(p.getBoard().getId())
                .postId(p.getId())
                .comments(commentList)
                .build();

        return response;
    }
}

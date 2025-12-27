package com.github.gubbib.backend.Service.Comment;

import com.github.gubbib.backend.DTO.Comment.CommentCreateRequestDTO;
import com.github.gubbib.backend.DTO.Comment.CommentCreateResponseDTO;
import com.github.gubbib.backend.DTO.Comment.CommentListDTO;
import com.github.gubbib.backend.DTO.Comment.CommentResponseDTO;
import com.github.gubbib.backend.DTO.Notification.CommentNotificationEventDTO;
import com.github.gubbib.backend.Domain.Comment.Comment;
import com.github.gubbib.backend.Domain.Post.Post;
import com.github.gubbib.backend.Domain.User.User;
import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;
import com.github.gubbib.backend.Repository.Comment.CommentRepository;
import com.github.gubbib.backend.Security.CustomUserPrincipal;
import com.github.gubbib.backend.Service.BoardPost.BoardPostService;
import com.github.gubbib.backend.Service.Like.LikeServiceImp;
import com.github.gubbib.backend.Service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public Comment existParentComment(Long parentId) {
        return commentRepository.findById(parentId)
                .orElseThrow(() -> new GlobalException(ErrorCode.COMMENT_NOT_FOUND));
    }

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

    @Override
    @Transactional
    public CommentCreateResponseDTO createComment(CustomUserPrincipal userPrincipal, CommentCreateRequestDTO dto) {
        User user = userService.checkUser(userPrincipal);
        Post p = boardPostService.existPost(dto.boardId(), dto.postId());
        Comment parent;
        if(dto.parentId() != null){
            parent = existParentComment(dto.parentId());
        } else {
            parent = null;
        }

        Comment c = Comment.create(
                dto.comment(),
                user,
                p,
                parent
        );

        commentRepository.save(c);

        if(!p.getUser().getId().equals(user.getId())) {
            eventPublisher.publishEvent(
                    CommentNotificationEventDTO.builder()
                            .receiver(p.getUser())
                            .sender(user)
                            .boardId(p.getBoard().getId())
                            .postId(p.getId())
                            .build()
            );
        }

        CommentCreateResponseDTO response =
                CommentCreateResponseDTO.builder()
                        .boardId(dto.boardId())
                        .postId(dto.postId())
                        .build();

        return response;
    }
}

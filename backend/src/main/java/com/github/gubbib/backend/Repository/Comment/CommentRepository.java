package com.github.gubbib.backend.Repository.Comment;

import com.github.gubbib.backend.DTO.Comment.CommentListDTO;
import com.github.gubbib.backend.DTO.User.UserMyCommentDTO;
import com.github.gubbib.backend.Domain.Comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("""
        SELECT new com.github.gubbib.backend.DTO.User.UserMyCommentDTO(
            c.id,
            c.comment,
            p.id,
            p.title,
            b.id,
            b.name,
            u.id,
            u.nickname,
            u.profile_image_url,
            c.createdAt
            )
        FROM Comment c
        JOIN c.post p
        JOIN p.board b
        JOIN c.user u
        WHERE u.id = :userId
        ORDER BY c.createdAt DESC
    """)
    List<UserMyCommentDTO> findMyCommentsByUserId(Long userId);

    @Query("""
        SELECT new com.github.gubbib.backend.DTO.Comment.CommentListDTO(
                c.id,
                u.id,
                u.nickname,
                null,
                null,
                c.comment,
                c.parent.id,
                CASE WHEN c.parent IS NULL THEN 0L ELSE 1L END,
                c.createdAt
            )
        FROM Comment c
        JOIN c.user u
        JOIN c.post p
        JOIN p.board b
        WHERE p.id = :postId AND b.id = :boardId
        ORDER BY c.createdAt ASC
    """)
    List<CommentListDTO> findPostComment(Long boardId, Long postId);
}
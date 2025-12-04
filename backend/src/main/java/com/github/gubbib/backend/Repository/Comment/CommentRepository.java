package com.github.gubbib.backend.Repository.Comment;

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
}

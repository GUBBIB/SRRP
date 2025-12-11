package com.github.gubbib.backend.Repository.Like;

import com.github.gubbib.backend.DTO.User.UserMyCommentDTO;
import com.github.gubbib.backend.DTO.User.UserMyLikeCommentDTO;
import com.github.gubbib.backend.DTO.User.UserMyLikePostDTO;
import com.github.gubbib.backend.Domain.Like.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like,Long> {

    @Query("""
        SELECT new com.github.gubbib.backend.DTO.User.UserMyLikePostDTO(
            p.id,
            b.id,
            p.title,
            u.nickname,
            u.id,
            p.createdAt,
            p.viewCount,
            (SELECT CAST(COUNT(c) AS LONG) FROM Comment c WHERE c.post.id = p.id)
        )
        FROM Like l
        JOIN l.post p
        JOIN p.board b
        JOIN p.user u
        WHERE l.user.id = :userId AND l.type = com.github.gubbib.backend.Domain.Like.LikeType.POST
        ORDER BY p.createdAt DESC
    """)
    List<UserMyLikePostDTO> findMyLikePostByUserId(Long userId);

    @Query("""
        SELECT new com.github.gubbib.backend.DTO.User.UserMyLikeCommentDTO(
            c.id,
            p.id,
            b.id,
            c.comment,
            u.nickname,
            u.id,
            c.createdAt
        )
        FROM Like l
        JOIN l.comment c
        JOIN c.post p
        JOIN p.board b
        JOIN c.user u
        WHERE l.user.id = :userId AND l.type = com.github.gubbib.backend.Domain.Like.LikeType.COMMENT
        ORDER BY c.createdAt DESC
    """)
    List<UserMyLikeCommentDTO> findMyLikeCommentByUserId(Long userId);

    Boolean existsByPost_IdAndUser_Id(Long postId, Long userId);
    Boolean existsByComment_IdAndUser_Id(Long commentId, Long userId);
}

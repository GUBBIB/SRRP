package com.github.gubbib.backend.Repository.Post;

import com.github.gubbib.backend.DTO.Post.PostDetailDTO;
import com.github.gubbib.backend.DTO.Post.PostListDTO;
import com.github.gubbib.backend.DTO.User.UserMyPostDTO;
import com.github.gubbib.backend.Domain.Post.Post;
import com.github.gubbib.backend.Domain.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
        SELECT new com.github.gubbib.backend.DTO.User.UserMyPostDTO(
                p.title,
                p.content,
                b.name,
                COUNT(c),
                p.id,
                u.id,
                u.nickname,
                u.profile_image_url,
                p.createdAt
            )
        FROM Post p
        JOIN p.user u
        JOIN p.board b
        LEFT JOIN Comment c ON c.post = p
        WHERE u.id = :userId
        GROUP BY p.id, b.name, u
    """)
    List<UserMyPostDTO> findMyPostByUserId(Long userId);

    @Query("""
        SELECT new com.github.gubbib.backend.DTO.Post.PostListDTO(
                b.id,
                p.id,
                p.title,
                u.nickname,
                u.id,
                ( SELECT CAST(COUNT(*) AS LONG) FROM Comment c WHERE c.post.id = p.id),
                p.createdAt
            )
        FROM Post p
        JOIN p.board b
        JOIN p.user u
        WHERE b.id = :boardId
        ORDER BY p.createdAt DESC
    """)
    List<PostListDTO> findAllByBoardId(Long boardId);

    Optional<Post> findByBoard_IdAndId(Long boardId, Long postId);

    @Query("""
        SELECT new com.github.gubbib.backend.DTO.Post.PostDetailDTO(
                p.id,
                b.id,
                u.id,
                b.name,
                u.nickname,
                p.title,
                p.content,
                p.viewCount,
                ( SELECT CAST(COUNT(*) AS LONG) FROM Comment c WHERE c.post.id = p.id ),
                ( 
                    SELECT CAST(COUNT(*) AS LONG) 
                    FROM Like l 
                    WHERE l.type = com.github.gubbib.backend.Domain.Like.LikeType.POST 
                        AND  l.post.id = p.id
                ),
                null,
                null,
                p.createdAt
            )
        FROM Post p
        JOIN p.board b
        JOIN p.user u
        WHERE b.id = :boardId AND p.id = :postId
    """)
    PostDetailDTO findPostDetail(Long boardId, Long postId);
}

package com.github.gubbib.backend.Domain.Post;

import com.github.gubbib.backend.Domain.BaseEntity;
import com.github.gubbib.backend.Domain.Board.Board;
import com.github.gubbib.backend.Domain.Comment.Comment;
import com.github.gubbib.backend.Domain.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "posts")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title",  nullable = false, length = 255)
    private String title;
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
    @Column(name = "viewCount", nullable = false)
    private Long viewCount = 0L;

    // 관계
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public static Post create(String title, String content, User user, Board board) {
        Post post = new Post();

        post.title = title;
        post.content = content;
        post.viewCount = 0L;

        post.setUser(user);
        post.setBoard(board);

        return post;
    }

}

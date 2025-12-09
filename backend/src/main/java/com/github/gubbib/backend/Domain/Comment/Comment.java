package com.github.gubbib.backend.Domain.Comment;

import com.github.gubbib.backend.Domain.BaseEntity;
import com.github.gubbib.backend.Domain.Post.Post;
import com.github.gubbib.backend.Domain.User.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "comment",  nullable = false, columnDefinition = "TEXT")
    private String comment;

    // 관계
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    // 대댓글
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    public static Comment create(String comment, User user, Post post, Comment parent) {
        Comment c = new Comment();

        c.comment = comment;
        c.setUser(user);
        c.setPost(post);
        c.parent = parent;

        return c;
    }

    public void changeComment(String comment) {
        this.comment = comment;
    }
}

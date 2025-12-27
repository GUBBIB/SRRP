package com.github.gubbib.backend.Domain.Like;

import com.github.gubbib.backend.Domain.BaseEntity;
import com.github.gubbib.backend.Domain.Comment.Comment;
import com.github.gubbib.backend.Domain.Post.Post;
import com.github.gubbib.backend.Domain.User.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(
        name = "likes",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"post_id", "user_id"}),
                @UniqueConstraint(columnNames = {"comment_id", "user_id"})
        }
)
public class Like extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type", nullable = false, length = 255)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private LikeType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static Like createPost(Post post, User user) {
        Like l = new Like();

        l.type = LikeType.POST;
        l.post = post;
        l.comment = null;
        l.user = user;

        return l;
    }

    public static Like createComment(Comment comment, User user) {
        Like l = new Like();

        l.type = LikeType.COMMENT;
        l.post = null;
        l.comment = comment;
        l.user = user;

        return l;
    }
}

package com.github.gubbib.backend.Repository.Comment;

import com.github.gubbib.backend.Domain.Comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {


}

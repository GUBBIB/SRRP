package com.github.gubbib.backend.Service.Comment;

import com.github.gubbib.backend.Repository.Comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ =  @Autowired)
public class CommentServiceImp implements CommentService {

    private final CommentRepository commentRepository;



}

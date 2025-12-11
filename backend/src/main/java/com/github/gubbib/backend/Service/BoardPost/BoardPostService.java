package com.github.gubbib.backend.Service.BoardPost;

import com.github.gubbib.backend.DTO.Board.BoardDetailDTO;
import com.github.gubbib.backend.Domain.Post.Post;

public interface BoardPostService {
    Post existPost(Long boardId, Long postId);
    BoardDetailDTO getBoardDetail(Long boardId);
}

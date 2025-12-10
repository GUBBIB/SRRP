package com.github.gubbib.backend.Service.BoardPost;

import com.github.gubbib.backend.DTO.Board.BoardDetailDTO;

public interface BoardPostService {
    BoardDetailDTO getBoardDetail(Long boardId);
}

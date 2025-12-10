package com.github.gubbib.backend.Service.Board;

import com.github.gubbib.backend.DTO.Board.BoardListDTO;

import java.util.List;

public interface BoardService {
    List<BoardListDTO> getBoardList();
}

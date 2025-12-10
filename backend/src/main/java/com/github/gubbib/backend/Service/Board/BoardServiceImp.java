package com.github.gubbib.backend.Service.Board;

import com.github.gubbib.backend.DTO.Board.BoardListDTO;
import com.github.gubbib.backend.Repository.Board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImp implements BoardService {

    private final BoardRepository boardRepository;


    public List<BoardListDTO> getBoardList() {

        List<BoardListDTO> boardListDTO = boardRepository.getBoardList();

        return boardListDTO;
    }
}

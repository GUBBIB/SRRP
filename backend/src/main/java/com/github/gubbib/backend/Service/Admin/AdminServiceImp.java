package com.github.gubbib.backend.Service.Admin;

import com.github.gubbib.backend.DTO.Board.BoardCreateDTO;
import com.github.gubbib.backend.DTO.Board.BoardDTO;
import com.github.gubbib.backend.DTO.Board.BoardDetailDTO;
import com.github.gubbib.backend.Domain.Board.Board;
import com.github.gubbib.backend.Exception.Board.BoardAlreadyExistsException;
import com.github.gubbib.backend.Repository.Board.BoardRepository;
import com.github.gubbib.backend.Security.CustomUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor_ =  @Autowired)
@Transactional(readOnly=true)
public class AdminServiceImp implements AdminService {

    private final BoardRepository boardRepository;

    @Override
    @Transactional(readOnly=false)
    public BoardDetailDTO createBoard(CustomUserPrincipal userPrincipal, BoardCreateDTO boardCreateDTO) {

        if(boardRepository.existsByName(boardCreateDTO.title())){
            throw new BoardAlreadyExistsException();
        }

        Board board = Board.create(boardCreateDTO.title(), boardCreateDTO.description());
        BoardDTO b1 = BoardDTO.builder()
                        .boardId(board.getId())
                        .title(board.getName())
                        .description(boardCreateDTO.description())
                        .build();

        boardRepository.save(board);

        BoardDetailDTO response = BoardDetailDTO.builder()
                .board(b1)
                .postList(null)
                .build();

        return response;
    }
}

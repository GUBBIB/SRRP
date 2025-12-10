package com.github.gubbib.backend.Repository.Board;

import com.github.gubbib.backend.DTO.Board.BoardListDTO;
import com.github.gubbib.backend.Domain.Board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {

    @Query("""
        SELECT new com.github.gubbib.backend.DTO.Board.BoardListDTO(
            b.id,
            b.name,
            b.description
        )
        FROM Board b
    """)
    List<BoardListDTO> getBoardList();
}

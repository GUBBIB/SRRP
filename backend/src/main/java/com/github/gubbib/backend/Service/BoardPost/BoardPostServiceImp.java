package com.github.gubbib.backend.Service.BoardPost;

import com.github.gubbib.backend.DTO.Board.BoardDTO;
import com.github.gubbib.backend.DTO.Board.BoardDetailDTO;
import com.github.gubbib.backend.DTO.Post.PostListDTO;
import com.github.gubbib.backend.Domain.Board.Board;
import com.github.gubbib.backend.Domain.Post.Post;
import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;
import com.github.gubbib.backend.Repository.Board.BoardRepository;
import com.github.gubbib.backend.Repository.Post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardPostServiceImp implements BoardPostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;

    private Board existsBoard(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new GlobalException(ErrorCode.BOARD_NOT_FOUND));
    }


    @Override
    public Post existPost(Long boardId, Long postId) {
        return postRepository.findByBoard_IdAndId(boardId, postId)
                .orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));
    }

    @Override
    public BoardDetailDTO getBoardDetail(Long boardId) {
        Board board = existsBoard(boardId);

        BoardDTO boardDTO = BoardDTO.builder()
                .boardId(board.getId())
                .title(board.getName())
                .description(board.getDescription())
                .build();

        List<PostListDTO> post = postRepository.findAllByBoardId(boardId);

        BoardDetailDTO boardDetailDTO = BoardDetailDTO.builder()
                .board(boardDTO)
                .postList(post)
                .build();

        return boardDetailDTO;
    }
}
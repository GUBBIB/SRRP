package com.github.gubbib.backend.Controller.Board;

import com.github.gubbib.backend.DTO.Board.BoardDetailDTO;
import com.github.gubbib.backend.DTO.Board.BoardListDTO;
import com.github.gubbib.backend.Service.Board.BoardService;
import com.github.gubbib.backend.Service.BoardPost.BoardPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/api/v1/board")
@Tag(name = "Board", description = "게시판 관련 API")
public class BoardController {

    private final BoardService boardService;
    private final BoardPostService boardPostService;

    @Operation(
            summary = "게시판 목록 조회",
            description = "등록된 모든 게시판 정보를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BoardListDTO.class))))
    })
    @GetMapping("/")
    public ResponseEntity<List<BoardListDTO>> getBoardList(){
        List<BoardListDTO> boardList = boardService.getBoardList();

        return ResponseEntity.ok()
                .body(boardList);
    }

    @Operation(
            summary = "게시판 상세 조회",
            description = "특정 게시판의 정보와 게시글 목록을 함께 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = BoardDetailDTO.class))),
            @ApiResponse(responseCode = "404", description = "해당 게시판이 존재하지 않음")
    })
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDetailDTO> getBoardDetail(
            @Parameter(description = "게시판 ID", required = true)
            @PathVariable Long boardId
    ){
        BoardDetailDTO boardDetailDTO = boardPostService.getBoardDetail(boardId);

        return ResponseEntity.ok()
                .body(boardDetailDTO);
    }

}

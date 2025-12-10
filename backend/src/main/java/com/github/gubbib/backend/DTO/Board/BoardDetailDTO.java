package com.github.gubbib.backend.DTO.Board;

import com.github.gubbib.backend.DTO.Post.PostListDTO;
import lombok.Builder;

import java.util.List;

@Builder
public record BoardDetailDTO(
    BoardDTO board,
    List<PostListDTO> postList
) {
}

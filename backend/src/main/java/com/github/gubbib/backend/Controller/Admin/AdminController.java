package com.github.gubbib.backend.Controller.Admin;

import com.github.gubbib.backend.DTO.Board.BoardCreateDTO;
import com.github.gubbib.backend.DTO.Board.BoardDetailDTO;
import com.github.gubbib.backend.Security.CustomUserPrincipal;
import com.github.gubbib.backend.Service.Admin.AdminService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor(onConstructor_ =  @Autowired)
@RequestMapping("/api/v1/admin/util")
@Tag(name = "Admin", description = "관리자 전용 API")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/create/board")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BoardDetailDTO> createBoard(
            @AuthenticationPrincipal CustomUserPrincipal userPrincipal,
            @RequestBody BoardCreateDTO boardCreateDTO
    ){
        BoardDetailDTO boardDetailDTO = adminService.createBoard(userPrincipal, boardCreateDTO);

        return ResponseEntity
                .created(URI.create("/api/v1/boards/" + boardDetailDTO.board().boardId()))
                .body(boardDetailDTO);
    }

}

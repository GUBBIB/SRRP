package com.github.gubbib.backend.Service.Admin;

import com.github.gubbib.backend.DTO.Admin.SystemNotificationEventRequestDTO;
import com.github.gubbib.backend.DTO.Board.BoardCreateDTO;
import com.github.gubbib.backend.DTO.Board.BoardDetailDTO;
import com.github.gubbib.backend.Security.CustomUserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

public interface AdminService {

    void sendNoticeAllUser(SystemNotificationEventRequestDTO dto, @AuthenticationPrincipal CustomUserPrincipal userPrincipal);
    BoardDetailDTO createBoard(@AuthenticationPrincipal CustomUserPrincipal userPrincipal, BoardCreateDTO boardCreateDTO);
}

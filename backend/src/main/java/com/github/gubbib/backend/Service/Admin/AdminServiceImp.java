package com.github.gubbib.backend.Service.Admin;

import com.github.gubbib.backend.DTO.Admin.SystemNotificationEventRequestDTO;
import com.github.gubbib.backend.DTO.Board.BoardCreateDTO;
import com.github.gubbib.backend.DTO.Board.BoardDTO;
import com.github.gubbib.backend.DTO.Board.BoardDetailDTO;
import com.github.gubbib.backend.DTO.Admin.SystemNotificationEventDTO;
import com.github.gubbib.backend.Domain.Board.Board;
import com.github.gubbib.backend.Domain.User.User;
import com.github.gubbib.backend.Domain.User.UserRole;
import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;
import com.github.gubbib.backend.Repository.Board.BoardRepository;
import com.github.gubbib.backend.Security.CustomUserPrincipal;
import com.github.gubbib.backend.Service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ =  @Autowired)
@Transactional(readOnly=true)
public class AdminServiceImp implements AdminService {

    private final BoardRepository boardRepository;
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public void sendNoticeAllUser(SystemNotificationEventRequestDTO dto, CustomUserPrincipal userPrincipal) {
        User admin = userService.checkUser(userPrincipal);

        List<User> users = userService.findAllByRoleNot(UserRole.SYSTEM);

        for(User user : users){
            eventPublisher.publishEvent(
                    new SystemNotificationEventDTO(
                            user,
                            dto.message(),
                            dto.targetUrl()
                    )
            );
        }
    }

    @Override
    @Transactional
    public BoardDetailDTO createBoard(CustomUserPrincipal userPrincipal, BoardCreateDTO boardCreateDTO) {

        if(boardRepository.existsByName(boardCreateDTO.title())){
            throw new GlobalException(ErrorCode.BOARD_ALREADY_EXISTS);
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

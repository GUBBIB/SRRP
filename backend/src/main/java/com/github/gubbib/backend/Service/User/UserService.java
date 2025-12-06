package com.github.gubbib.backend.Service.User;

import com.github.gubbib.backend.DTO.User.SearchUserInfoDTO;
import com.github.gubbib.backend.DTO.User.UserInfoDTO;
import com.github.gubbib.backend.DTO.User.UserMyCommentDTO;
import com.github.gubbib.backend.DTO.User.UserMyPostDTO;
import com.github.gubbib.backend.Domain.User.User;
import com.github.gubbib.backend.Security.CustomUserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

public interface UserService {
    User checkUser(CustomUserPrincipal userPrincipal);
    User findUser(Long userId);
    UserInfoDTO me(@AuthenticationPrincipal CustomUserPrincipal userPrincipal);
    List<UserMyPostDTO> myPostList(@AuthenticationPrincipal CustomUserPrincipal userPrincipal);
    List<UserMyCommentDTO> myCommentList(@AuthenticationPrincipal CustomUserPrincipal userPrincipal);

    SearchUserInfoDTO searchUserInfo(@AuthenticationPrincipal CustomUserPrincipal userPrincipal, Long userId);
}

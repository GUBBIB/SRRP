package com.github.gubbib.backend.Controller.User;

import com.github.gubbib.backend.DTO.User.UserInfoDTO;
import com.github.gubbib.backend.DTO.User.UserMyPostDTO;
import com.github.gubbib.backend.Security.CustomUserPrincipal;
import com.github.gubbib.backend.Service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserInfoDTO> me(@AuthenticationPrincipal CustomUserPrincipal userPrincipal) {
        UserInfoDTO userInfoDTO = userService.me(userPrincipal);

        return ResponseEntity.ok()
                .body(userInfoDTO);
    }

    @GetMapping("/my-post")
    public ResponseEntity<List<UserMyPostDTO>> myPost(@AuthenticationPrincipal CustomUserPrincipal userPrincipal) {

        List<UserMyPostDTO> userMyPostList = userService.myPostList(userPrincipal);

        return ResponseEntity.ok()
                .body(userMyPostList);
    }

    @GetMapping("/my-comment")
    public ResponseEntity<List<UserMyPostDTO>> myComment(@AuthenticationPrincipal CustomUserPrincipal userPrincipal) {
        List<UserMyPostDTO> userMyCommentList = userService.myCommentList(userPrincipal);

        return ResponseEntity.ok()
                .body(userMyCommentList);
    }
}
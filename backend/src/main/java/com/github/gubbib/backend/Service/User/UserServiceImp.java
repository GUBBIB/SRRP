package com.github.gubbib.backend.Service.User;

import com.github.gubbib.backend.DTO.User.UserInfoDTO;
import com.github.gubbib.backend.DTO.User.UserMyCommentDTO;
import com.github.gubbib.backend.DTO.User.UserMyPostDTO;
import com.github.gubbib.backend.Domain.Post.Post;
import com.github.gubbib.backend.Domain.User.User;
import com.github.gubbib.backend.Exception.User.UserNotFoundException;
import com.github.gubbib.backend.Repository.Comment.CommentRepository;
import com.github.gubbib.backend.Repository.Post.PostRepository;
import com.github.gubbib.backend.Repository.User.UserRepository;
import com.github.gubbib.backend.Security.CustomUserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    public User checkUser(CustomUserPrincipal customUserPrincipal) {
        return userRepository.findById(customUserPrincipal.getUser().getId())
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserInfoDTO me(CustomUserPrincipal customUserPrincipal) {
        User user = checkUser(customUserPrincipal);

        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .nickname(user.getNickname())
                .profile_image_url(user.getProfile_image_url())
                .build();


        return userInfoDTO;
    }

    @Override
    public List<UserMyPostDTO> myPostList(CustomUserPrincipal userPrincipal) {
        User user = checkUser(userPrincipal);

        List<UserMyPostDTO> getMyPosts = postRepository.findMyPostByUserId(user.getId());

        return getMyPosts;
    }

    @Override
    public List<UserMyCommentDTO> myCommentList(CustomUserPrincipal userPrincipal) {
        User user = checkUser(userPrincipal);

        List<UserMyCommentDTO> getMyComments = commentRepository.findMyCommentsByUserId(user.getId());
        return List.of();
    }
}

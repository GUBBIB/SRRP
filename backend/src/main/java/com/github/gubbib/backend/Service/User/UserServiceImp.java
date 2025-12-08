package com.github.gubbib.backend.Service.User;

import com.github.gubbib.backend.DTO.User.*;
import com.github.gubbib.backend.Domain.User.User;
import com.github.gubbib.backend.Exception.User.UserNicknameDuplicationException;
import com.github.gubbib.backend.Exception.User.UserNotFoundException;
import com.github.gubbib.backend.Exception.User.UserPasswordNotMatchException;
import com.github.gubbib.backend.Exception.User.UserSameAsOldPasswordException;
import com.github.gubbib.backend.Repository.Comment.CommentRepository;
import com.github.gubbib.backend.Repository.Post.PostRepository;
import com.github.gubbib.backend.Repository.User.UserRepository;
import com.github.gubbib.backend.Security.CustomUserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User checkUser(CustomUserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getUser().getId())
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User findUser(Long userId) {
        return userRepository.findById(userId)
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

    @Override
    public SearchUserInfoDTO searchUserInfo(CustomUserPrincipal userPrincipal, Long userId) {
        User user = checkUser(userPrincipal);

        User searchUser = findUser(userId);

        SearchUserInfoDTO getSearchUserInfo = SearchUserInfoDTO.builder()
                .email(searchUser.getEmail())
                .name(searchUser.getName())
                .nickname(searchUser.getNickname())
                .profile_image_url(searchUser.getProfile_image_url())
                .build();

        return  getSearchUserInfo;
    }

    @Override
    public void checkNickname(CustomUserPrincipal userPrincipal, String nickname) {

        if(userRepository.existsByNickname(nickname)) {
            throw new UserNicknameDuplicationException();
        }
    }

    @Override
    public void modifyNickname(CustomUserPrincipal userPrincipal, ModifyUserNicknameDTO modifyNickname) {
        User user = checkUser(userPrincipal);

        if(userRepository.existsByNickname(modifyNickname.modifyNick())){
            throw new UserNicknameDuplicationException();
        }

        user.setNickname(modifyNickname.modifyNick());
        userRepository.save(user);
    }

    @Override
    public void modifyPassword(CustomUserPrincipal userPrincipal, ModifyUserPasswordDTO modifyUserPasswordDTO) {
        User user = checkUser(userPrincipal);

        if(!passwordEncoder.matches(modifyUserPasswordDTO.currentPassword(),  user.getPassword())){
            throw new UserPasswordNotMatchException();
        }

        if (passwordEncoder.matches(modifyUserPasswordDTO.currentPassword(), modifyUserPasswordDTO.modifyPassword())) {
            throw new UserSameAsOldPasswordException();
        }

        user.setPassword(passwordEncoder.encode(modifyUserPasswordDTO.modifyPassword()));
        userRepository.save(user);
    }
}
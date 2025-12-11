package com.github.gubbib.backend.Service.Like;

import com.github.gubbib.backend.Repository.Like.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor(onConstructor_ =  @Autowired)
public class LikeServiceImp implements LikeService {
    private final LikeRepository likeRepository;

    @Override
    public boolean isLikedComment(Long commentId, Long userId) {
        return likeRepository.existsByComment_IdAndUser_Id(commentId, userId);
    }

    @Override
    public boolean isLikedPost(Long postId, Long userId) {
        return likeRepository.existsByPost_IdAndUser_Id(postId, userId);
    }
}

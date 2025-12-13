package com.github.gubbib.backend.Service.Redis;

import com.github.gubbib.backend.Repository.Post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ViewCountSyncService {

    private final StringRedisTemplate redisTemplate;
    private final PostRepository postRepository;

    private static final String POST_VIEW_KEY_PREFIX = "view:post:";
    private static final String VIEW_POST_SET_KEY = "view:set:postIds";

    private String getPostViewKey(Long postId) {
        return POST_VIEW_KEY_PREFIX + postId;
    }

    @Transactional
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void syncViewCountToDB(){
        Set<String> postIds = redisTemplate.opsForSet().members(VIEW_POST_SET_KEY);
        if(postIds.isEmpty() || postIds == null) return;

        for(String postIdStr : postIds){
            Long postId = Long.valueOf(postIdStr);
            String viewKey = getPostViewKey(postId);

            String deltaStr = redisTemplate.opsForValue().get(viewKey);
            Long delta = (deltaStr == null) ? 0L : Long.parseLong(deltaStr);

            if(delta <= 0){
                redisTemplate.opsForSet().remove(VIEW_POST_SET_KEY, postIdStr);
                continue;
            }

            postRepository.addViewCount(postId, delta);

            redisTemplate.delete(viewKey);
            redisTemplate.opsForSet().remove(VIEW_POST_SET_KEY, postIdStr);
        }
    }
}

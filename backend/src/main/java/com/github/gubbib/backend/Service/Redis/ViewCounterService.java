package com.github.gubbib.backend.Service.Redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViewCounterService {
    private final StringRedisTemplate redisTemplate;

    private static final String POST_VIEW_KEY_PREFIX = "view:post:";

    private String getPostViewKey(Long postId){
        return POST_VIEW_KEY_PREFIX + postId;
    }

    public long increasePostView(Long postId){
        return redisTemplate
                .opsForValue()
                .increment(getPostViewKey(postId));
    }

    public long getPostViewDelta(Long postId){
        String value = redisTemplate.opsForValue().get(getPostViewKey(postId));
        if(value == null) return 0L;
        return Long.parseLong(value);
    }

    public void resetPostView(Long postId){
        redisTemplate.delete(getPostViewKey(postId));
    }
}

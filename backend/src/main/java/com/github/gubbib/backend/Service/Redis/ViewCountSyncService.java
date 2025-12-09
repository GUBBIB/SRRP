package com.github.gubbib.backend.Service.Redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViewCountSyncService {

    private final StringRedisTemplate redisTemplate;
}

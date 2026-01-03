package com.github.gubbib.backend.Service.Redis;

import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class StudentVerificationService {
    private final StringRedisTemplate redisTemplate;

    private static final String VERIFICATION_KEY_PREFIX = "verify:student:";
    private static final String VERIFICATION_COUNT_KEY_PREFIX = "verify:student:count:";
    private static final int MAX_DAILY_VERIFICATION = 3;
    private static final Duration VERIFICATION_LIMIT_TTL = Duration.ofHours(24);

    // 키 생성
    private String getKey(String prefix, String email){
        return prefix + email;
    }

    // 메일 전송 카운트용
    public void increaseVerificationRequestCount(String email){
        String key = getKey(VERIFICATION_COUNT_KEY_PREFIX,  email);
        Long count = redisTemplate.opsForValue().increment(key);

        if(count != null && count == 1L){
            redisTemplate.expire(key, VERIFICATION_LIMIT_TTL);
        }

        if(count != null && count > MAX_DAILY_VERIFICATION){
            throw new GlobalException(ErrorCode.AUTH_VERIFICATION_LIMIT_EXCEEDED);
        }
    }

    // 인증 코드 저장
    public void saveVerificationCode(String email, String code, Duration ttl){
        redisTemplate.opsForValue().set(getKey(VERIFICATION_KEY_PREFIX, email), code, ttl);
    }

    // 인증 코드 조회
    public String getVerificationCode(String email){
        return redisTemplate.opsForValue().get(getKey(VERIFICATION_KEY_PREFIX, email));
    }

    // 인증 코드 검증
    public boolean verifyCode(String email, String code){
        String savedCode = getVerificationCode(email);
        log.debug("✅ 인증코드 검증 완료!");
        return savedCode != null && savedCode.equals(code);
    }

    // 인증 코드 삭제
    public void deleteVerificationCode(String email){
        redisTemplate.delete(getKey(VERIFICATION_KEY_PREFIX, email));
    }

}

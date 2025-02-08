package com.final_test_sof3012.sof3022_ass_restful_api.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class TokenBlacklistReidsService {

    RedisTemplate<String,String> redisTemplate;
    public void blacklistToken(String token,long expirationMillis){
        redisTemplate.opsForValue().set(token,"blacklisted",expirationMillis, TimeUnit.MILLISECONDS);
    }

    public Boolean isTokenBlacklisted(String token){
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
    }
}

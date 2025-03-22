package com.final_test_sof3012.sof3022_ass_restful_api.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OtpService {

    final StringRedisTemplate redisTemplate;

    static final long OTP_EXPIRATION_TIME = 2;

    public void saveOTP(String email,String otp){
        String key = "OTP:"+email;
        redisTemplate.opsForValue().set(key,otp,OTP_EXPIRATION_TIME, TimeUnit.MINUTES);
    }

    public String getOtp(String email){
        return redisTemplate.opsForValue().get("OTP:"+email);
    }

    public void deleteOtp(String email){
        redisTemplate.delete("OTP:"+email);
    }


}

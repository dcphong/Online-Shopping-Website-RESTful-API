package com.final_test_sof3012.sof3022_ass_restful_api.controllers;

import com.final_test_sof3012.sof3022_ass_restful_api.models.ResponseObject;
import com.final_test_sof3012.sof3022_ass_restful_api.services.MailerService;
import com.final_test_sof3012.sof3022_ass_restful_api.services.OtpService;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/api/v1")
public class MailController {

    final MailerService mailerService;
    final OtpService otpService;

    @PostMapping("/mail/send")
    public ResponseEntity<?> sendOtp(@RequestParam String email) throws MessagingException{
        mailerService.sendOtpToEmail(email);
        return ResponseEntity.ok(
                new ResponseObject<>("OK","Otp is sending to: "+email,email)
        );
    }

    @PostMapping("/mail/verify")
    public ResponseEntity<?> verifyOtp(@RequestParam String email,@RequestParam String otp){
        boolean isValid = otpService.getOtp(email).equals(otp);
        return ResponseEntity.ok(
                new ResponseObject<>("OK","Checking valid otp have result!",isValid)
        );
    }

}

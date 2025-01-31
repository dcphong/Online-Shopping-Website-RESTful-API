package com.final_test_sof3012.sof3022_ass_restful_api.controllers;

import com.final_test_sof3012.sof3022_ass_restful_api.Repositories.AccountRepository;
import com.final_test_sof3012.sof3022_ass_restful_api.models.Account;
import com.final_test_sof3012.sof3022_ass_restful_api.models.ResponseObject;
import com.final_test_sof3012.sof3022_ass_restful_api.utils.JwtUtil;
import jakarta.persistence.Access;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class AuthController {

    AuthenticationManager  authenticationManager;
    JwtUtil jwtUtil;
    AccountRepository accountRepository;
    PasswordEncoder passwordEncoder;


    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody Account account){
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject<>("OK","Register Successfully!",null)
        );
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> credentials){
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(credentials
                        .get("username"),
                        credentials
                        .get("password")));
        String token =  jwtUtil.generateToken(credentials.get("username"));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject<>("OK","Login Successfully!",null));
    }

}

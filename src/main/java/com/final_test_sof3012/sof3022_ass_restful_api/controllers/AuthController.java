package com.final_test_sof3012.sof3022_ass_restful_api.controllers;

import com.final_test_sof3012.sof3022_ass_restful_api.dto.UserDTO;
import com.final_test_sof3012.sof3022_ass_restful_api.dto.request.LoginRequest;
import com.final_test_sof3012.sof3022_ass_restful_api.dto.request.RefreshTokenRequest;
import com.final_test_sof3012.sof3022_ass_restful_api.dto.request.RegistryRequest;
import com.final_test_sof3012.sof3022_ass_restful_api.dto.request.TokenRequest;
import com.final_test_sof3012.sof3022_ass_restful_api.dto.response.AuthResponse;
import com.final_test_sof3012.sof3022_ass_restful_api.mappers.UserMapper;
import com.final_test_sof3012.sof3022_ass_restful_api.models.ResponseObject;
import com.final_test_sof3012.sof3022_ass_restful_api.models.User;
import com.final_test_sof3012.sof3022_ass_restful_api.repositories.UserRepository;
import com.final_test_sof3012.sof3022_ass_restful_api.services.AuthService;
import com.final_test_sof3012.sof3022_ass_restful_api.services.UserService;
import com.final_test_sof3012.sof3022_ass_restful_api.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        AuthResponse authResponse = authService.login(loginRequest);
        if(authResponse == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject<>("ERROR","NOT FOUND USER",null)
            );
        }
        return ResponseEntity.ok(
                authResponse
        );
    }

    @PostMapping("registry")
    public ResponseEntity<ResponseObject<UserDTO>> registry(@RequestBody RegistryRequest request) {
        if(request == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject<>("ERROR", "Can't registry!", null)
            );
        } else {
            User user = new User();
            BeanUtils.copyProperties(request,user);
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userService.save(user);
            return ResponseEntity.ok(new ResponseObject<>(
                    "SUCCESS", "Registry successfully!", userMapper.toUserDTO(user)
            ));
        }
    }

    @PostMapping("refresh")
    public ResponseEntity<?>refreshToken(@RequestBody RefreshTokenRequest request){
        return ResponseEntity.ok(authService.refreshToken(request.getRefreshToken()));
    }

    @GetMapping("validateToken")
    public ResponseEntity<?> isTokenExpired(@RequestBody TokenRequest request){
        return ResponseEntity.ok(
                new ResponseObject<>("CHECK SUCCESS","Check token is expired yet?",jwtUtil.isTokenExpired(request.getAccessToken()))
        );
    }

}

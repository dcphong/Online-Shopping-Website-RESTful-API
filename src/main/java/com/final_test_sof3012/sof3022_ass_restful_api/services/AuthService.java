package com.final_test_sof3012.sof3022_ass_restful_api.services;

import com.final_test_sof3012.sof3022_ass_restful_api.dto.request.LoginRequest;
import com.final_test_sof3012.sof3022_ass_restful_api.dto.response.AuthResponse;
import com.final_test_sof3012.sof3022_ass_restful_api.mappers.UserMapper;
import com.final_test_sof3012.sof3022_ass_restful_api.models.User;
import com.final_test_sof3012.sof3022_ass_restful_api.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {

    AuthenticationManager authenticationManager;
    JwtUtil jwtUtil;
    UserService userService;
    UserMapper userMapper;

    @Transactional
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userService.findUserByUsername(request.getUsername()).orElseThrow(() -> new RuntimeException("User not found!"));
        String accessToken = jwtUtil.generateToken(user.getUsername(),user.getRoles().stream().map(Enum::name).toList());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());
        Long expiresIn = jwtUtil.getExpirationTime();

        List<String> roles = user.getRoles().stream().map(Enum::name).toList();

        return new AuthResponse(accessToken, refreshToken, "Bearer", expiresIn,
                userMapper.toUserDTO(user));
    }

    @Transactional
    public AuthResponse refreshToken(String refreshToken) {
        String username = jwtUtil.extractUsername(refreshToken);

        if (username == null || !jwtUtil.validateToken(refreshToken, username)) {
            throw new RuntimeException("Invalid or expired refresh token");
        }

        User user = userService.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        List<String> roles = user.getRoles().stream().map(Enum::name).toList();
        String newAccessToken = jwtUtil.generateToken(username, roles);
        Long expiresIn = jwtUtil.getExpirationTime();

        return new AuthResponse(newAccessToken, refreshToken, "Bearer", expiresIn, userMapper.toUserDTO(user));
    }

}

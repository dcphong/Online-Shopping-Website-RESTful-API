package com.final_test_sof3012.sof3022_ass_restful_api.controllers;

import com.final_test_sof3012.sof3022_ass_restful_api.dto.UserDTO;
import com.final_test_sof3012.sof3022_ass_restful_api.dto.request.LoginRequest;
import com.final_test_sof3012.sof3022_ass_restful_api.dto.request.RegistryRequest;
import com.final_test_sof3012.sof3022_ass_restful_api.dto.response.AuthResponse;
import com.final_test_sof3012.sof3022_ass_restful_api.dto.response.LoginResponse;
import com.final_test_sof3012.sof3022_ass_restful_api.mappers.UserMapper;
import com.final_test_sof3012.sof3022_ass_restful_api.models.ResponseObject;
import com.final_test_sof3012.sof3022_ass_restful_api.models.User;
import com.final_test_sof3012.sof3022_ass_restful_api.repositories.UserRepository;
import com.final_test_sof3012.sof3022_ass_restful_api.services.UserService;
import com.final_test_sof3012.sof3022_ass_restful_api.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
       try {
           Authentication authentication = authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(
                           loginRequest.getUsername(), loginRequest.getPassword()
                   )
           );
           Optional<User> user = userService.findUserByUsername(loginRequest.getUsername());
           String token = jwtUtil.generateToken(user.get().getUsername(), user.get().getRoles());
           return ResponseEntity.ok(new AuthResponse(token));
       }catch (BadCredentialsException e){
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
       }

//        UserDetails userDetails = (UserDetails)  authentication.getPrincipal();
//        LoginResponse  loginResponse = LoginResponse.builder().username(userDetails.getUsername()).roles(userDetails.toString()).build();
//        return ResponseEntity.ok(
//                new ResponseObject<>(
//                        "SUCCESS","Login successfully!", loginResponse )
//                );
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


}

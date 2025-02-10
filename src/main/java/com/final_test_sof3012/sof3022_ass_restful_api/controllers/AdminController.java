package com.final_test_sof3012.sof3022_ass_restful_api.controllers;

import com.final_test_sof3012.sof3022_ass_restful_api.dto.UserDTO;
import com.final_test_sof3012.sof3022_ass_restful_api.mappers.UserMapper;
import com.final_test_sof3012.sof3022_ass_restful_api.models.ResponseObject;
import com.final_test_sof3012.sof3022_ass_restful_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/users/search")
    public ResponseEntity<?> getUserWithRoleAdmin(@RequestParam String username) {
        Optional<UserDTO> user = userService.findUserByUsername(username).map(userMapper::toUserDTO);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject<>("ERROR", "Not found any user with username: " + username, null)
            );
        }
        return ResponseEntity.ok(
                new ResponseObject<>("SUCCESS", "Found user with username '" + username + "' successfully!", user)
        );
    }

}

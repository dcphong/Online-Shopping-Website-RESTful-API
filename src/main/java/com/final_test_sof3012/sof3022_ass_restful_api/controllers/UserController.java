    package com.final_test_sof3012.sof3022_ass_restful_api.controllers;

    import com.final_test_sof3012.sof3022_ass_restful_api.dto.UserDTO;
    import com.final_test_sof3012.sof3022_ass_restful_api.dto.request.PasswordRequest;
    import com.final_test_sof3012.sof3022_ass_restful_api.dto.request.UserRequest;
    import com.final_test_sof3012.sof3022_ass_restful_api.mappers.UserMapper;
    import com.final_test_sof3012.sof3022_ass_restful_api.models.ResponseObject;
    import com.final_test_sof3012.sof3022_ass_restful_api.models.User;
    import com.final_test_sof3012.sof3022_ass_restful_api.services.UserService;
    import lombok.AccessLevel;
    import lombok.RequiredArgsConstructor;
    import lombok.experimental.FieldDefaults;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import javax.swing.text.html.Option;
    import java.util.List;
    import java.util.Optional;

    @RestController
    @RequiredArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    @RequestMapping("/api/v1/")
    public class UserController {

        UserService userService;
        UserMapper userMapper;

        @GetMapping("users")
        public ResponseEntity<?> getListOfUsersDto(){
            return  ResponseEntity.ok(
                    new ResponseObject<>("OK","List of user",userService.getAllUsers())
            );
        }

        @GetMapping("/users/{id}")
        public ResponseEntity<?> getUserById(@PathVariable Long id){
            Optional<UserDTO> user = userService.getById(id);
            if(user.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject<>("ERROR","Not found any user with id:"+id,null)
                );
            }

            return ResponseEntity.ok(
                    new ResponseObject<>("SUCCESS","Get user by id:"+id+" successfully!",user)
            );
        }

        @GetMapping("/users/search")
        public ResponseEntity<?> getUserByUsername(@RequestParam String username){
            Optional<UserDTO> user = userService.findUserByUsername(username).map(userMapper::toUserDTO);
            if(user.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject<>("ERROR","Not found any user with username: "+username,null)
                );
            }
            return ResponseEntity.ok(
                    new ResponseObject<>("SUCCESS","Found user with username '"+username+"' successfully!",user)
            );
        }

        @GetMapping("/user/{id}")
        public ResponseEntity<?> getUserProfileById(@PathVariable Long id){
            Optional<UserDTO> user = userService.findUserById(id);
            if(user.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject<>("ERROR","User not found!",null)
                );
            }
            return ResponseEntity.ok(
                    new ResponseObject<>("SUCCESS","Get user by id successfully!",user)
            );
        }

        @PutMapping("/user/{id}")
        public ResponseEntity<?> updateProfile (@PathVariable Long id, @RequestBody UserRequest request){
            Optional<UserDTO> user = userService.updateUser(id,request);
            if(user.isEmpty()){
                return  ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(
                    new ResponseObject<>("OK","Update user with id: "+id+" successfully!",user)
            );
        }

        @PostMapping("/user/check-valid-password/{id}")
        public ResponseEntity<?> checkValidPassword(@PathVariable Long id, @RequestBody PasswordRequest request){
            return userService.checkValidPassword(id,request);
        }

        @PutMapping("/user/change-password/{id}")
        public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody PasswordRequest request){
            return userService.changePassword(id,request);
        }

    }

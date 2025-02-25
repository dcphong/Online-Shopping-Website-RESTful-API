    package com.final_test_sof3012.sof3022_ass_restful_api.controllers;

    import com.final_test_sof3012.sof3022_ass_restful_api.dto.UserDTO;
    import com.final_test_sof3012.sof3022_ass_restful_api.dto.request.PasswordRequest;
    import com.final_test_sof3012.sof3022_ass_restful_api.dto.request.UserRequest;
    import com.final_test_sof3012.sof3022_ass_restful_api.mappers.UserMapper;
    import com.final_test_sof3012.sof3022_ass_restful_api.models.ResponseObject;
    import com.final_test_sof3012.sof3022_ass_restful_api.models.Roles;
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
    import java.util.Set;

    @RestController
    @RequiredArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    @RequestMapping("/api/v1/")
    public class UserController {

        UserService userService;
        UserMapper userMapper;

        @GetMapping("users")
        public ResponseEntity<?> getListOfUsersDto(
                @RequestParam(defaultValue = "ROLE_USER") String role,
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "5") int size,
                @RequestParam(defaultValue = "username") String key,
                @RequestParam(defaultValue = "asc") String direction
        ){
            return  ResponseEntity.ok(
                    new ResponseObject<>("OK",
                            "List of user",
                            userService
                                    .getAllUsersWithoutRoles(Set.of(Roles.valueOf(role))
                                            ,page,size,key,direction))
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

        @GetMapping("/users/search/username")
        public ResponseEntity<?> getUserByUsername(@RequestParam String username){
            Optional<UserDTO> user = userService.getUserByUsername(username);
            if(user.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject<>("ERROR","Not found any user with username: "+username,null)
                );
            }
            return ResponseEntity.ok(
                    new ResponseObject<>("SUCCESS","Found user with username '"+username+"' successfully!",user)
            );
        }
        @GetMapping("/users/search/phone")
        public ResponseEntity<?> getUserByPhone(@RequestParam String phone){
            Optional<UserDTO> user = userService.getUserByPhoneNumber(phone);
            if(user.isEmpty()){
                return ResponseEntity.ok(
                        new ResponseObject<>("ERROR","Not found any user with phone: "+phone,null)
                );
            }
            return ResponseEntity.ok(
                    new ResponseObject<>("SUCCESS","Found user with phone '"+phone+"' successfully!",user)
            );
        }
        @GetMapping("/users/search/email")
        public ResponseEntity<?> getUserByEmail(@RequestParam String email){
            Optional<UserDTO> user = userService.getUserByEmail(email);
            if(user.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject<>("ERROR","Not found any user with email: "+email,null)
                );
            }
            return ResponseEntity.ok(
                    new ResponseObject<>("SUCCESS","Found user with email '"+email+"' successfully!",user)
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

        @PutMapping("/user/change-profile-photo/{id}")
        public ResponseEntity<?> changeProfilePhoto(@PathVariable Long id,@RequestParam String photo){
            return userService.setProfilePhoto(id,photo);
        }

        @PutMapping("/forget-password/{id}")
        public ResponseEntity<?> changePasswordByForgetPassword(@PathVariable Long id,@RequestParam String password){
            return userService.changePasswordByForgetPassword(id,password);
        }

        @PutMapping("/admin/user/locked/{id}")
        public ResponseEntity<?> changeUserStatus(@PathVariable Long id){
            return userService.changeUserStatus(id);
        }

    }

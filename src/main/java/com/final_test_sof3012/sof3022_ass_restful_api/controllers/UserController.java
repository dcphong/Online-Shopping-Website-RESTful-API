    package com.final_test_sof3012.sof3022_ass_restful_api.controllers;

    import com.final_test_sof3012.sof3022_ass_restful_api.dto.UserDTO;
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
    @RequestMapping("/api/v1")
    public class UserController {

        UserService userService;


        @GetMapping("users")
        public ResponseEntity<List<User>> getListOfUsersDto(){
            return  userService.getAllUsers();
        }

        @GetMapping("/users/{id}")
        public ResponseEntity<ResponseObject<UserDTO>> getUserById(@PathVariable Long id){
            return userService.getById(id);
        }

        @GetMapping("/users/search")
        public ResponseEntity<ResponseObject> getUserByUsername(@RequestParam String username){
            return userService.getUserByUsername(username);
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
    }

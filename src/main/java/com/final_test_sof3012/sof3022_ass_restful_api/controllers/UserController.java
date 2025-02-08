    package com.final_test_sof3012.sof3022_ass_restful_api.controllers;

    import com.final_test_sof3012.sof3022_ass_restful_api.dto.UserDTO;
    import com.final_test_sof3012.sof3022_ass_restful_api.models.ResponseObject;
    import com.final_test_sof3012.sof3022_ass_restful_api.models.User;
    import com.final_test_sof3012.sof3022_ass_restful_api.services.UserService;
    import lombok.AccessLevel;
    import lombok.RequiredArgsConstructor;
    import lombok.experimental.FieldDefaults;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

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
    }

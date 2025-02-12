package com.final_test_sof3012.sof3022_ass_restful_api.services;

import com.final_test_sof3012.sof3022_ass_restful_api.mappers.UserMapper;
import com.final_test_sof3012.sof3022_ass_restful_api.models.ResponseObject;
import com.final_test_sof3012.sof3022_ass_restful_api.models.User;
import com.final_test_sof3012.sof3022_ass_restful_api.repositories.UserRepository;
import com.final_test_sof3012.sof3022_ass_restful_api.dto.UserDTO;
import com.final_test_sof3012.sof3022_ass_restful_api.specifications.UserSpecifications;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;

    @Transactional
    public ResponseEntity<List<UserDTO>> getAllUsersDto(){
        return ResponseEntity.ok(
                userRepository.findAll().stream().map(userMapper::toUserDTO).collect(Collectors.toList())
        );
    }

    @Transactional
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(
                userRepository.findAll()
        );
    }

    @Transactional
    public ResponseEntity<ResponseObject<UserDTO>> getById(Long id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject<>("NOT_FOUND_USER","can't found user matches this id: "+id,null)
            );
        }

        return ResponseEntity.ok(
                new ResponseObject<>("OK","found user with id: "+id+" successfully!", userMapper.toUserDTO(user.get()))
        );
    }

    public Optional<User> findUserByUsername(String username){
        Specification<User> userSpec = UserSpecifications.hasUsername(username);
        Optional<User> user = userRepository.findOne(userSpec);
        return user;
    }

    public Optional<UserDTO> findUserById(Long id){
        return userRepository.findById(id).map(userMapper::toUserDTO);
    }

    public void save(User user){
        userRepository.save(user);
    }
}

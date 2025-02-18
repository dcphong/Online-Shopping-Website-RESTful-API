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
    public List<UserDTO> getAllUsers(){
        return userRepository.findAll().stream().map(userMapper::toUserDTO).collect(Collectors.toList());
    }

    @Transactional
    public Optional<UserDTO> getById(Long id){
       User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("NOT_FOUND_USER_BY_ID:"+id));
        return Optional.of(userMapper.toUserDTO(user));
    }

    @Transactional
    public Optional<User> findUserByUsername(String username){
        Specification<User> userSpec = UserSpecifications.hasUsername(username);
        Optional<User> user = userRepository.findOne(userSpec);
        return user;
    }
    @Transactional
    public Optional<UserDTO> findUserById(Long id){
        return userRepository.findById(id).map(userMapper::toUserDTO);
    }
    @Transactional
    public void save(User user){
        userRepository.save(user);
    }
}

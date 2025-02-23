package com.final_test_sof3012.sof3022_ass_restful_api.services;

import com.final_test_sof3012.sof3022_ass_restful_api.dto.request.PasswordRequest;
import com.final_test_sof3012.sof3022_ass_restful_api.dto.request.UserRequest;
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
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    PasswordEncoder passwordEncoder;

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
    public Optional<UserDTO> getUserByUsername(String username){
        Specification<User> spec = UserSpecifications.hasUsername(username);
        return Optional.of(userMapper.toUserDTO( userRepository.findOne(spec).orElseThrow(() -> new RuntimeException("NOT FOUND USER WITH USERNAME:"+username))));
    }
    @Transactional
    public Optional<UserDTO> getUserByPhoneNumber(String phone){
        Specification<User> spec = UserSpecifications.hasPhoneNumber(phone);
        return Optional.of(userMapper.toUserDTO( userRepository.findOne(spec).orElseThrow(() -> new RuntimeException("NOT FOUND USER WITH PHONE NUMBER:"+phone))));
    }
    @Transactional
    public Optional<UserDTO> getUserByEmail(String email){
        Specification<User> spec = UserSpecifications.hasEmail(email);
        return Optional.of(userMapper.toUserDTO( userRepository.findOne(spec).orElseThrow(() -> new RuntimeException("NOT FOUND USER WITH EMAIL:"+email))));
    }

    @Transactional
    public Optional<UserDTO> findUserById(Long id){
        return userRepository.findById(id).map(userMapper::toUserDTO);
    }
    @Transactional
    public void save(User user){
        userRepository.save(user);
    }

    @Transactional
    public Optional<UserDTO> updateUser(Long id, UserRequest request){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("NOT FOUND ANY USER WITH ID: "+id));

        BeanUtils.copyProperties(request,user);
        User newUser = userRepository.save(user);

        return Optional.of(userMapper.toUserDTO(newUser));
    }

    @Transactional
    public ResponseEntity<?> checkValidPassword(Long id, PasswordRequest request){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("NOT FOUND ANY USER WITH ID:"+id));
        if(!passwordEncoder.matches(request.getOldPassword(),user.getPassword())){
            return ResponseEntity.ok(
                    new ResponseObject<>("INCORRECT_OLD_PASSWORD","The request oldPassword is not correct",request.getOldPassword())
            );
        }
        if(!request.getNewPassword().equals(request.getConfirmPassword())){
            return ResponseEntity.ok(
                    new ResponseObject<>("INCORRECT_CONFIRM_PASSWORD","The request confirmPassword is not correct",request.getConfirmPassword())
            );
        }
        return ResponseEntity.ok(
                new ResponseObject<>("VALID_PASSWORD","Changeable password!",request.getConfirmPassword())
        );
    }

    @Transactional
    public ResponseEntity<?> changePassword(Long id,PasswordRequest request){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("NOT FOUND USER WITH ID:"+id));
        user.setPassword(passwordEncoder.encode(request.getConfirmPassword()));
        return ResponseEntity.ok(
                new ResponseObject<>("SUCCESS","Change password successfully!",userMapper.toUserDTO(user))
        );
    }

}

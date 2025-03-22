package com.final_test_sof3012.sof3022_ass_restful_api.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
    Long id;
    String username;
    String password;
    String email;
    String fullName;
    String address;
    String phone;
    String photo;
    Boolean available;
    Set<String> roles;
    List<Long> orderListIds;
    List<Long> productCreatingListIds;

}

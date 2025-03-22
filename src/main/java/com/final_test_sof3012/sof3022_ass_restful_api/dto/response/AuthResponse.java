package com.final_test_sof3012.sof3022_ass_restful_api.dto.response;

import com.final_test_sof3012.sof3022_ass_restful_api.dto.UserDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthResponse {
    String accessToken;
    String refreshToken;
    String tokenType = "Bearer";
    Long expiresIn;
    UserDTO user;
}

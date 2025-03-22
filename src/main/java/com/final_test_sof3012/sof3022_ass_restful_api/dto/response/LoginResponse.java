package com.final_test_sof3012.sof3022_ass_restful_api.dto.response;

import jakarta.annotation.Nullable;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginResponse {
     String username;
     String roles;
     @Nullable
     String token;
}

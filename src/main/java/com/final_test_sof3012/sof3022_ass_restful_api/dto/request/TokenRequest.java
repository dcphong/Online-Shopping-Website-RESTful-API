package com.final_test_sof3012.sof3022_ass_restful_api.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenRequest {
    String accessToken;
}

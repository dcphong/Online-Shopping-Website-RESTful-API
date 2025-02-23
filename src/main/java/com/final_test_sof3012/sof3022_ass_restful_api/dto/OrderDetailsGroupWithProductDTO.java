package com.final_test_sof3012.sof3022_ass_restful_api.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailsGroupWithProductDTO {
         Long id;
         Long quantity;
         Double price;
         Double total;
         String descriptions;
         ProductDTO product;
}

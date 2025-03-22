package com.final_test_sof3012.sof3022_ass_restful_api.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    String name;
    Double price;
    String image;
    Boolean available = true;
    BigDecimal discountPrice = null;
    Long stock_quantity;
    Long createdBy;
    Long categoryId;
    String descriptions;
}

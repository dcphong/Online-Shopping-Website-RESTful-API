package com.final_test_sof3012.sof3022_ass_restful_api.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDTO {
     Integer id;
     String name;
     Double price;
     String image;
     Boolean available;
     Double discountPrice;
     Long stock_quantity;
     Long sold_quantity;
     Date createdDate;
     String createdBy;
     String categoryId;
     String categoryName;
     String descriptions;
     List<Long> orderDetailsIds;
}

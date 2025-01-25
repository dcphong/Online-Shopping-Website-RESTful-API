package com.final_test_sof3012.sof3022_ass_restful_api.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

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
     Date createdDate;
     String categoryId;
     String categoryName;

}

package com.final_test_sof3012.sof3022_ass_restful_api.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDTO {
    Long id;
    Long user_id;
    Date orderDate;
    Double totalAmount;
    String address;
    Date update_at;
    String order_status;
    List<Long> orderDetailsListId;
}

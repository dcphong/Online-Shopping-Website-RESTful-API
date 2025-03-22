package com.final_test_sof3012.sof3022_ass_restful_api.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDTO {
    Long id;
    Long userId;
    Date orderDate = new Date();
    Double totalAmount;
    String address;
    Date update_at;

    @Value("${DEFAULT_ORDER_STATUS}")
    String order_status;
    List<Long> orderDetailsListId;
}

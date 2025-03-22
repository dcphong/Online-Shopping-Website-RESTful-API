package com.final_test_sof3012.sof3022_ass_restful_api.dto.procedure;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderedProcedure {

    Long orderId;
    Date orderDate;
    BigDecimal totalAmount;
    String orderStatus;
    Date updatedAt;
    String addressOrder;
    Long salerId;
    String customerName;
}

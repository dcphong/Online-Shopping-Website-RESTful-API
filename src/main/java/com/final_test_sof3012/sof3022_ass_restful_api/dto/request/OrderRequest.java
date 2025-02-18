package com.final_test_sof3012.sof3022_ass_restful_api.dto.request;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.final_test_sof3012.sof3022_ass_restful_api.models.OrderStatus;
import com.final_test_sof3012.sof3022_ass_restful_api.models.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    Long userId;
    Double totalAmount;
    String address;
    String status;
}

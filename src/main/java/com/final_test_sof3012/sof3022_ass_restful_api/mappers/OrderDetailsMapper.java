package com.final_test_sof3012.sof3022_ass_restful_api.mappers;

import com.final_test_sof3012.sof3022_ass_restful_api.dto.OrderDetailsDTO;
import com.final_test_sof3012.sof3022_ass_restful_api.models.OrderDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper( componentModel = "spring")
public interface OrderDetailsMapper {

    OrderDetailsMapper INSTANCE = Mappers.getMapper(OrderDetailsMapper.class);

    @Mapping(target = "productId",source = "product.id")
    @Mapping(target = "orderId",source = "order.id")
    OrderDetailsDTO toOrderDetailsDTO(OrderDetails orderDetails);

}

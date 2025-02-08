package com.final_test_sof3012.sof3022_ass_restful_api.mappers;

import com.final_test_sof3012.sof3022_ass_restful_api.dto.OrderDTO;
import com.final_test_sof3012.sof3022_ass_restful_api.models.Order;
import com.final_test_sof3012.sof3022_ass_restful_api.models.OrderDetails;
import com.final_test_sof3012.sof3022_ass_restful_api.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper( componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "user_id",source = "user.id")
    @Mapping(target = "order_status",source = "status")
    @Mapping(target = "orderDetailsListId",expression = "java(mapOrderDetailsListId(order))")
    OrderDTO toOrderDTO(Order order);
    Product toProduct(OrderDTO orderDTO);

    default List<Long> mapOrderDetailsListId(Order order){
        if (order.getOrderDetailsList() == null) return null;
        return order.getOrderDetailsList().stream().map(OrderDetails::getId).collect(Collectors.toList());
    }

}
